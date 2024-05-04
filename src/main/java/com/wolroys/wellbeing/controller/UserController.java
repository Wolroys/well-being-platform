package com.wolroys.wellbeing.controller;

import com.wolroys.wellbeing.dto.AuthorizationRequest;
import com.wolroys.wellbeing.dto.UserDto;
import com.wolroys.wellbeing.dto.UserRequestDto;
import com.wolroys.wellbeing.service.UserService;
import com.wolroys.wellbeing.util.response.Response;
import com.wolroys.wellbeing.util.response.ResponseWithList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    private ResponseEntity<Response<UserDto>> login(@RequestBody AuthorizationRequest authorizationRequest) {
        return ResponseEntity.ok(userService.login(authorizationRequest));
    }

    @GetMapping
    public ResponseEntity<ResponseWithList<UserDto>> findAllEvents() {
        return ResponseEntity.ok(new ResponseWithList<UserDto>().foundWithPages(userService.getAll()));
    }

    @PostMapping("/register")
    public ResponseEntity<Response<UserDto>> createEvent(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(new Response<UserDto>().created(userService.register(userRequestDto)));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response<UserDto>> editEvent(@PathVariable Long id, @RequestBody UserRequestDto updatedUser) {
        return ResponseEntity.ok(new Response<UserDto>().updated(userService.edit(id, updatedUser)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<UserDto>> deleteEvent(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<UserDto>().deleted(userService.deleteById(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<UserDto>().found(userService.findById(id)));
    }
}
