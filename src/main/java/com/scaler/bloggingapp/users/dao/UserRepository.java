package com.scaler.bloggingapp.users.dao;

import com.scaler.bloggingapp.users.entity.UserEnitity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEnitity, Long> {

    public boolean existsByEmailId(String emailId);

    public boolean existsByUsername(String username);

    public Optional<UserEnitity> findByUsername(String username);

    public Optional<UserEnitity> findByEmailId(String emailId);

}
