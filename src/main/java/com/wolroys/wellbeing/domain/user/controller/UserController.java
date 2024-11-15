package com.wolroys.wellbeing.domain.user.controller;

import com.wolroys.wellbeing.domain.user.entity.AuthorizationRequest;
import com.wolroys.wellbeing.domain.user.entity.UserDto;
import com.wolroys.wellbeing.domain.user.entity.UserParameterDto;
import com.wolroys.wellbeing.domain.user.entity.UserRequest;
import com.wolroys.wellbeing.domain.user.service.UserService;
import com.wolroys.wellbeing.util.response.Response;
import com.wolroys.wellbeing.util.response.ResponseWithList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Response<UserDto>> login(@Validated @RequestBody AuthorizationRequest authorizationRequest) {
        return ResponseEntity.ok(userService.login(authorizationRequest));
    }

    @GetMapping
    public ResponseEntity<ResponseWithList<UserDto>> findAll(@PageableDefault(sort = {"email"},
                                                                         direction = Sort.Direction.ASC, size = 8) Pageable pageable,
                                                             @RequestParam(required = false) String name) {
        return ResponseEntity.ok(new ResponseWithList<UserDto>().foundWithPages(userService.findAll(pageable, name)));
    }

    @PostMapping("/register")
    public ResponseEntity<Response<UserDto>> register(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(new Response<UserDto>().created(userService.register(userRequest)));
    }

    @PutMapping("/edit")
    public ResponseEntity<Response<UserDto>> edit(@RequestBody UserRequest updatedUser) {
        return ResponseEntity.ok(new Response<UserDto>().updated(userService.edit(updatedUser)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<UserDto>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<UserDto>().deleted(userService.deleteById(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<UserDto>().found(userService.findById(id)));
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<Response<UserDto>> confirm(@RequestParam String token) {
        return ResponseEntity
                .ok(new Response<UserDto>().updated(userService.confirmRegistrationToken(token)));
    }

    @GetMapping("/get_speakers")
    public ResponseEntity<ResponseWithList<UserDto>> getSpeakers(@RequestParam(required = false) String name) {
        return ResponseEntity
                .ok(new ResponseWithList<UserDto>().found(userService.findAllSpeakers(name)));
    }

    @PostMapping("/set_parameters")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<UserParameterDto> setParameters(@RequestBody UserRequest request) {
        return new Response<UserParameterDto>().created(userService.setBodyParameters(request));
    }
}
