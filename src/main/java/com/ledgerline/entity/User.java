package com.ledgerline.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name="app_user") @Getter @Setter @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true) private String email;
    @Column(name="password_hash",nullable=false) private String passwordHash;
    @Column(name="full_name",nullable=false) private String fullName;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role=Role.STAFF;
    private boolean active=true;
    public enum Role { ADMIN, MANAGER, STAFF }
}
