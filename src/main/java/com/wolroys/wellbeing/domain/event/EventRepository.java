package com.wolroys.wellbeing.domain.event;

import com.wolroys.wellbeing.domain.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select * from Event e " +
            "where (:title is null or lower(e.title) like lower(concat('%', :title, '%')))", nativeQuery = true)
    Page<Event> findAll(Pageable pageable, @Param("title") String title);
}
