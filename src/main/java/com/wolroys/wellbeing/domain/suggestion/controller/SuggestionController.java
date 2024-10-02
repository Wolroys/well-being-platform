package com.wolroys.wellbeing.domain.suggestion.controller;

import com.wolroys.wellbeing.domain.suggestion.entity.Suggestion;
import com.wolroys.wellbeing.domain.suggestion.service.SuggestionService;
import com.wolroys.wellbeing.domain.suggestion.service.dto.SuggestionRequest;
import com.wolroys.wellbeing.util.response.Response;
import com.wolroys.wellbeing.util.response.ResponseWithList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestions")
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping
    public ResponseEntity<ResponseWithList<Suggestion>> getAllSuggestions() {
        return ResponseEntity.ok(suggestionService.getAllSuggestions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Suggestion>> getSuggestionById(@PathVariable Long id) {
        return ResponseEntity.ok(suggestionService.getSuggestion(id));
    }

    @PatchMapping("/edit")
    public ResponseEntity<Response<Suggestion>> editSuggestion(@RequestBody SuggestionRequest request) {
        return ResponseEntity.ok(suggestionService.editSuggestion(request));
    }

    @PostMapping("/suggest")
    public ResponseEntity<Response<Suggestion>> suggestSpeaker(@RequestBody SuggestionRequest request) {
        return ResponseEntity.ok(suggestionService.suggestSpeaker(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Suggestion>> deleteSuggestion(@PathVariable Long id) {
        return ResponseEntity.ok(suggestionService.deleteSuggestion(id));
    }

    @GetMapping("/approve/{id}")
    public ResponseEntity<Response<Suggestion>> approveSuggestion(@PathVariable Long id) {
        return ResponseEntity.ok(suggestionService.approveSuggestion(id));
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<Response<Suggestion>> rejectSuggestion(@PathVariable Long id) {
        return ResponseEntity.ok(suggestionService.rejectSuggestion(id));
    }
}
