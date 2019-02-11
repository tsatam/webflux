package localhost.tsatam.webflux.repository;

import localhost.tsatam.webflux.entity.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TodoRepositoryTest {
    private TodoRepository repository;
    private DatabaseClient client;

    @Autowired
    TodoRepositoryTest(TodoRepository repository, DatabaseClient databaseClient) {
        this.repository = repository;
        this.client = databaseClient;
    }

    @BeforeEach
    void clearDatabase() {
        client.execute()
            .sql("DELETE FROM todo")
            .then()
            .block();
    }

    @Test
    void findAll_returnsNoEntriesWhenNoneAreAdded() {
        var result = repository.findAll();

        StepVerifier.create(result)
            .expectComplete()
            .verify();
    }

    @Test
    void findAll_returnsElementsSavedToDatabase() {
        this.client.insert()
            .into(Todo.class)
            .using(new Todo("notDone", false))
            .then()
            .block();

        var result = repository.findAll();

        StepVerifier.create(result)
            .expectNextMatches(t -> t.getName().equals("notDone") && !t.isCompleted())
            .expectComplete()
            .verify();
    }

    @Test
    void save_savesTodoToDatabase() {
        var todo = new Todo("something I should do instead", false);

        var result = repository.save(todo).block();

        this.client.select()
            .from(Todo.class)
            .fetch()
            .all()
            .as(StepVerifier::create)
            .expectNextMatches(t -> t.getName().equals("something I should do instead") && !t.isCompleted() && t.getId().equals(result.getId()))
            .verifyComplete();
    }
}
