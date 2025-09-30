package com.nick.ecommerce.user_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole role;

    public enum ERole {
        ROLE_USER,
        ROLE_ADMIN
    }

    public Role() {}

    public ERole getRole() {
        return role;
    }
}