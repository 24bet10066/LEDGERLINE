package com.ledgerline.entity;
import jakarta.persistence.*;
import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;
@Entity @Table(name="location") @Getter @Setter @NoArgsConstructor
public class Location { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(optional=false) @JoinColumn(name="warehouse_id") private Warehouse warehouse; @Column(nullable=false) private String code; }
