package com.ae.authentication.usecase.user;

import com.ae.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository < User, Long > {
        /**
         *  Get user by username
         * @param userName of user
         * @return User found
         */
        public User findByUserName ( final String userName);
}
