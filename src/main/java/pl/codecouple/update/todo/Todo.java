package pl.codecouple.update.todo;

import lombok.Data;
import org.springframework.util.Assert;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
public class Todo {

    private String title;

    private String description;

    public Todo(String title, String description) {
        Assert.notNull(title, "Title cannot be null!");
        Assert.hasLength(title, "Title cannot be empty!");
        Assert.notNull(description, "Description cannot be null!");
        Assert.hasLength(description, "Description cannot be empty!");
        this.title = title;
        this.description = description;
    }
}
