package com.ae.authentication.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Role  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, length = 30)
    private String name;


}
