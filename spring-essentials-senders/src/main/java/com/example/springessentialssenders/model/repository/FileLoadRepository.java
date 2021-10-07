package com.example.springessentialssenders.model.repository;

import com.example.springessentialssenders.model.entity.FileLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileLoadRepository extends JpaRepository<FileLoad, UUID> {}
