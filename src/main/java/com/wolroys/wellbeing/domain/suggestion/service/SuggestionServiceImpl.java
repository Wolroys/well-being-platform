package com.wolroys.wellbeing.domain.suggestion.service;

import com.wolroys.wellbeing.domain.exception.EntityNotFoundException;
import com.wolroys.wellbeing.domain.suggestion.entity.Suggestion;
import com.wolroys.wellbeing.domain.suggestion.repository.SuggestionRepository;
import com.wolroys.wellbeing.domain.suggestion.service.dto.SuggestionRequest;
import com.wolroys.wellbeing.util.response.Response;
import com.wolroys.wellbeing.util.response.ResponseWithList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
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

    @Override
    public ResponseWithList<Suggestion> getAllSuggestions() { //TODO Добавить пагинацию
        return new ResponseWithList<Suggestion>().found(suggestionRepository.findAll());
    }

    @Override
    public Response<Suggestion> getSuggestion(Long id) {
        Suggestion suggestion = findSuggestionById(id);

        return new Response<Suggestion>().found(suggestion);
    }

    @Override
    public Response<Suggestion> editSuggestion(SuggestionRequest request) {
        Suggestion suggestion = findSuggestionById(request.getId());

        if (StringUtils.hasText(request.getSpeakerName()))
            suggestion.setSpeaker(request.getSpeakerName());

        if (StringUtils.hasText(request.getDescription()))
            suggestion.setDescription(request.getDescription());

        if (request.getTheme() != null)
            suggestion.setTheme(request.getTheme());

        return new Response<Suggestion>().updated(suggestion);
    }

    @Override
    public Response<Suggestion> deleteSuggestion(Long id) {
        Suggestion suggestion = findSuggestionById(id);
        suggestionRepository.delete(suggestion);

        return new Response<Suggestion>().deleted(suggestion);
    }

    @Override
    public Response<Suggestion> approveSuggestion(Long id) {
        Suggestion suggestion = findSuggestionById(id);
        suggestion.setIsApproved(true);

        return new Response<Suggestion>().updated(suggestion);
    }

    @Override
    public Response<Suggestion> rejectSuggestion(Long id) {
        Suggestion suggestion = findSuggestionById(id);
        suggestion.setIsApproved(false);

        return new Response<Suggestion>().updated(suggestion);
    }

    private Suggestion findSuggestionById(Long suggestionId) {
        return suggestionRepository.findById(suggestionId)
                .orElseThrow(() -> {
                    log.error("Suggestion with id {} not found", suggestionId);
                    return new EntityNotFoundException("Suggestion not found");
                });
    }
}
