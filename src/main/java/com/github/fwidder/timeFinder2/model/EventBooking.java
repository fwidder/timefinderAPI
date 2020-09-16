package com.github.fwidder.timeFinder2.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(indexes = {@Index(name = "eventBookingAttendeeIndex", columnList = "ATTENDEE_ID"),
        @Index(name = "eventBookingDayIndex", columnList = "DAY"),
        @Index(name = "eventBookingEventIndex", columnList = "EVENT_ID"),
        @Index(name = "eventBookingBookingUniqueConstraintIndex", columnList = "ATTENDEE_ID, DAY, EVENT_ID")},
        uniqueConstraints = {@UniqueConstraint(name = "eventBookingBookingUniqueConstraint", columnNames = {"ATTENDEE_ID", "DAY", "EVENT_ID"})
        })
public class EventBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private ApplicationUser attendee;

    @NotNull
    @ManyToOne
    private Event event;

    @NotNull
    @Column
    private LocalDate day;
}
