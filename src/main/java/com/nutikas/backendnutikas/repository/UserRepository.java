package com.nutikas.backendnutikas.repository;

import com.nutikas.backendnutikas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByUsername(String username);

}
