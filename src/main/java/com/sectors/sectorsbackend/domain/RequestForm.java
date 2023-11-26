package com.sectors.sectorsbackend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "requestform")
@Data
@NoArgsConstructor
public class RequestForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "requestform_sector",
            joinColumns = @JoinColumn(name = "requestform_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id"))
    @JsonManagedReference
    private Set<Sector> sectors = new HashSet<>();

    @Column(nullable = false)
    private Boolean termsAgreed;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

}
