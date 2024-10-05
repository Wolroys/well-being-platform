package com.wolroys.wellbeing.util.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
