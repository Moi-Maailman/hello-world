package fi.haagahelia.hello_world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.hello_world.domain.Quiz;
import fi.haagahelia.hello_world.domain.QuizRepository;

@SpringBootApplication
public class HelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(QuizRepository quizRepository) {
		return (args) -> {
			if (quizRepository.count() == 0) {
				quizRepository.save(new Quiz("Java Basics", "Test your basic Java knowledge", "SOF001", true));
				quizRepository.save(new Quiz("Database Basics", "Test your database knowledge", "SOF002", false));
			}
		};
	}

}
