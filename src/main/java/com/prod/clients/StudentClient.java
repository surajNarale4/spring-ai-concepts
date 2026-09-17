package com.prod.clients;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentClient {

    private String message;
    private List<Student> students;


}


