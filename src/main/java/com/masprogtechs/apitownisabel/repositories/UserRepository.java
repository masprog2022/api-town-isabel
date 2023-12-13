package com.masprogtechs.apitownisabel.repositories;

import com.masprogtechs.apitownisabel.enums.Role;
import com.masprogtechs.apitownisabel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u.role from User u where u.username like :username")
    Role findRoleByUsername(String username);
}
