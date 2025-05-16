package com.playtodoo.modulith.customers.domain;

import com.playtodoo.modulith.users.config.AuditEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditEntityListener.class)
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;
}
