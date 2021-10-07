package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.entity.Users;
import com.example.springessentialssenders.model.exceptions.EssentialsRuntimeException;
import com.example.springessentialssenders.model.repository.UsersReporitory;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUsersService implements UserDetailsService {

    @Autowired private UsersReporitory reporitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users users =
                    Optional.ofNullable(reporitory.findByUsername(username))
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            List<GrantedAuthority> authorityListAdmin =
                    AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
            List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
            return new User(
                    users.getUsername(),
                    users.getPassword(),
                    users.isAdmin() ? authorityListAdmin : authorityListUser);
        } catch (Exception e){
            log.error("There was a generic problem when trying to load the user by Username", ExceptionUtils.getStackTrace(e));
            throw new EssentialsRuntimeException(EValidation.NOT_IDENTIFIED.getDescription());
        }
    }
}
