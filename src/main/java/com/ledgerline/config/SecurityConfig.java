package com.ledgerline.config;
import com.ledgerline.repository.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration @EnableMethodSecurity
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return PasswordEncoding.encoder();}
 @Bean UserDetailsService userDetailsService(UserRepository users){return username->users.findByEmailAndActiveTrue(username).map(u->User.withUsername(u.getEmail()).password(u.getPasswordHash()).roles(u.getRole().name()).build()).orElseThrow(()->new UsernameNotFoundException(username));}
 @Bean SecurityFilterChain filterChain(HttpSecurity http)throws Exception{return http.authorizeHttpRequests(a->a.requestMatchers("/css/**","/login","/error","/actuator/health").permitAll().anyRequest().authenticated()).formLogin(f->f.loginPage("/login").defaultSuccessUrl("/dashboard",true).permitAll()).logout(l->l.logoutSuccessUrl("/login?logout")).csrf(c->c.ignoringRequestMatchers("/api/**","/login")).build();}
}
