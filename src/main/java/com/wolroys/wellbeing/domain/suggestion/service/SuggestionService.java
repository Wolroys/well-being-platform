package com.wolroys.wellbeing.domain.suggestion.service;

import com.wolroys.wellbeing.domain.suggestion.entity.Suggestion;
import com.wolroys.wellbeing.domain.suggestion.service.dto.SuggestionRequest;
import com.wolroys.wellbeing.util.response.Response;
import com.wolroys.wellbeing.util.response.ResponseWithList;

public interface SuggestionService {

    Response<Suggestion> suggestSpeaker(SuggestionRequest request);

    ResponseWithList<Suggestion> getAllSuggestions(SuggestionRequest request);

    Response<Suggestion> getSuggestion(SuggestionRequest request);

    Response<Suggestion> editSuggestion(SuggestionRequest request);

    Response<Suggestion> deleteSuggestion(SuggestionRequest request);

    Response<Suggestion> approveSuggestion(SuggestionRequest request);

    Response<Suggestion> rejectSuggestion(SuggestionRequest request);
}
