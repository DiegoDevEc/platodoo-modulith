package com.playtodoo.modulith.users.infrastructure;

import com.playtodoo.modulith.users.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    @Query("""
    SELECT u FROM User u 
    WHERE (LOWER(u.email) = LOWER(:value) 
       OR LOWER(u.username) = LOWER(:value) 
       OR LOWER(u.phone) = LOWER(:value)) 
      AND u.platform = :platform
      AND u.status = 'ACT'
    """)
    Optional<User> findByEmailUsernamePhoneAndPlatform(@Param("value") String value, @Param("platform") String platform);

    @Query("SELECT u FROM User u WHERE u.email = :value OR u.username = :value OR u.phone = :value")
    Optional<User> findByEmailUsernamePhone(@Param("value") String value);

    @Query("""
    SELECT u FROM User u 
    WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :search, '%'))
    """)
    Page<User> searchUsers(@Param("search") String search, Pageable pageable);
}