package com.playtodoo.modulith.licensing.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "licensing_license")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class License {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "sport_complex_id", nullable = false)
    private UUID sportComplexId;

    @Column(nullable = false)
    private String type; // FREE, BASIC, PREMIUM

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private String status = "ACT";
}

