package com.ledgerline.validation;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public final class ValidationMessages {
    private ValidationMessages() {
    }

    public static String from(BindingResult result) {
        return result.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }
}
