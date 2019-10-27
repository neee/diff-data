package ru.serdyuk.diff.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.serdyuk.diff.entities.DiffPutValueResult;
import ru.serdyuk.diff.entities.DiffResult;
import ru.serdyuk.diff.services.DiffService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/diff")
public class DiffControllerV1 {

    private final DiffService diffService;

    @PostMapping(value = "/{id}/left", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DiffPutValueResult> left(@PathVariable("id") String id, @RequestBody String data) {
        return diffService.putLeft(id, data)
            .map(e -> DiffPutValueResult.builder().id(e).build());
    }

    @PostMapping(value = "/{id}/right", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DiffPutValueResult> right(@PathVariable("id") String id, @RequestBody String data) {
        return diffService.putRight(id, data)
            .map(e -> DiffPutValueResult.builder().id(e).build());
    }

    @GetMapping(value = "/{id}")
    public Mono<DiffResult> result(@PathVariable("id") String id) {
        return diffService.getResult(id);
    }
}
