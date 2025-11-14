package com.schoolplus.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.schoolplus.back.model.User;

public interface UserRepository extends JpaRepository<User, String> {
   User findByLogin(String login);
}
