package ru.serdyuk.diff.services;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import reactor.core.publisher.Mono;
import ru.serdyuk.diff.entities.Diff;
import ru.serdyuk.diff.entities.DiffResult;
import ru.serdyuk.diff.entities.DiffResultStatus;
import ru.serdyuk.diff.utils.DiffHelper;
import ru.serdyuk.diff.utils.DiffValidator;

/**
 * Local implementation with in-memory storage like Map, recommended for using only for test, non production
 */
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
                .left(storage.get(id).getLeft())
                .right(Optional.of(data));
            storage.put(id, putValueBuilder.build());
        } else {
            putValueBuilder.id(id).right(Optional.of(data)).build();
        }
        storage.put(id, putValueBuilder.build());
        return Mono.just(id);
    }

    @Override
    public Mono<DiffResult> getResult(String id) {
        var foundDiff = Optional.ofNullable(storage.get(id));
        DiffValidator.validateValues(foundDiff, id);
        Diff diff = foundDiff.get();
        String left = diff.getLeft().get();
        String right = diff.getRight().get();
        if (left.length() != right.length()) {
            return Mono.just(DiffResult.builder()
                .status(DiffResultStatus.SIZE_IS_DIFFERENT)
                .build());
        }
        var offsets = DiffHelper.getOffsets(left, right);
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
}
