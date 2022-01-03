package com.example.springessentialssenders.model.repository;

import com.example.springessentialssenders.model.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersReporitory extends PagingAndSortingRepository<Users, UUID> {

    @Query("select s from Users s where s.id = ?1")
    Users findByUserId(UUID id);

    Users findByUsername(String username);
}
