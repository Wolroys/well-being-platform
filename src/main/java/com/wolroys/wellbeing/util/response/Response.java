package com.wolroys.wellbeing.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<E> {

    private LocalDateTime timestamp;
    private String success;
    private String error;
    private String description;
    private String token;
    private E data;

    public Response<E> created(E data) {
        success = "object has been added";
        this.data = data;
        timestamp = LocalDateTime.now();
        return this;
    }

    public Response<E> found(E data) {
        success = "found";
        this.data = data;
        timestamp = LocalDateTime.now();
        return this;
    }

    public Response<E> deleted(E data) {
        success = "object has been deleted";
        this.data = data;
        timestamp = LocalDateTime.now();
        return this;
    }

    public Response<E> updated(E data) {
        success = "object has been updated";
        this.data = data;
        timestamp = LocalDateTime.now();
        return this;
    }

    public Response<E> login(String token, E data) {
        success = "authentication was successful";
        this.data = data;
        this.token = token;
        timestamp = LocalDateTime.now();
        return this;
    }
}
