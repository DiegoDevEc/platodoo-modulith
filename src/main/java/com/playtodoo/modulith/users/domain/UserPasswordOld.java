package com.playtodoo.modulith.users.domain;

import com.playtodoo.modulith.users.config.AuditEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users_user_password")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditEntityListener.class)
public class UserPasswordOld {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_users_user_password_user"))
    private User user;

    @Column(name = "old_password", nullable = false, columnDefinition = "TEXT")
    private String oldPassword;
}
