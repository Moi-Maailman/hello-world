package fi.haagahelia.hello_world.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz, Long> {

    // Newest quizzes first, for the front page list
    List<Quiz> findAllByOrderByCreatedAtDesc();
}