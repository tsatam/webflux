package localhost.tsatam.webflux.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TodoHandlerTest {
    private WebTestClient testClient;
    private TodoRepository repository;

    @Autowired
    TodoHandlerTest(WebTestClient testClient, TodoRepository repository) {
        this.testClient = testClient;
        this.repository = repository;
    }

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll().block();
    }

    @Test
    void listTodos_whenNoTodos_returnsEmptyList() {
        testClient
            .get()
            .uri("/fn/todo")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBodyList(Todo.class)
            .hasSize(0);
    }

    @Test
    void listTodos_whenTodoIsPresent_returnsListWithTodo() {
        var todo = new Todo("do the thing", false);

        repository.save(todo).block();

        testClient
            .get()
            .uri("/fn/todo")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBodyList(Todo.class)
            .hasSize(1)
            .contains(todo);
    }

    @Test
    void getTodo_whenTodoNotPresent_returnsNotFound() {
        testClient
            .get()
            .uri("/fn/todo/42")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void getTodo_whenTodoIsPresent_returnsTodo() {
        var todo = new Todo("do the thing", false);
        todo = repository.save(todo).block();

        testClient
            .get()
            .uri("/fn/todo/" + todo.getId().toString())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBody(Todo.class)
            .isEqualTo(todo);
    }

    @Test
    void addTodo_addsTodo() {
        var todo = new Todo("do the thing", false);

        testClient
            .post()
            .uri("/fn/todo")
            .body(Mono.just(todo), Todo.class)
            .exchange()
            .expectStatus()
            .isOk();

        repository.findAll()
            .as(StepVerifier::create)
            .expectNextMatches(savedTodo -> Objects.equals(savedTodo.getName(), todo.getName()))
            .verifyComplete();
    }
}
