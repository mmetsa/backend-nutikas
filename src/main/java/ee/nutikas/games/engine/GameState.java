package ee.nutikas.games.engine;

import java.util.List;

public interface GameState<T extends GamePlayer> {

    List<T> getPlayers();

    /**
     * Time left for playing the game in seconds.
     */
    Integer getTime();
}
