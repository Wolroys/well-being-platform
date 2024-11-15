package com.wolroys.wellbeing.domain.user.repository;

import com.wolroys.wellbeing.domain.user.entity.UserParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserParameterRepository extends JpaRepository<UserParameter, Long> {
}
