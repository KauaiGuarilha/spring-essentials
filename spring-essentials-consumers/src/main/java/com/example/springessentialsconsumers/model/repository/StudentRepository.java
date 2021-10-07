package com.example.springessentialsconsumers.model.repository;

import com.example.springessentialsconsumers.model.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, UUID> {

    @Query("select s from Student s where s.id = ?1")
    Student findByStudentId(UUID id);

    List<Student> findByNameIgnoreCaseContaining(String name);
}
