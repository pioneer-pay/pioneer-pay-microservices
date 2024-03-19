package com.wu.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wu.userservice.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public User findByEmailId(String emailId);

    public User findByEmailIdAndPassword(String emailId,String password);

    public User findByUserId(String id);
}
