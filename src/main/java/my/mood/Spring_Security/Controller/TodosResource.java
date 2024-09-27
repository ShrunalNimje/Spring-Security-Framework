package my.mood.Spring_Security.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodosResource {
	
	private static List<Todo> todos = List.of(new Todo("Shrunal", "Learn Spring Security"),
			new Todo("Shrunal", "Learn Spring Boot"));
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/todos")
	public List<Todo> RetrieveAllTodos() {
		return todos;
	}
	
	@GetMapping("/todos/{username}")
	public Todo RetrieveTodo(@PathVariable("username") String username) {
		return todos.get(0);
	}
	
	@PostMapping("/todos/{username}")
	public void CreateTodo(@PathVariable String username, @RequestBody Todo todos) {
		logger.info("Creates {} for username {}", todos, username);
	}
	
}

record Todo(String username, String description) {
	
}