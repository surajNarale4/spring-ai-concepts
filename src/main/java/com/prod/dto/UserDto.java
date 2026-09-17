package com.prod.dto;

import com.prod.entities.enums.Roles;
import lombok.*;

import java.io.Serializable;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    private String email;
    private String password;
    private String name;

    private Set<Roles> roles;
}