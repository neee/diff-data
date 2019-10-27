package ru.serdyuk.diff.utils;

import java.util.HashSet;
import java.util.Set;

import ru.serdyuk.diff.entities.DiffOffset;

public final class DiffHelper {

    private DiffHelper() {
    }

    public static Set<DiffOffset> getOffsets(String left, String right) {
        char[] leftChars = left.toCharArray();
        char[] rightChars = right.toCharArray();
        Set<DiffOffset> offsets = new HashSet<>();
        int offsetPosition = 0;
        int offsetLength = 0;
        for (int i = 0; i < leftChars.length; i++) {
            if (leftChars[i] != rightChars[i]) {
                if (offsetLength == 0) {
                    offsetPosition = i + 1;
                }
                offsetLength++;
                continue;
            }
            if (offsetLength != 0) {
                offsets.add(DiffOffset.builder()
                    .position(offsetPosition)
                    .length(offsetLength)
                    .build());
                offsetLength = 0;
            }
        }
        if (offsetLength != 0) {
            offsets.add(DiffOffset.builder()
                .position(offsetPosition)
                .length(offsetLength)
                .build());
        }
        return offsets;
    }
}
