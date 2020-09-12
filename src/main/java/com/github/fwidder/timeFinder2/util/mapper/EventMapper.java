package com.github.fwidder.timeFinder2.util.mapper;

import com.github.fwidder.timeFinder2.model.ApplicationUser;
import com.github.fwidder.timeFinder2.model.Event;
import com.github.fwidder.timeFinder2.model.rest.ApplicationUserRequest;
import com.github.fwidder.timeFinder2.model.rest.EventRequest;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public Event map(EventRequest eventRequest, ApplicationUser applicationUser){
        return Event.builder()//
            .creator(applicationUser)//
            .description(eventRequest.getDescription())//
            .end(eventRequest.getEnd())//
            .name(eventRequest.getName())//
            .password(eventRequest.getPassword())//
            .secure(eventRequest.getSecure())//
            .start(eventRequest.getStart())//
            .build();
    }
}
