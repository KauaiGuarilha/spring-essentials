package com.example.springessentials.model.repository;

import com.example.springessentials.model.entity.FileLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileLoadRepository extends JpaRepository<FileLoad, UUID> {}
