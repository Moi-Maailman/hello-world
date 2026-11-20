package fi.haagahelia.hello_world.dto;

import fi.haagahelia.hello_world.domain.Difficulty;

public record QuestionResultDTO(
        Long questionId,
        String questionText,
        Difficulty questionDifficulty,
        long totalAnswers,
        long correctAnswers,
        long wrongAnswers) {
}