package com.github.fwidder.timeFinder2;

import com.github.fwidder.timeFinder2.dao.EventBookingRepository;
import com.github.fwidder.timeFinder2.model.ApplicationUser;
import com.github.fwidder.timeFinder2.model.Event;
import com.github.fwidder.timeFinder2.model.EventBooking;
import com.github.fwidder.timeFinder2.service.EventBookingService;
import com.github.fwidder.timeFinder2.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EventBookingServiceTest {
    EventBookingService eventBookingService;
    EventService eventService;
    EventBookingRepository eventBookingRepository;

    @BeforeEach
    public void setup(){
        eventService = Mockito.mock(EventService.class);
        eventBookingRepository = Mockito.mock(EventBookingRepository.class);
        eventBookingService = new EventBookingService(eventBookingRepository, eventService);
    }

    @Test
    public void createBookingTest(){
        Event e = Event.builder().build();
        ApplicationUser user = ApplicationUser.builder().build();

        eventBookingService.bookEvent(LocalDate.now(), e, user, null);

        verify(eventBookingRepository, Mockito.atLeastOnce()).save(Mockito.any());
    }

    @Test
    public void getBestDayForEventTest(){
        Event e = new Event();
        List<EventBooking> bookings = Collections.singletonList(EventBooking.builder().day(LocalDate.of(2020,10,5)).build());

        when(eventBookingRepository.findAllByEvent(e)).thenReturn(bookings);

        LocalDate day = eventBookingService.getBestDayForEvent(e);

        assertThat(LocalDate.of(2020,10,5), equalTo(day));
    }


}
