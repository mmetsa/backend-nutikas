package ee.nutikas.games.api.school.repository;

import ee.nutikas.games.api.school.model.SchoolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolModel, Long> { }
