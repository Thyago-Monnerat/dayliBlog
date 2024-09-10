package com.dayliBlog.repositories;

import com.dayliBlog.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByUsername(String name);

    @Query(value = "SELECT * FROM users WHERE username = :name", nativeQuery = true)
    Optional<UserModel> findByName(@Param("name") String name);
}
