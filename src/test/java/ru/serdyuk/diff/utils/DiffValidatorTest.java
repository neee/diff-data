package ru.serdyuk.diff.utils;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import ru.serdyuk.diff.TestData;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DiffValidatorTest {

    @Test
    void valuesExists() {
        DiffValidator.validateValues(Optional.of(TestData.valueWithEqualData()),
            TestData.valueWithEqualData().getId());
        DiffValidator.validateValues(Optional.of(TestData.valueWithNotEqualDataAndOneOffset()),
            TestData.valueWithEqualData().getId());
    }

    @Test
    void valuesDoesNotExists() {
        assertThrows(ru.serdyuk.diff.exceptions.DiffValueIsNotPresentException.class, () -> DiffValidator
            .validateValues(Optional.of(TestData.valueWithEmptyData()), TestData.valueWithEqualData().getId()));
    }
}
