package com.ae.authentication.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "username", unique = true, length = 25)
        private String userName;

        @Column(name = "password", length = 60)
        private String password;

        @Column(name = "enabled")
        private Boolean enabled;

        @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private List<RoleEntity> roles;

}
