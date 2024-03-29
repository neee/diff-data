package ru.serdyuk.diff.entities;

import java.util.Optional;

import lombok.Builder;
import lombok.Value;

/**
 * Main pojo class for the {@link ru.serdyuk.diff.controllers.DiffControllerV1}
 * id - unique string
 * left - first value
 * right - second value
 */
@Value
@Builder
public class Diff {

    String id;

    @Builder.Default
    Optional<String> left = Optional.empty();

    @Builder.Default
    Optional<String> right = Optional.empty();
}
