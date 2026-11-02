package com.ledgerline.entity;
import jakarta.persistence.*;
import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;
@Entity @Table(name="warehouse") @Getter @Setter @NoArgsConstructor
public class Warehouse { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false,unique=true) private String name; private String address; private boolean active=true; }
