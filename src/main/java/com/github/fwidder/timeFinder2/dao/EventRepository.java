package com.github.fwidder.timeFinder2.dao;

import com.github.fwidder.timeFinder2.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByCreator_Username(String creator);

    List<Event> findAllBySecureFalse();

    List<Event> findAllByStartAfterAndEndBefore(LocalDate start, LocalDate end);
}