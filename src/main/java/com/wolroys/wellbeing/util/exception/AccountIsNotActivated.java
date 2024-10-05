package com.wolroys.wellbeing.util.exception;

public class AccountIsNotActivated extends RuntimeException {
    public AccountIsNotActivated(String message) {
        super(message);
    }
}
