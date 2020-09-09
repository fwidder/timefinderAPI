package com.github.fwidder.timeFinder2.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column
    private String password;

    @Column
    @NotNull
    @Builder.Default
    private Boolean AccountNonExpired = true;

    @Column
    @NotNull
    @Builder.Default
    private Boolean AccountNonLocked = true;

    @Column
    @NotNull
    @Builder.Default
    private Boolean CredentialsNonExpired = true;

    @Column
    @NotNull
    @Builder.Default
    private Boolean Enabled = true;
}
