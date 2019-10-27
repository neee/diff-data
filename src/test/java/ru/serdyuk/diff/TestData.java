package ru.serdyuk.diff;

import java.util.Optional;

import ru.serdyuk.diff.entities.Diff;

public final class TestData {

    private TestData() {
    }

    public static Diff valueWithEqualData() {
        return Diff.builder()
            .id("1")
            .left(Optional.of("abcdef"))
            .right(Optional.of("abcdef")).build();
    }

    public static Diff valueWithNotEqualDataAndOneOffset() {
        return Diff.builder()
            .id("2")
            .left(Optional.of("abcdef"))
            .right(Optional.of("abUUef")).build();
    }

    public static Diff valueWithNotEqualDataAndTwoOffsets() {
        return Diff.builder()
            .id("2")
            .left(Optional.of("abcdefg"))
            .right(Optional.of("abUdefU")).build();
    }

    public static Diff valueWithEmptyData() {
        return Diff.builder()
            .id("3")
            .build();
    }
}
