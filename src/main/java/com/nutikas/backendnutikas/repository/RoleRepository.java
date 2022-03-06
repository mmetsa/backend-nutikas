package com.nutikas.backendnutikas.repository;

import com.nutikas.backendnutikas.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    RoleModel findByName(String roleName);

}
