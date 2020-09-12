package com.github.fwidder.timeFinder2.model.rest;

import com.github.fwidder.timeFinder2.model.ApplicationUser;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    private String name;
    private String description;
    private Boolean secure;
    private String password;
    private LocalDate start;
    private LocalDate end;
}
