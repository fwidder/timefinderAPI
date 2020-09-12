package com.github.fwidder.timeFinder2.controller.rest;

import com.github.fwidder.timeFinder2.dao.ApplicationUserRepository;
import com.github.fwidder.timeFinder2.model.ApplicationUser;
import com.github.fwidder.timeFinder2.model.Event;
import com.github.fwidder.timeFinder2.model.rest.EventRequest;
import com.github.fwidder.timeFinder2.service.EventService;
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
    private final ApplicationUserRepository applicationUserRepository;

    public EventController(EventService eventService, EventMapper eventMapper, ApplicationUserRepository applicationUserRepository) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.applicationUserRepository = applicationUserRepository;
    }

    @PostMapping
    public void postEvent(@RequestBody EventRequest event, Principal principal){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        eventService.createEvent(eventMapper.map(event, user));
    }

    @GetMapping
    public List<Event> getEvents(){
        return eventService.getAllVisibleEvents();
    }

    @GetMapping("/id/{id}")
    public Event getEventById(@PathVariable String id){
        Long longId = Long.parseLong(id);
        return eventService.getEventById(longId);
    }

    @GetMapping("/users/{username}")
    public List<Event> getEventByUsername(@PathVariable String username){
        return eventService.searchEvent(username);
    }

    @GetMapping("/start/{start}/end/{end}")
    public List<Event> getEventByStartAndEnd(@PathVariable String start, @PathVariable String end){
        LocalDate startDate = LocalDate.parse(start), endDate = LocalDate.parse(end);
        return eventService.searchEvent(startDate, endDate);
    }

    @GetMapping("/end/{end}")
    public List<Event> getEventByEnd(@PathVariable String end){
        LocalDate endDate = LocalDate.parse(end), startDate = endDate.minusMonths(6);
        return eventService.searchEvent(startDate, endDate);
    }

    @GetMapping("/start/{start}")
    public List<Event> getEventByStart(@PathVariable String start){
        LocalDate startDate = LocalDate.parse(start), endDate = startDate.plusMonths(6);
        return eventService.searchEvent(startDate, endDate);
    }

    @DeleteMapping("/id/{id}")
    public void deleteEventById(@PathVariable String id, Principal principal) throws IllegalAccessException {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        Event event = getEventById(id);
        if(!event.getCreator().equals(user)){
            throw new IllegalAccessException("Only Creator can delete Event!");
        }
        eventService.deleteEvent(event.getId());
    }
}