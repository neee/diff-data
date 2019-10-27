package ru.serdyuk.diff;

import java.util.Optional;

import ru.serdyuk.diff.entities.Diff;

public final class TestData {

    public final static String DIFF_CONTROLLER_PATH = "/v1/diff/";

    public final static String ID_1 = "1";
    public final static String UN_EXISTS_ID = "un_exists_id";
    public final static String LEFT_PATH = "/left";
    public final static String RIGHT_PATH = "/right";

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
