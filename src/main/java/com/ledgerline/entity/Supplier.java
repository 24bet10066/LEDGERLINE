package com.ledgerline.entity;
import jakarta.persistence.*;
import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;
@Entity @Table(name="supplier") @Getter @Setter @NoArgsConstructor
public class Supplier { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String name; private String email; private String phone; private boolean active=true; }
