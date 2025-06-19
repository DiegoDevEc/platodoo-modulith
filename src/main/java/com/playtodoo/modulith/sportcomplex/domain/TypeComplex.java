package com.playtodoo.modulith.sportcomplex.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "type_complex")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeComplex {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String status = "ACT";
}
