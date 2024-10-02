package com.wolroys.wellbeing.domain.suggestion.service;

import com.wolroys.wellbeing.domain.suggestion.entity.Suggestion;
import com.wolroys.wellbeing.domain.suggestion.repository.SuggestionRepository;
import com.wolroys.wellbeing.domain.suggestion.service.dto.SuggestionRequest;
import com.wolroys.wellbeing.util.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;

    @Override
    @Transactional
    public Response<Suggestion> suggestSpeaker(SuggestionRequest request) {

        Suggestion suggestion = new Suggestion();

        if (!StringUtils.hasText(request.getSpeakerName())) {
            throw new IllegalArgumentException("Speaker name cannot be empty");
        }

        if (StringUtils.hasText(request.getDescription()))
            suggestion.setDescription(request.getDescription());

        if (request.getTheme() != null)
            suggestion.setTheme(request.getTheme());

        suggestion.setSpeaker(request.getSpeakerName());
        suggestion.setIsApproved(false);

        Suggestion createdSuggestion = suggestionRepository.save(suggestion);

        return new Response<Suggestion>().created(createdSuggestion);
    }
}
