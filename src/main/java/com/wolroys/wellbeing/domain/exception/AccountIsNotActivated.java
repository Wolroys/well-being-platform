package com.wolroys.wellbeing.domain.exception;

public class AccountIsNotActivated extends RuntimeException {
    public AccountIsNotActivated(String message) {
        super(message);
    }
}
