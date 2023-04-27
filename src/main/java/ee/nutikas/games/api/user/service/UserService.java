package ee.nutikas.games.api.user.service;

import ee.nutikas.games.api.auth.model.UserModel;
import ee.nutikas.games.api.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserModel> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

}
