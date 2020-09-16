package com.github.fwidder.timeFinder2.controller.rest;

import com.github.fwidder.timeFinder2.model.ApplicationUser;
import com.github.fwidder.timeFinder2.model.Event;
import com.github.fwidder.timeFinder2.model.UserPrincipal;
import com.github.fwidder.timeFinder2.model.rest.EventRequest;
import com.github.fwidder.timeFinder2.service.EventService;
import com.github.fwidder.timeFinder2.service.UserDetailsService;
import com.github.fwidder.timeFinder2.util.EventNotFoundException;
import com.github.fwidder.timeFinder2.util.mapper.EventMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/event")
@PreAuthorize("isAuthenticated()")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UserDetailsService userDetailsService;

    public EventController(EventService eventService, EventMapper eventMapper, UserDetailsService userDetailsService) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public void postEvent(@RequestBody EventRequest event, Principal principal) {
        ApplicationUser user = ((UserPrincipal) userDetailsService.loadUserByUsername(principal.getName())).getApplicationUser();
        eventService.createEvent(eventMapper.map(event, user));
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getAllVisibleEvents();
    }

    @GetMapping("/id/{id}")
    public Event getEventById(@PathVariable String id) {
        Long longId = Long.parseLong(id);
        Event event = eventService.getEventById(longId);
        if (event == null)
            throw new EventNotFoundException();
        return event;
    }

    @GetMapping("/users/{username}")
    public List<Event> findEventByUsername(@PathVariable String username) {
        return eventService.searchEvent(username);
    }

    @GetMapping("/start/{start}/end/{end}")
    public List<Event> findEventByStartAndEnd(@PathVariable String start, @PathVariable String end) {
        LocalDate startDate = LocalDate.parse(start), endDate = LocalDate.parse(end);
        return eventService.searchEvent(startDate, endDate);
    }

    @GetMapping("/end/{end}")
    public List<Event> findEventByEnd(@PathVariable String end) {
        LocalDate endDate = LocalDate.parse(end), startDate = endDate.minusMonths(6);
        return eventService.searchEvent(startDate, endDate);
    }

    @GetMapping("/start/{start}")
    public List<Event> findEventByStart(@PathVariable String start) {
        LocalDate startDate = LocalDate.parse(start), endDate = startDate.plusMonths(6);
        return eventService.searchEvent(startDate, endDate);
    }

    @DeleteMapping("/id/{id}")
    public void deleteEventById(@PathVariable String id, Principal principal) throws IllegalAccessException {
        ApplicationUser user = ((UserPrincipal) userDetailsService.loadUserByUsername(principal.getName())).getApplicationUser();
        Event event = getEventById(id);
        if (!event.getCreator().equals(user)) {
            throw new IllegalAccessException("Only Creator can delete Event!");
        }
        eventService.deleteEvent(event.getId());
    }
}