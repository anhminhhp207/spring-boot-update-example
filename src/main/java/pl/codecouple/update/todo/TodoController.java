package pl.codecouple.update.todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Krzysztof Chruściel.
 */
@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Todo created!")
    public void addNewTodo(@RequestBody Todo todo){
        todoService.addNewTodo(todo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Todo updated!")
    public void updateTodo(@RequestBody Todo todo, @PathVariable Long id) throws TodoNotFound {
        Todo todoById = todoService.getTodoById(id);
        if(todoById == null){
            throw new TodoNotFound(String.format("Todo with ID %d not exists!", id));
        }
        todoService.update(todo);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Todo partial updated!")
    public void updateTodo(@RequestBody Map<String, Object> updates, @PathVariable Long id) throws TodoNotFound {
        Todo todo = todoService.getTodoById(id);
        if (todo == null) {
            throw new TodoNotFound(String.format("Todo with ID %d not exists!", id));
        }
        partialUpdate(todo, updates);
    }

    private void partialUpdate(Todo todo, Map<String, Object> updates) {
        if (updates.containsKey("title")) {
            todo.setTitle((String) updates.get("title"));
        }
        if (updates.containsKey("description")) {
            todo.setDescription((String) updates.get("description"));
        }
        todoService.partialUpdated(todo);
    }
}
