package com.prod.clients;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student{
    private Long id;
    private Integer age;
    private String name;
    private String grade;
    private String email;
}
