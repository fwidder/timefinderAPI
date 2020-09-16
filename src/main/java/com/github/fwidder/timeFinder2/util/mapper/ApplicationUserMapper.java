package com.github.fwidder.timeFinder2.util.mapper;

import com.github.fwidder.timeFinder2.model.ApplicationUser;
import com.github.fwidder.timeFinder2.model.rest.ApplicationUserRequest;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUserMapper {
    public ApplicationUser map(ApplicationUserRequest applicationUserRequest) {
        return ApplicationUser.builder()//
                .password(applicationUserRequest.getPassword())//
                .username(applicationUserRequest.getUsername())//
                .email(applicationUserRequest.getEmail())//
                .build();
    }
}
