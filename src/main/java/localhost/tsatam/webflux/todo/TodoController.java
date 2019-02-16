package localhost.tsatam.webflux.todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TodoController {
    private TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/mvc/todo")
    public Flux<Todo> listTodos() {
        return repository.findAll();
    }

    @GetMapping("/mvc/todo/{id}")
    public Mono<Todo> getTodo(@PathVariable("id") String id) {
        return repository.findById(Long.parseLong(id))
            .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/mvc/todo")
    public Mono<Todo> addTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }
}
