package com.playtodoo.modulith.users.infrastructure;

import com.playtodoo.modulith.users.domain.UserPasswordOld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPasswordOld, UUID> {
}
