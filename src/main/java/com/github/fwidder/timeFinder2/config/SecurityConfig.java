package com.github.fwidder.timeFinder2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final String jwtSecret;
    private final String jwtIssuer;
    private final String jwtType;
    private final String jwtAudience;

    public SecurityConfig(UserDetailsService userDetailsService,
                          @Value("${jwt.secret}") String jwtSecret,
                          @Value("${jwt.issuer}") String jwtIssuer,
                          @Value("${jwt.type}") String jwtType,
                          @Value("${jwt.audience}") String jwtAudience) {
        this.userDetailsService = userDetailsService;
        this.jwtSecret = jwtSecret;
        this.jwtIssuer = jwtIssuer;
        this.jwtType = jwtType;
        this.jwtAudience = jwtAudience;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable Post Requests
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/jwt-login").permitAll();

        // Allow Embedding
        http.headers().frameOptions().disable();

        // Enable Basic Auth
        // http.httpBasic();

        // Disable Session handling
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Disable Login Form
        http.formLogin().disable();

        // Add Filter for JWT
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtAudience, jwtIssuer, jwtSecret, jwtType));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtSecret));
    }

}