package fi.haagahelia.hello_world.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AnswerOptionRepository extends CrudRepository<AnswerOption, Long> {

    List<AnswerOption> findByQuestionId(Long questionId);
}