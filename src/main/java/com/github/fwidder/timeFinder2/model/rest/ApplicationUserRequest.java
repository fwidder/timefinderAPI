package com.github.fwidder.timeFinder2.model.rest;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserRequest {
    private String username;
    private String email;
    private String password;
}