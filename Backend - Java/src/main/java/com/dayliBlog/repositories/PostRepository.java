package com.dayliBlog.repositories;

import com.dayliBlog.models.PostModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {

    Optional<PostModel> findByTitle(String title);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM posts WHERE id = :id", nativeQuery = true)
    void deletePost(@Param("id") UUID id);
}
