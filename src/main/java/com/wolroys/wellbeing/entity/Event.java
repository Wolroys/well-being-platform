package com.wolroys.wellbeing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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

    @Column
    private LocalDateTime date;

    @Column(nullable = false)
    private String speaker;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
