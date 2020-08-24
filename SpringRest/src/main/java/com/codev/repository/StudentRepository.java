package com.codev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codev.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
