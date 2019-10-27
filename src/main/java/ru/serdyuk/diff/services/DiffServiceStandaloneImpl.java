package ru.serdyuk.diff.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import reactor.core.publisher.Mono;
import ru.serdyuk.diff.entities.Diff;
import ru.serdyuk.diff.entities.DiffOffset;
import ru.serdyuk.diff.entities.DiffResult;
import ru.serdyuk.diff.entities.DiffResultStatus;
import ru.serdyuk.diff.exceptions.DiffKeyNotFoundException;
import ru.serdyuk.diff.exceptions.DiffValueIsNotPresentException;

public class DiffServiceStandaloneImpl implements DiffService {

    private final Map<String, Diff> storage = new ConcurrentHashMap<>();

    @Override
    public Mono<String> putLeft(String id, String data) {
        Diff.DiffBuilder putValueBuilder = Diff.builder();
        if (storage.containsKey(id)) {
            putValueBuilder.id(id)
                .left(Optional.of(data))
                .right(storage.get(id).getRight());
            storage.put(id, putValueBuilder.build());
        } else {
            putValueBuilder.id(id).left(Optional.of(data)).build();
        }
        storage.put(id, putValueBuilder.build());
        return Mono.just(id);
    }

    @Override
    public Mono<String> putRight(String id, String data) {
        Diff.DiffBuilder putValueBuilder = Diff.builder();
        if (storage.containsKey(id)) {
            putValueBuilder
                .id(id)
                .right(Optional.of(data))
                .left(storage.get(id).getRight());
            storage.put(id, putValueBuilder.build());
        } else {
            putValueBuilder.id(id).right(Optional.of(data)).build();
        }
        storage.put(id, putValueBuilder.build());
        return Mono.just(id);
    }

    @Override
    public Mono<DiffResult> getResult(String id) {
        validateValues(id);
        Diff diff = storage.get(id);
        String left = diff.getLeft().get();
        String right = diff.getRight().get();
        if (left.length() != right.length()) {
            return Mono.just(DiffResult.builder()
                .status(DiffResultStatus.SIZE_IS_DIFFERENT)
                .build());
        }
        char[] leftChars = left.toCharArray();
        char[] rightChars = right.toCharArray();
        Set<DiffOffset> offsets = new HashSet<>();
        int offsetStatPosition = 0;
        int offsetLength = 0;
        for (int i = 0; i < leftChars.length; i++) {
            if (leftChars[i] != rightChars[i]) {
                if (offsetStatPosition == 0) {
                    offsetStatPosition = i + 1;
                }
                offsetLength++;
                continue;
            }
            if (offsetStatPosition != 0) {
                offsets.add(DiffOffset.builder()
                    .position(offsetStatPosition)
                    .length(offsetLength)
                    .build());
            }
        }
        if (offsets.isEmpty()) {
            return Mono.just(DiffResult.builder()
                .status(DiffResultStatus.EQUAL)
                .build());
        }
        return Mono.just(DiffResult.builder()
            .status(DiffResultStatus.FOUND_DIFFERENSES)
            .offsets(offsets)
            .build());
    }

    private void validateValues(String id) {
        if (!storage.containsKey(id)) {
            throw new DiffKeyNotFoundException(String.format("Key with id %s, doesn't exists", id));
        }
        Diff foundValue = storage.get(id);
        if (foundValue.getLeft().isEmpty()) {
            throw new DiffValueIsNotPresentException(String.format("Left value for key %s is not present", id));
        }
        if (foundValue.getRight().isEmpty()) {
            throw new DiffValueIsNotPresentException(String.format("Right value for key %s is not present", id));
        }
    }
}
