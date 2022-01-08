package com.example.springessentialssenders.model.repository;

import com.example.springessentialssenders.model.entity.Student;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, UUID> {

    @Query("select s from Student s where s.id = ?1")
    Student findByStudentId(UUID id);

    @Query("select s from Student s where s.id = ?1")
    Optional<Student> findByStudentIdOptional(UUID id);

    List<Student> findByNameIgnoreCaseContaining(String name);
}
