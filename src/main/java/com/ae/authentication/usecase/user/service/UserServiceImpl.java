package com.ae.authentication.usecase.user.service;

import com.ae.authentication.entity.UserEntity;
import com.ae.authentication.usecase.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = getUserEntity(username);
        log.debug("username: " + username);
        if (userEntity == null) {
            log.debug("User not exist");
            throw new UsernameNotFoundException("User " + username + " not exist");
        }
        return getUser(username);
    }

    private User getUser(final String userName) {
        UserEntity user = getUserEntity(userName);
        return new User(
                user.getUserName(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getGrantedAuthorities(user)
                );
    }

    private UserEntity getUserEntity(final String userName) {
        return userRepository.findByUserName(userName);
    }

    private List<GrantedAuthority> getGrantedAuthorities(final UserEntity user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek( authority -> log.debug(authority.getAuthority()))
                .collect(Collectors.toList());
    }

}
