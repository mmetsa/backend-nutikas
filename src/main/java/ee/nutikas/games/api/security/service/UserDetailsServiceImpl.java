package ee.nutikas.games.api.security.service;

import ee.nutikas.games.api.auth.model.UserModel;
import ee.nutikas.games.api.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with nickname %s not found", username)));
        return UserDetailsImpl.build(user);
    }
}
