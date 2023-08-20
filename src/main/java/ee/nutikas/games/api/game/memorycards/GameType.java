package ee.nutikas.games.api.game.memorycards;

import java.util.Arrays;

public enum GameType {

    MEMORY_MATCH;

    public static GameType fromString(String gameType) {
        return Arrays.stream(GameType.values())
                .filter(gt -> gt.name().equals(gameType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Game Type"));
    }

}
