package com.ae.authentication.usecase.user.repository;

import com.ae.authentication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository < UserEntity, Long > {
        /**
         *  Get user by username
         * @param userName of user
         * @return User found
         */
        public UserEntity findByUserName ( final String userName);
}
