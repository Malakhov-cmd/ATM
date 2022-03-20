package com.server.server.repo;

import com.server.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, Long> {
    User findByUserNameAndPassword(String username, String password);
}
