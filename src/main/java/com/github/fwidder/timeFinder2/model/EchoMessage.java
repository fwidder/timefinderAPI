package com.github.fwidder.timeFinder2.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EchoMessage {
    @Builder.Default
    private String message = "Hello World";
}
