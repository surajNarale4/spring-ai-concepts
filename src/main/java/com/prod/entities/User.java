package com.prod.entities;


import com.prod.entities.enums.Permission;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import  com.prod.entities.enums.Roles;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column(unique = true)
    private String email;
    private String password;


    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream()
                .map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet());
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
