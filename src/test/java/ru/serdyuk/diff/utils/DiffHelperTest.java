package ru.serdyuk.diff.utils;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.serdyuk.diff.TestData;
import ru.serdyuk.diff.entities.DiffOffset;

class DiffHelperTest {

    @Test
    void checkNoOffsetsWithEqualData() {
        var diff = TestData.valueWithEqualData();
        var left = diff.getLeft().get();
        var right = diff.getRight().get();
        var offsets = DiffHelper.getOffsets(left, right);
        Assertions.assertTrue(offsets.isEmpty());
    }

    @Test
    void checkDataWithOneOffsetAndTwoSymbolsLength() {
        var diff = TestData.valueWithNotEqualDataAndOneOffset();
        var left = diff.getLeft().get();
        var right = diff.getRight().get();
        var offsets = DiffHelper.getOffsets(left, right);
        Assertions.assertEquals(1, offsets.size());
        Assertions.assertEquals(2, offsets.stream().findFirst().get().getLength());
        Assertions.assertEquals(3, offsets.stream().findFirst().get().getPosition());
    }

    @Test
    void checkDataWithTwoOffsets() {
        var diff = TestData.valueWithNotEqualDataAndTwoOffsets();
        var left = diff.getLeft().get();
        var right = diff.getRight().get();
        var offsets = DiffHelper.getOffsets(left, right);
        Assertions.assertEquals(Set.of(
            DiffOffset.builder().position(3).length(1).build(),
            DiffOffset.builder().position(7).length(1).build()),
            offsets);
    }
}
