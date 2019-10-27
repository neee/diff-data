package ru.serdyuk.diff.services;

import reactor.core.publisher.Mono;
import ru.serdyuk.diff.entities.DiffResult;

/**
 * Service interface - put/get to storage first/second values
 */
public interface DiffService {

    Mono<String> putLeft(String id, String data);

    Mono<String> putRight(String id, String data);

    Mono<DiffResult> getResult(String id);
}
