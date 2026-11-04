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

			Quiz quiz1 = new Quiz(
					"Java Basics",
					"Test your basic Java knowledge",
					"SOF001",
					true);

			Quiz quiz2 = new Quiz(
					"Database Basics",
					"Test your database knowledge",
					"SOF002",
					false);

			quizRepository.save(quiz1);
			quizRepository.save(quiz2);
		};
	}

}
