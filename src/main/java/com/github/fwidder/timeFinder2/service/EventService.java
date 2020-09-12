package com.github.fwidder.timeFinder2.service;

import com.github.fwidder.timeFinder2.dao.EventRepository;
import com.github.fwidder.timeFinder2.model.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public List<Event> getAllVisibleEvents() {
        return eventRepository.findAllBySecureFalse();
    }

    public List<Event> searchEvent(String creator){
        return eventRepository.findAllByCreator_Username(creator);
    }

    public List<Event> searchEvent(LocalDate start, LocalDate end){
        return eventRepository.findAllByStartAfterAndEndBefore(start, end);
    }

    public void createEvent(Event event){
        if(event.getSecure()){
            if(event.getPassword() == null)
                throw new IllegalArgumentException("If Event is Secure, Password is not allowed to be Empty!");
            if(event.getPassword().isBlank())
                throw new IllegalArgumentException("If Event is Secure, Password is not allowed to be Empty!");
        }
        eventRepository.save(event);
    }

    public void deleteEvent(Long eventID){
        eventRepository.deleteById(eventID);
    }
}
