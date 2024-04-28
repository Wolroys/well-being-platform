package com.wolroys.wellbeing.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWithList<E> {

    private Collection<E> data;
    private String err;
    private String description;
    private String success;
    private Long totalElements;
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

    private ResponseWithList<E> foundWithPages(Collection<E> data, Long totalElements) {
        this.data = data;
        success = "founded";
        this.totalElements = totalElements;
        timestamp = LocalDateTime.now();
        return this;
    }
}
