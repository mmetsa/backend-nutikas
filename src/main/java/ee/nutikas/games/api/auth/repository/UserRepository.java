package ee.nutikas.games.api.auth.repository;

import ee.nutikas.games.api.auth.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByNickname(String nickname);

    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);

}
