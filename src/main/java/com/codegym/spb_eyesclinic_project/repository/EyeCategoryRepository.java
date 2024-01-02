package com.codegym.spb_eyesclinic_project.repository;

import com.codegym.spb_eyesclinic_project.domain.EyeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EyeCategoryRepository extends JpaRepository<EyeCategory, Long> {
}
