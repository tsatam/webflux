package localhost.tsatam.webflux.todo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class TodoHandler {
    private TodoRepository repository;

    public TodoHandler(TodoRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> listTodos(ServerRequest request) {
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(repository.findAll(), Todo.class);
    }

    public Mono<ServerResponse> getTodo(ServerRequest request) {
        long id = Long.parseLong(request.pathVariable("id"));
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                repository.findById(id)
                    .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))),
                Todo.class
            );
    }

    public Mono<ServerResponse> addTodo(ServerRequest request) {
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(repository.saveAll(request.bodyToMono(Todo.class)).last(), Todo.class);
    }
}
