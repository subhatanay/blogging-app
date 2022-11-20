package com.scaler.bloggingapp.users.dao;

import com.scaler.bloggingapp.users.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public boolean existsByEmailId(String emailId);

    public boolean existsByUsername(String username);

    public Optional<UserEntity> findByUsername(String username);

    public Optional<UserEntity> findByEmailId(String emailId);

    @Query(nativeQuery = true,
            value = "select * from users where user_id in (select follower_id from follow where followed_id=?1) order by user_id \n-- #page\n",
            countQuery = "select count(*) from users where user_id in (select follower_id from follow where followed_id=?1) order by user_id")
    public Page<UserEntity> findFollowersByUserId(Long userId, Pageable page);

    @Query(nativeQuery = true,
                value = "select * from users where user_id in (select followed_id from follow where follower_id=?1) order by user_id \n-- #page\n",
                countQuery = "select * from users where user_id in (select followed_id from follow where follower_id=?1) order by user_id")
    public Page<UserEntity> findFollowingsByUserId(Long userId, Pageable page);

}
