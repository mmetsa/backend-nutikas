package ee.nutikas.games.api.game.repository;

import ee.nutikas.games.api.game.model.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameModel, Long> {

    List<GameModel> findByCreatorId(Long creatorId);

    List<GameModel> findByClassIdIsIn(List<Long> classIds);

}
