package com.example.springessentialssenders.model.repository;

import com.example.springessentialssenders.model.entity.Users;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersReporitory extends PagingAndSortingRepository<Users, UUID> {

    Users findByUsername(String username);
}
