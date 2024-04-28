package com.wolroys.wellbeing.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWithList<E> {

    private Collection<E> data;
    private String err;
    private String description;
    private String success;
    private int totalElements;
    private LocalDateTime timestamp;

    public ResponseWithList<E> invalid(String err, String description) {
        this.err = err;
        this.description = description;
        timestamp = LocalDateTime.now();
        return this;
    }

    public ResponseWithList<E> found(Collection<E> data) {
        this.data = data;
        success = "founded";
        timestamp = LocalDateTime.now();
        return this;
    }

    public ResponseWithList<E> foundWithPages(Collection<E> data) {
        this.data = data;
        success = "founded";
        totalElements = data.size();
        timestamp = LocalDateTime.now();
        return this;
    }
}
