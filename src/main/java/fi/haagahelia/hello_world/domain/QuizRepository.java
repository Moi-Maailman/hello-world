package fi.haagahelia.hello_world.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz, Long> {

    List<Quiz> findByPublishedTrue();
}