package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.dto.UserDTOResponse;
import com.example.springessentialssenders.model.exceptions.UUIDNotFoundException;
import com.example.springessentialssenders.model.exceptions.UserNotFoundException;
import com.example.springessentialssenders.model.exceptions.UsernameAlreadyInUseException;
import com.example.springessentialssenders.model.parser.UsersParser;
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

    @Autowired private UsersParser parser;
    @Autowired private UsersReporitory repository;

    public UserDTOResponse saveUpdate(UserDTO userDTO){
        try {
            if (Objects.isNull(userDTO.getId())) return saveUser(userDTO);

            Users user = parser.toUsers(userDTO);
            Optional<Users> optional = repository.findById(user.getId());

            Users db = new Users();
            if (optional.isPresent()) db = optional.get();

            db.setName(userDTO.getName());
            db.setPassword(cryptPassword(userDTO.getPassword()));
            db.setAdmin(userDTO.getAdmin());

            return parser.toResponse(db);
        } catch (UsernameAlreadyInUseException e){
            throw e;
        } catch (Exception e){
            log.error("There was a generic problem when trying to save or update the user.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    private UserDTOResponse saveUser(UserDTO userDTO){
        verifyUsername(userDTO);

        userDTO.setPassword(cryptPassword(userDTO.getPassword()));
        return parser.toResponse(repository.save(parser.toUsers(userDTO)));
    }

    private String cryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private void verifyUsername(UserDTO userDTO){
        Users user = repository.findByUsername(userDTO.getUsername());

        if (Objects.isNull(user)) return;

        if (user.getUsername().equals(userDTO.getUsername()))
            throw new UsernameAlreadyInUseException(EValidation.USERNAME_ALREADY_USING, userDTO.getUsername());
    }

    public UserDTOResponse userById(String id){
        try {
            verifyUUID(id);
            Users user = repository.findByUserId(UUID.fromString(id));
            if (Objects.isNull(user))
                throw new UserNotFoundException(EValidation.USER_NOT_FOUND_FOR_ID, id);

            return parser.toResponse(user);
        } catch (UserNotFoundException | UUIDNotFoundException e){
            throw e;
        } catch (Exception e){
            log.error("There was a generic problem when trying to return user by id.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }

    private void verifyUUID(String id) {
        if (Objects.isNull(UUID.fromString(id)))
            throw new UUIDNotFoundException(EValidation.UUID_NOT_FOUND, id);
    }

    public void delete(String id) {
        try {
            verifyUUID(id);
            repository.deleteById(UUID.fromString(id));
        } catch (UUIDNotFoundException e){
            throw e;
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
