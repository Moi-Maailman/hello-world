package fi.haagahelia.hello_world.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findByAnswerOptionQuestionId(Long questionId);
}