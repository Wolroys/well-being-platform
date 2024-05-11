package com.wolroys.wellbeing.domain.exception;

public class EventNotFoundException extends EntityNotFoundException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
