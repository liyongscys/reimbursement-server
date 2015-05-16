package example;

import lombok.Data;

import org.springframework.stereotype.Component;

@Data
@Component
public class Person {

	private String firstName;
	private String lastName;

	public void sayHello() {
		System.out.println("Hello " + firstName + " " + lastName);
	}

}