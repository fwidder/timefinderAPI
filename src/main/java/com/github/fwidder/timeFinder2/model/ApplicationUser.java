package com.github.fwidder.timeFinder2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(indexes = {  @Index(name = "userIndex", columnList = "USERNAME"),
                    @Index(name = "emailIndex", columnList = "EMAIL")})
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column(unique = true)
    @JsonIgnore
    private String email;

    @NotNull
    @Column
    @JsonIgnore
    private String password;

    @Column
    @NotNull
    @Builder.Default
    @JsonIgnore
    private Boolean AccountNonExpired = true;

    @Column
    @NotNull
    @Builder.Default
    @JsonIgnore
    private Boolean AccountNonLocked = true;

    @Column
    @NotNull
    @Builder.Default
    @JsonIgnore
    private Boolean CredentialsNonExpired = true;

    @Column
    @NotNull
    @Builder.Default
    @JsonIgnore
    private Boolean Enabled = true;
}
