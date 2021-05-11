package com.javohir.task2.repository;

import com.javohir.task2.entity.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PLRepository extends JpaRepository<ProgrammingLanguage, Integer> {
    boolean existsByName(String name);
}
