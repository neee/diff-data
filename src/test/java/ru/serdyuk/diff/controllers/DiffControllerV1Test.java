package ru.serdyuk.diff.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.serdyuk.diff.TestData;
import ru.serdyuk.diff.services.DiffService;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiffControllerV1Test {

    @Autowired
    WebTestClient client;

    @Autowired
    DiffService diffService;

    @Test
    void callLeftSuccessful() {
        client.post().uri(TestData.DIFF_CONTROLLER_PATH + TestData.ID_1 + TestData.LEFT_PATH)
            .contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromValue(TestData.valueWithEqualData().getLeft().get()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("{\"id\":\"1\"}");
    }

    @Test
    void callRightSuccessful() {
        client.post().uri(TestData.DIFF_CONTROLLER_PATH + TestData.ID_1 + TestData.RIGHT_PATH)
            .contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromValue(TestData.valueWithEqualData().getRight().get()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("{\"id\":\"1\"}");
    }

    @Test
    void gettingResultWithUnExistsId() {
        client.get().uri(TestData.DIFF_CONTROLLER_PATH + TestData.UN_EXISTS_ID)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody()
            .json("{\"status\":\"ERROR\", \"offsets\":[], \"message\":\"Key with id 1, doesn't exists\"}");
    }

    @Test
    void gettingSuccessfulResult() {
        client.post().uri(TestData.DIFF_CONTROLLER_PATH + TestData.ID_1 + TestData.LEFT_PATH)
            .contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromValue(TestData.valueWithEqualData().getLeft().get()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("{\"id\":\"1\"}");
        client.post().uri(TestData.DIFF_CONTROLLER_PATH + TestData.ID_1 + TestData.RIGHT_PATH)
            .contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromValue(TestData.valueWithEqualData().getRight().get()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("{\"id\":\"1\"}");
        client.get().uri(TestData.DIFF_CONTROLLER_PATH + TestData.ID_1)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody();
    }
}
