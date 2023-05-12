package ee.nutikas.games.engine.memorycards.dto;

import lombok.Data;

@Data
public class MemoryCard {

    private Long id;
    private Long questionId;
    private String text;

    private Boolean answered = false;


}
