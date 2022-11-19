package com.scaler.bloggingapp.users.dao;

import com.scaler.bloggingapp.users.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RolesEntity,Long> {

    public Optional<RolesEntity> findByRoleName(String roleName);

}
