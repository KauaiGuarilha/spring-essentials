package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.builder.UsersBuilder;
import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.dto.UserDTO;
import com.example.springessentialssenders.model.entity.Users;
import com.example.springessentialssenders.model.exceptions.ResourceNotFoundException;
import com.example.springessentialssenders.model.repository.UsersReporitory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired private UsersBuilder builder;
    @Autowired private UsersReporitory repository;

    public UserDTO saveUpdate(UserDTO userDTO){
        try{
            if (Objects.isNull(userDTO.getId())) return saveUser(userDTO);

            Users user = builder.toUsers(userDTO);
            Optional<Users> optional = repository.findById(user.getId());

            Users db = new Users();
            if (optional.isPresent()) db = optional.get();

            db.setName(userDTO.getName());
            db.setPassword(cryptPassword(userDTO.getPassword()));
            db.setAdmin(userDTO.isAdmin());

            return builder.toResponse(db);
        } catch (Exception e){
            log.error("There was a generic problem when trying to save or update the user.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    private UserDTO saveUser(UserDTO userDTO){
        userDTO.setPassword(cryptPassword(userDTO.getPassword()));
        return builder.toResponse(repository.save(builder.toUsers(userDTO)));
    }

    private String cryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public UserDTO userById(String id){
        try{
            verifyUserExists(id);
            Users user = repository.findByUserId(UUID.fromString(id));
            if (Objects.isNull(user))
                throw new ResourceNotFoundException("User not found for ID");

            return builder.toResponse(user);
        } catch (Exception e){
            log.error("There was a generic problem when trying to return user by id.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    private void verifyUserExists(String id) {
        if (Objects.isNull(UUID.fromString(id)))
            throw new ResourceNotFoundException("User not found for UUID");
    }

    public void delete(String id) {
        try {
            verifyUserExists(id);
            repository.deleteById(UUID.fromString(id));
        } catch (Exception e){
            log.error("There was a generic problem when trying to delete users by id.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    public Page<Users> listAll(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (Exception e){
            log.error("There was a generic problem when trying to list all user.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }
}
