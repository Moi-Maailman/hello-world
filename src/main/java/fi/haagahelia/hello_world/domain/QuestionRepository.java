package fi.haagahelia.hello_world.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findByQuizId(Long quizId);
}