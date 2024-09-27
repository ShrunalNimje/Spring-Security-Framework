package my.mood.Spring_Security.HelloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResource {
	
	@GetMapping("/hello-world")
	public String HelloWorld() {
		return "Hello World!";
	}
}