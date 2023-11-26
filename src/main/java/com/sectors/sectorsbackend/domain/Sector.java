package com.sectors.sectorsbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sectors")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "children")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id", unique = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Sector parent;

    @OneToMany(mappedBy = "parent")
    @JsonManagedReference
    private Set<Sector> children = new HashSet<>();

    public Sector(Sector parent, String name) {
        this.parent = parent;
        this.name = name;
    }
}
