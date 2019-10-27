package ru.serdyuk.diff.utils;

import java.util.Optional;

import ru.serdyuk.diff.entities.Diff;
import ru.serdyuk.diff.exceptions.DiffKeyNotFoundException;
import ru.serdyuk.diff.exceptions.DiffValueIsNotPresentException;

/**
 * Support class for validate received values
 */
public class DiffValidator {

    /**
     * Check values before calculate differences
     * @param value
     * @param id
     */
    public static void validateValues(Optional<Diff> value, String id) {
        if (value.isEmpty()) {
            throw new DiffKeyNotFoundException(String.format("Key with id %s, doesn't exists", id));
        }
        if (value.get().getLeft().isEmpty()) {
            throw new DiffValueIsNotPresentException(
                String.format("Left value for key %s is not present", value.get().getId()));
        }
        if (value.get().getRight().isEmpty()) {
            throw new DiffValueIsNotPresentException(
                String.format("Right value for key %s is not present", value.get().getId()));
        }
    }
}
