package com.wolroys.wellbeing.domain.user;

import com.wolroys.wellbeing.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM Users u WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))",
            nativeQuery = true)
    Page<User> findAll(Pageable pageable, String name);

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);
}
