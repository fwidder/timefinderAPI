package com.github.fwidder.timeFinder2.dao;

import com.github.fwidder.timeFinder2.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);

    ApplicationUser findByEmail(String email);
}