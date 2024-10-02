package com.wolroys.wellbeing.domain.suggestion.service.dto;

import com.wolroys.wellbeing.domain.event.entity.EventTheme;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionRequest {

    private Long id;

    private String speakerName;

    private String description;

    private EventTheme theme;
}
