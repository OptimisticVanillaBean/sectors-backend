package com.sectors.sectorsbackend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false, name = "id", unique = true)
        private Long id;

        @Column(nullable = false, name = "name", unique = true)
        private String name;

        @Column(nullable = false, length = 64)
        private String password;

}