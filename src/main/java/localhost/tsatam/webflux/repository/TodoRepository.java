package localhost.tsatam.webflux.repository;

import localhost.tsatam.webflux.entity.Todo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {}
