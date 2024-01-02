package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

