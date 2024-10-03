package com.wolroys.wellbeing.domain.suggestion.entity;

import com.wolroys.wellbeing.domain.event.entity.EventTheme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String suggestionSpeaker;

    private String comment;

    private Boolean isApproved;

    @Enumerated(EnumType.STRING)
    private EventTheme theme;
}
