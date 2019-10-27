package ru.serdyuk.diff.entities;

import java.util.Optional;
import java.util.Set;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DiffResult {

    DiffResultStatus status;

    @Builder.Default
    Set<DiffOffset> offsets = Set.of();

    @Builder.Default
    Optional<String> message = Optional.empty();
}
