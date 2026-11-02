package com.ledgerline.entity;
import jakarta.persistence.*;
import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;
import java.time.LocalDateTime;
@Entity @Table(name="purchase_order") @Getter @Setter @NoArgsConstructor
public class PurchaseOrder { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(name="order_number",nullable=false,unique=true) private String orderNumber; @ManyToOne @JoinColumn(name="supplier_id") private Supplier supplier; @ManyToOne @JoinColumn(name="warehouse_id") private Warehouse warehouse; @Column(nullable=false) private String status="DRAFT"; @Column(name="ordered_at") private LocalDateTime orderedAt; }
