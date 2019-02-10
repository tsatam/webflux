package localhost.tsatam.webflux.entity;

import org.springframework.data.annotation.Id;

public class Todo {
    @Id
    private Long id;
    private String name;
    private boolean isCompleted;

    public Todo() {}

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
}
