package com.wolroys.wellbeing.domain.suggestion.service;

import com.wolroys.wellbeing.domain.suggestion.entity.Suggestion;
import com.wolroys.wellbeing.domain.suggestion.service.dto.SuggestionRequest;
import com.wolroys.wellbeing.util.response.Response;

public interface SuggestionService {

    Response<Suggestion> suggestSpeaker(SuggestionRequest request);
}
