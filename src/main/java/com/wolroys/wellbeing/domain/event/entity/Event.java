package com.wolroys.wellbeing.domain.event.entity;

import com.wolroys.wellbeing.domain.question.entity.Question;
import com.wolroys.wellbeing.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_id")
    private User speaker;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
}
