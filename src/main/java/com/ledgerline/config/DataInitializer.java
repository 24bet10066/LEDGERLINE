package com.ledgerline.config;
import com.ledgerline.entity.User;
import com.ledgerline.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration @RequiredArgsConstructor
public class DataInitializer {
 private final JdbcTemplate jdbc; private final UserRepository users; private final PasswordEncoder encoder;
 @Bean CommandLineRunner initialize(@Value("${LEDGERLINE_ADMIN_EMAIL:admin@ledgerline.local}")String email,@Value("${LEDGERLINE_ADMIN_PASSWORD:change-me-now}")String password){return args->{if(users.findByEmailAndActiveTrue(email).isEmpty()){User u=new User();u.setEmail(email);u.setPasswordHash(encoder.encode(password));u.setFullName("System Administrator");u.setRole(User.Role.ADMIN);users.save(u);}if(jdbc.queryForObject("SELECT COUNT(*) FROM audit_log WHERE action='DEMO_INITIALIZED'",Long.class)==0){jdbc.update("INSERT INTO audit_log(action,entity,details) VALUES('DEMO_INITIALIZED','SYSTEM','Existing workspace preserved; no automatic reseeding')");}};}
}
