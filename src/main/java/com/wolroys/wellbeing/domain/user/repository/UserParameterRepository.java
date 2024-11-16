package com.wolroys.wellbeing.domain.user.repository;

import com.wolroys.wellbeing.domain.user.entity.UserParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserParameterRepository extends JpaRepository<UserParameter, Long> {

    @Query("select up from UserParameter up where up.user.id = :userId and up.addedAt in (" +
            "select max(up2.addedAt) from UserParameter up2)")
    Optional<UserParameter> findLatestByUserId(Long userId);

}
