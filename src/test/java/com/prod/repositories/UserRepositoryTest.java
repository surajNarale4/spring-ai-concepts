package com.prod.repositories;

import com.prod.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static User user;



    @BeforeAll
    static void setUp(){
        user = User.builder()
                .id(1L)
                .email("test@email.com")
                .build();
    }

    @Test
    void findByEmail_whenEmailIsValid_thenReturnUser() {
        //arrange
        userRepository.save(user);
        //when
        assertThat(userRepository.findByEmail(user.getEmail())).hasValue(user);
        //then
    }

    @Test
    void findByEmail_whenEmailIsInValid_thenReturnEmptyUser(){
        userRepository.save(user);

    }

}