package com.github.fwidder.timeFinder2.controller.rest;

import com.github.fwidder.timeFinder2.dao.ApplicationUserRepository;
import com.github.fwidder.timeFinder2.model.rest.ApplicationUserRequest;
import com.github.fwidder.timeFinder2.util.mapper.ApplicationUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class ApplicationUserController {

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserMapper applicationUserMapper;

    public ApplicationUserController(ApplicationUserRepository applicationUserRepository,
                                     PasswordEncoder passwordEncoder, ApplicationUserMapper applicationUserMapper) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationUserMapper = applicationUserMapper;
    }

    @PostMapping("/sign-up")
    @PreAuthorize("permitAll()")
    public void signUp(@RequestBody ApplicationUserRequest user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(applicationUserMapper.map(user));
    }
}