package com.playtodoo.modulith.courts.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "courts_availability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourtAvailability {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "court_id", nullable = false)
    private UUID courtId;

    @Column(nullable = false)
    private String dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private String status = "ACT";
}

