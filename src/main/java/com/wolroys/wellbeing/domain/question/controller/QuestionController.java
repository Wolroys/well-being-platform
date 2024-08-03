package com.wolroys.wellbeing.domain.question.controller;

import com.wolroys.wellbeing.domain.question.entity.QuestionDto;
import com.wolroys.wellbeing.domain.question.entity.QuestionRequest;
import com.wolroys.wellbeing.domain.question.service.QuestionService;
import com.wolroys.wellbeing.util.response.Response;
import com.wolroys.wellbeing.util.response.ResponseWithList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<ResponseWithList<QuestionDto>> getAllQuestions(@RequestParam Long eventId) {
        return ResponseEntity.ok(new ResponseWithList<QuestionDto>().found(questionService.findAll(eventId)));
    }

    @PostMapping("/ask")
    public ResponseEntity<Response<QuestionDto>> create(@RequestBody QuestionRequest request) {
        return ResponseEntity.ok(new Response<QuestionDto>().created(questionService.create(request)));
    }

    @PutMapping("/edit")
    public ResponseEntity<Response<QuestionDto>> update(@RequestBody QuestionRequest request) {
        return ResponseEntity.ok(new Response<QuestionDto>().updated(questionService.edit(request)));
    }

    @DeleteMapping
    public ResponseEntity<Response<QuestionDto>> delete(@RequestParam Long id) {
        return ResponseEntity.ok(new Response<QuestionDto>().deleted(questionService.delete(id)));
    }
}
