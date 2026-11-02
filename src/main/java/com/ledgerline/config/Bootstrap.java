package com.ledgerline.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public final class Bootstrap {
    private Bootstrap() {}
    public static void initialize() throws Exception {
        try (Connection c = Database.get().getConnection()) {
            try (InputStream in = Bootstrap.class.getResourceAsStream("/schema.sql")) {
                if (in == null) throw new IllegalStateException("schema.sql is missing");
                for (String statement : new String(in.readAllBytes(), StandardCharsets.UTF_8).split(";")) {
                    if (!statement.isBlank()) try (Statement s = c.createStatement()) { s.execute(statement); }
                }
            }
            ensureAdmin(c);
            if (!hasDemoMarker(c)) insertDemo(c);
        }
    }
    private static void ensureAdmin(Connection c) throws SQLException {
        String email = env("LEDGERLINE_ADMIN_EMAIL", "admin@ledgerline.local");
        try (PreparedStatement p = c.prepareStatement("SELECT id FROM app_user WHERE email=?")) {
            p.setString(1, email);
            if (p.executeQuery().next()) return;
        }
        try (PreparedStatement p = c.prepareStatement("INSERT INTO app_user(email,password_hash,full_name,role) VALUES(?,?,?,'ADMIN')")) {
            p.setString(1, email);
            p.setString(2, PasswordEncoding.encoder().encode(env("LEDGERLINE_ADMIN_PASSWORD", "change-me-now")));
            p.setString(3, "System Administrator");
            p.executeUpdate();
        }
    }
    private static boolean hasDemoMarker(Connection c) throws SQLException {
        try (PreparedStatement p = c.prepareStatement("SELECT id FROM audit_log WHERE action='DEMO_INITIALIZED' LIMIT 1")) { return p.executeQuery().next(); }
    }
    private static void insertDemo(Connection c) throws SQLException {
        c.setAutoCommit(false);
        try {
            long admin = scalar(c, "SELECT id FROM app_user ORDER BY id LIMIT 1");
            String[] categories = {"Electronics","Office","Packaging","Safety"};
            for (String x : categories) insert(c, "INSERT IGNORE INTO category(name) VALUES(?)", x);
            insert(c, "INSERT IGNORE INTO supplier(name,email,phone) VALUES(?,?,?)", "Northstar Supply","orders@northstar.example","+1 555 0100");
            insert(c, "INSERT IGNORE INTO supplier(name,email,phone) VALUES(?,?,?)", "Morrow Industrial","sales@morrow.example","+1 555 0101");
            insert(c, "INSERT IGNORE INTO supplier(name,email,phone) VALUES(?,?,?)", "Cedar Office Co.","hello@cedar.example","+1 555 0102");
            insert(c, "INSERT IGNORE INTO warehouse(name,address) VALUES(?,?)", "Central Warehouse","14 Foundry Lane");
            insert(c, "INSERT IGNORE INTO warehouse(name,address) VALUES(?,?)", "North Annex","8 Rail Yard Road");
            long warehouse = scalar(c, "SELECT id FROM warehouse ORDER BY id LIMIT 1");
            insert(c, "INSERT IGNORE INTO location(warehouse_id,code) VALUES(?,?)", warehouse, "A-01");
            insert(c, "INSERT IGNORE INTO location(warehouse_id,code) VALUES(?,?)", warehouse, "B-01");
            String[][] products = {{"LL-1001","USB-C Dock","Electronics","32","89.00","129.00"},{"LL-1002","Barcode Scanner","Electronics","12","74.00","109.00"},{"LL-1003","Thermal Labels","Packaging","100","8.50","14.00"},{"LL-1004","A4 Paper 500pk","Office","40","4.20","7.90"},{"LL-1005","Safety Gloves","Safety","60","3.10","6.50"},{"LL-1006","Packing Tape","Packaging","24","2.80","5.90"},{"LL-1007","Desk Organizer","Office","15","9.00","16.00"},{"LL-1008","Cable Ties 100pk","Packaging","50","3.20","6.00"}};
            for (String[] p : products) {
                insert(c, "INSERT IGNORE INTO product(sku,name,category_id,supplier_id,reorder_level,cost,price) VALUES(?,?,(SELECT id FROM category WHERE name=?),(SELECT id FROM supplier ORDER BY id LIMIT 1),?,?,?)", p[0],p[1],p[2],p[3],p[4],p[5]);
                long product = scalar(c, "SELECT id FROM product WHERE sku='"+p[0]+"'");
                insert(c, "INSERT IGNORE INTO stock_balance(product_id,warehouse_id,location_id,quantity) VALUES(?,?,(SELECT id FROM location WHERE warehouse_id=? ORDER BY id LIMIT 1),?)",product,warehouse,warehouse,p[3]);
                insert(c, "INSERT IGNORE INTO stock_movement(product_id,warehouse_id,location_id,quantity,movement_type,reference_code,reason,created_by) VALUES(?,?,(SELECT id FROM location WHERE warehouse_id=? ORDER BY id LIMIT 1),?,'OPENING',?,'Demonstration opening stock',?)",product,warehouse,warehouse,p[3],"DEMO-"+p[0],admin);
            }
            insert(c, "INSERT INTO audit_log(user_id,action,entity,details) VALUES(?,'DEMO_INITIALIZED','SYSTEM','One-time demonstration workspace created')", admin);
            c.commit();
        } catch (SQLException e) { c.rollback(); throw e; } finally { c.setAutoCommit(true); }
    }
    private static long scalar(Connection c, String sql) throws SQLException { try (Statement s=c.createStatement(); ResultSet r=s.executeQuery(sql)) { if(!r.next()) throw new SQLException("Expected database row"); return r.getLong(1); } }
    private static void insert(Connection c,String sql,Object... values) throws SQLException { try(PreparedStatement p=c.prepareStatement(sql)){for(int i=0;i<values.length;i++)p.setObject(i+1,values[i]);p.executeUpdate();} }
    private static String env(String n,String f){String v=System.getenv(n);return v==null||v.isBlank()?f:v;}
}
