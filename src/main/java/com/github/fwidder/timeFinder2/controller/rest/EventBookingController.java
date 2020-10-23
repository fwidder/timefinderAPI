package com.github.fwidder.timeFinder2.controller.rest;

import com.github.fwidder.timeFinder2.model.ApplicationUser;
import com.github.fwidder.timeFinder2.model.Event;
import com.github.fwidder.timeFinder2.model.EventBooking;
import com.github.fwidder.timeFinder2.model.UserPrincipal;
import com.github.fwidder.timeFinder2.service.EventBookingService;
import com.github.fwidder.timeFinder2.service.EventService;
import com.github.fwidder.timeFinder2.service.UserDetailsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking")
@PreAuthorize("isAuthenticated()")
public class EventBookingController {

    private final EventBookingService eventBookingService;
    private final EventService eventService;
    private final UserDetailsService userDetailsService;

    public EventBookingController(EventBookingService eventBookingService, EventService eventService, UserDetailsService userDetailsService) {
        this.eventBookingService = eventBookingService;
        this.eventService = eventService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/{eventId}")
    public LocalDate getBestDay(@PathVariable String eventId) {
        Event event = eventService.getEventById(Long.valueOf(eventId));
        return eventBookingService.getBestDayForEvent(event);
    }

    @GetMapping("/")
    public List<EventBooking> getMyBookings(Principal principal){
        ApplicationUser user = ((UserPrincipal) userDetailsService.loadUserByUsername(principal.getName())).getApplicationUser();
        return eventBookingService.getMyBookings(user);
    }

    @PostMapping("/{eventId}/{day}")
    public void postBooking(@PathVariable String eventId, @PathVariable String day, @RequestBody(required = false) String password, Principal principal) {
        ApplicationUser user = ((UserPrincipal) userDetailsService.loadUserByUsername(principal.getName())).getApplicationUser();
        Event event = eventService.getEventById(Long.valueOf(eventId));
        LocalDate date = LocalDate.parse(day);
        eventBookingService.bookEvent(date, event, user, password);
    }

    @DeleteMapping("/{eventId}/{day}")
    public void deleteBooking(@PathVariable String eventId, @PathVariable String day, Principal principal) {
        ApplicationUser user = ((UserPrincipal) userDetailsService.loadUserByUsername(principal.getName())).getApplicationUser();
        Event event = eventService.getEventById(Long.valueOf(eventId));
        LocalDate date = LocalDate.parse(day);
        eventBookingService.deleteBooking(date, event, user);
    }
}
