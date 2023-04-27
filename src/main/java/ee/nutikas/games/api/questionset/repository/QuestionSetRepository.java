package ee.nutikas.games.api.questionset.repository;

import ee.nutikas.games.api.questionset.model.QuestionSetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionSetRepository extends JpaRepository<QuestionSetModel, Long> {

    List<QuestionSetModel> findAllByOwnerId(Long ownerId);

}



