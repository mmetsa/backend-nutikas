package ee.nutikas.games.api.school.repository;

import ee.nutikas.games.api.school.model.SchoolCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolCodeRepository extends JpaRepository<SchoolCodeModel, Long> {

    Optional<SchoolCodeModel> findByCode(String code);

}
