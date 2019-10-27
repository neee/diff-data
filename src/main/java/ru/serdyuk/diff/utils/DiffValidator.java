package ru.serdyuk.diff.utils;

import ru.serdyuk.diff.entities.Diff;
import ru.serdyuk.diff.exceptions.DiffValueIsNotPresentException;

public class DiffValidator {

    public static void validateValues(Diff value) {
        if (value.getLeft().isEmpty()) {
            throw new DiffValueIsNotPresentException(
                String.format("Left value for key %s is not present", value.getId()));
        }
        if (value.getRight().isEmpty()) {
            throw new DiffValueIsNotPresentException(
                String.format("Right value for key %s is not present", value.getId()));
        }
    }
}
