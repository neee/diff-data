package ru.serdyuk.diff.entities;

import java.util.Optional;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Diff {

    String id;

    @Builder.Default
    Optional<String> left = Optional.empty();

    @Builder.Default
    Optional<String> right = Optional.empty();
}
