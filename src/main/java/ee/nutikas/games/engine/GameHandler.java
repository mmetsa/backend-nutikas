package ee.nutikas.games.engine;

import java.util.*;

/**
 * A Base class that will handle all the games
 * @param <T> The GameState type, each Game Type can have a different Game State object holding different values.
 */
public interface GameHandler<T extends Game, T2 extends CreateGameRequest> {

    /**
     * A Map of all the currently ongoing games.
     * The key is the Game ID.
     * The value is a collection of Game State objects.
     */
    Map<UUID, T> getGames();

    /**
     * The method with which a new Game will be created
     * @param request The game request object from which the game will be created.
     *                The request type can be implemented so there are different values that needs to be set.
     *
     * @return UUID of the created game
     */
    UUID addGame(T2 request);

    void publishLatestState(UUID uuid);

}
