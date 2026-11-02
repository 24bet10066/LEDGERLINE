package com.ledgerline.dao;
import com.ledgerline.config.Database;
import com.ledgerline.config.PasswordEncoding;
import com.ledgerline.model.User;
import java.sql.*;
public class UserDao {
 public User authenticate(String email,String password) throws SQLException {
  try(PreparedStatement p=Database.get().getConnection().prepareStatement("SELECT id,email,full_name,role,password_hash FROM app_user WHERE email=? AND active=1")) {
   p.setString(1,email);try(ResultSet r=p.executeQuery()){if(r.next()&&PasswordEncoding.encoder().matches(password,r.getString(5)))return new User(r.getLong(1),r.getString(2),r.getString(3),r.getString(4));}
  } return null;
 }
}
