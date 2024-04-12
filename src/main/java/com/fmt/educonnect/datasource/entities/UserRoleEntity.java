package com.fmt.educonnect.datasource.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

}
