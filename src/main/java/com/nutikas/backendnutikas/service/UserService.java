package com.nutikas.backendnutikas.service;

import com.nutikas.backendnutikas.auth.UserDetailsService;
import com.nutikas.backendnutikas.dto.QuestionSetDTO;
import com.nutikas.backendnutikas.dto.UserResponse;
import com.nutikas.backendnutikas.mapper.QuestionSetMapper;
import com.nutikas.backendnutikas.mapper.UserMapper;
import com.nutikas.backendnutikas.model.RoleModel;
import com.nutikas.backendnutikas.model.UserModel;
import com.nutikas.backendnutikas.repository.RoleRepository;
import com.nutikas.backendnutikas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.error("User with username {} not found", username);
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.get().getUsername(), user.get().getPassword(), authorities);
    }

    public UserModel saveUser(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public RoleModel saveRole(RoleModel role) {
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        var user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            var role = roleRepository.findByName(roleName);
            user.get().getRoles().add(role);
        }
    }

    public UserResponse getUser(Long id) {
        return UserMapper.INSTANCE.dtoToResponse(
                UserMapper.INSTANCE.modelToDTO(
                        userRepository.findById(id).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND"))
                ));
    }

    public UserResponse getUser(String username) {
        return UserMapper.INSTANCE.dtoToResponse(UserMapper.INSTANCE.modelToDTO(
                userRepository.findByUsername(username).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND")))
        );
    }

    public void addQuiestionSetToUser(String username, QuestionSetDTO questionSet) {

        var user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            user.get().getQuestionSets().add(QuestionSetMapper.INSTANCE.dtoToModel(questionSet));
            userRepository.save(user.get());
        }
    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }
}
