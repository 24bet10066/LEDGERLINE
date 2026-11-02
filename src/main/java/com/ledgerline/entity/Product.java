package com.ledgerline.entity;
import jakarta.persistence.*;
import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;
import java.math.BigDecimal;
@Entity @Table(name="product") @Getter @Setter @NoArgsConstructor
public class Product { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false,unique=true) private String sku; @Column(nullable=false) private String name; @ManyToOne @JoinColumn(name="category_id") private Category category; @ManyToOne @JoinColumn(name="supplier_id") private Supplier supplier; @Column(name="reorder_level",nullable=false) private BigDecimal reorderLevel=BigDecimal.ZERO; @Column(nullable=false) private BigDecimal cost=BigDecimal.ZERO; @Column(nullable=false) private BigDecimal price=BigDecimal.ZERO; private boolean active=true; }
