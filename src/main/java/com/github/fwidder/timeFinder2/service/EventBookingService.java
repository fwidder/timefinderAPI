package com.github.fwidder.timeFinder2.service;

import com.github.fwidder.timeFinder2.dao.EventBookingRepository;
import com.github.fwidder.timeFinder2.model.ApplicationUser;
import com.github.fwidder.timeFinder2.model.Event;
import com.github.fwidder.timeFinder2.model.EventBooking;
import com.github.fwidder.timeFinder2.util.BookingNotFoundException;
import com.github.fwidder.timeFinder2.util.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EventBookingService {

    private final EventBookingRepository eventBookingRepository;

    public EventBookingService(EventBookingRepository eventBookingRepository) {
        this.eventBookingRepository = eventBookingRepository;
    }

    public LocalDate getBestDayForEvent(Event event) {
        List<EventBooking> bookings = eventBookingRepository.findAllByEvent(event);
        HashMap<LocalDate, Long> dates = new HashMap<>();

        // Return first Day if no Votes present
        if (bookings.isEmpty())
            return event.getStart();

        // Count attendees per date
        bookings.forEach(b -> {
            LocalDate day = b.getDay();
            dates.putIfAbsent(day, 0L);
            dates.put(day, dates.get(day) + 1L);
        });

        // Get date with highest attendees
        return Collections.max(dates.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public void bookEvent(LocalDate date, Event event, ApplicationUser applicationUser, String password) {
        if (event.getSecure())
            if (!event.getPassword().equals(password))
                throw new WrongPasswordException();
        EventBooking booking = EventBooking.builder().//
                attendee(applicationUser).//
                day(date).//
                event(event).//
                build();//

        eventBookingRepository.save(booking);
    }

    public void deleteBooking(LocalDate date, Event event, ApplicationUser applicationUser) {
        EventBooking booking = eventBookingRepository.findByDayAndEventAndAttendee(date, event, applicationUser);
        if (booking == null) {
            throw new BookingNotFoundException();
        }
        eventBookingRepository.delete(booking);
    }

    public List<EventBooking> getMyBookings(ApplicationUser applicationUser){
        return eventBookingRepository.findAllByAttendee(applicationUser);
    }
}
