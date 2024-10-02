package com.wolroys.wellbeing.domain.suggestion.service;

import com.wolroys.wellbeing.domain.suggestion.entity.Suggestion;
import com.wolroys.wellbeing.domain.suggestion.service.dto.SuggestionRequest;
import com.wolroys.wellbeing.util.response.Response;
import com.wolroys.wellbeing.util.response.ResponseWithList;

public interface SuggestionService {

    Response<Suggestion> suggestSpeaker(SuggestionRequest request);

    ResponseWithList<Suggestion> getAllSuggestions();

    Response<Suggestion> getSuggestion(Long id);

    Response<Suggestion> editSuggestion(SuggestionRequest request);

    Response<Suggestion> deleteSuggestion(Long id);

    Response<Suggestion> approveSuggestion(Long id);

    Response<Suggestion> rejectSuggestion(Long id);
}
