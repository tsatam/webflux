package localhost.tsatam.webflux.todo;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Todo {
    @Id
    private Long id;
    private String name;
    private boolean isCompleted;

    public Todo() {}

    public Todo(String name, boolean isCompleted) {
        this(null, name, isCompleted);
    }

    public Todo(Long id, String name, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean isCompleted() { return isCompleted; }

    public void setCompleted(boolean completed) { isCompleted = completed; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return isCompleted == todo.isCompleted &&
            Objects.equals(id, todo.id) &&
            Objects.equals(name, todo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isCompleted);
    }
}
