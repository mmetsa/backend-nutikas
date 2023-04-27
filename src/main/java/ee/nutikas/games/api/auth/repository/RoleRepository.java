package ee.nutikas.games.api.auth.repository;

import ee.nutikas.games.api.enums.Role;
import ee.nutikas.games.api.auth.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    Optional<RoleModel> findByName(Role name);
}
