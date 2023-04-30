package ee.nutikas.games.api.school.repository;

import ee.nutikas.games.api.school.model.ClassModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassModel, Long> {

    List<ClassModel> findBySchoolId(Long schoolId);
}
