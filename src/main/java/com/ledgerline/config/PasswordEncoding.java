package com.ledgerline.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordEncoding {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private PasswordEncoding() {
    }

    public static PasswordEncoder encoder() {
        return ENCODER;
    }
}
