package ee.nutikas.games.api.auth.dto;

import lombok.Data;

@Data
public class UserPoints {

    private Long coins;
    private Long experience;
    private Integer level;
    private Long experienceLeft;
    private Long startingXp;

}
