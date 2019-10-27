package ru.serdyuk.diff.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    /**
     * Method put first value for comparing
     * @param id - any unique string, need for update current value or find result
     * @param data - string base64 encoded value for comparing
     * @return
     */
    @PostMapping(value = "/{id}/left", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DiffPutValueResult> left(@PathVariable("id") String id, @RequestBody String data) {
        return diffService.putLeft(id, data)
            .map(e -> DiffPutValueResult.builder().id(e).build());
    }

    /**
     * Method put second value for comparing
     * @param id - any unique string, need for update current value or find result
     * @param data - string base64 encoded value for comparing
     * @return
     */
    @PostMapping(value = "/{id}/right", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DiffPutValueResult> right(@PathVariable("id") String id, @RequestBody String data) {
        return diffService.putRight(id, data)
            .map(e -> DiffPutValueResult.builder().id(e).build());
    }

    /**
     * Method get comparing result between first value and second with the same string id
     * @param id - any unique string, need for find comparing result
     * @return
     */
    @GetMapping(value = "/{id}")
    public Mono<DiffResult> result(@PathVariable("id") String id) {
        return diffService.getResult(id);
    }
}
