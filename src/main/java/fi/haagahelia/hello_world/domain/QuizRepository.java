package fi.haagahelia.hello_world.domain;

import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz, Long> {

}