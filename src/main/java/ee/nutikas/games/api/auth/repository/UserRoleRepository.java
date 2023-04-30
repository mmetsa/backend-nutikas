package ee.nutikas.games.api.auth.repository;

import ee.nutikas.games.api.auth.model.UserRoleSchoolClassModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleSchoolClassModel, Long> {

    List<UserRoleSchoolClassModel> findAllByClassModelId(Long classId);

    List<UserRoleSchoolClassModel> findAllBySchoolIdAndDisabled(Long schoolId, Boolean disabled);
}
