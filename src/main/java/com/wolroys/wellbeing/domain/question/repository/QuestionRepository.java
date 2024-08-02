package com.wolroys.wellbeing.domain.question.repository;

import com.wolroys.wellbeing.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByEventId(Long eventId);
}
