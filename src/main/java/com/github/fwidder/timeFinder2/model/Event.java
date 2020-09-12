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
@Table(indexes = {  @Index(name = "creatorIndex", columnList = "CREATOR_ID"),
                    @Index(name = "timeIndex", columnList = "START, END"),
                    @Index(name = "secureIndex", columnList = "SECURE")})
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private ApplicationUser creator;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String description;

    @NotNull
    @Column
    @Builder.Default
    private Boolean secure = false;

    @Column
    private String password;

    @NotNull
    @Column
    private LocalDate start;

    @NotNull
    @Column
    private LocalDate end;
}
