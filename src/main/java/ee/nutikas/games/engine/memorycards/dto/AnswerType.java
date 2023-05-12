package ee.nutikas.games.engine.memorycards.dto;

import java.util.Arrays;

public enum AnswerType {

    NUMBER,
    PLAIN_TEXT,
    CHOOSE_ONE,
    CHOOSE_MULTIPLE;

    public AnswerType fromString(String answerType) {
        return Arrays.stream(AnswerType.values())
                .filter(gt -> gt.name().equals(answerType))
                .findFirst()
                .orElseThrow();
    }

}
