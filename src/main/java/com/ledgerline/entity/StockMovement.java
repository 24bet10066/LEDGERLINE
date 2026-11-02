package com.ledgerline.entity;
import jakarta.persistence.*;
import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;
import java.math.BigDecimal;import java.time.LocalDateTime;
@Entity @Table(name="stock_movement") @Getter @Setter @NoArgsConstructor
public class StockMovement { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne @JoinColumn(name="product_id") private Product product; @ManyToOne @JoinColumn(name="warehouse_id") private Warehouse warehouse; @ManyToOne @JoinColumn(name="location_id") private Location location; private BigDecimal quantity; @Column(name="movement_type") private String movementType; @Column(name="reference_code") private String referenceCode; private String reason; @Column(name="created_at") private LocalDateTime createdAt; }
