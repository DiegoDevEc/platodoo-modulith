package com.playtodoo.modulith.sportcomplex.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sportcomplex_complex")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SportComplex {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Double latitude;
    private Double longitude;

    @Column(columnDefinition = "TEXT")
    private String landingPageConfig;

    @Column(nullable = false)
    private String status = "ACT";

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
