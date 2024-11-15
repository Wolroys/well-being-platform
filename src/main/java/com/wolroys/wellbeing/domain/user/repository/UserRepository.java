package com.wolroys.wellbeing.domain.user.repository;

import com.wolroys.wellbeing.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM Users u WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))",
            nativeQuery = true)
    Page<User> findAll(Pageable pageable, String name);

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    @Query(value = "select * from users u " +
            "where u.role = 'SPEAKER' " +
            "and (:name is null or lower(u.name) like lower(concat('%', :name, '%')) " +
            "or lower(u.last_name) like lower(concat('%', :name, '%'))) ", nativeQuery = true)
    List<User> findAllSpeakers(String name);
}
