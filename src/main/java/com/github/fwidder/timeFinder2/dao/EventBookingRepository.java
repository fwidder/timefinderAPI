package com.github.fwidder.timeFinder2.dao;

import com.github.fwidder.timeFinder2.model.ApplicationUser;
import com.github.fwidder.timeFinder2.model.Event;
import com.github.fwidder.timeFinder2.model.EventBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventBookingRepository extends JpaRepository<EventBooking, Long> {
    List<EventBooking> findAllByAttendee(ApplicationUser attendee);

    List<EventBooking> findAllByEvent(Event event);

    EventBooking findByDayAndEventAndAttendee(LocalDate day, Event event, ApplicationUser user);
}