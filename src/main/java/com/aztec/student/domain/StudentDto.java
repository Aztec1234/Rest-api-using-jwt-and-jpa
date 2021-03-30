package com.aztec.student.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class StudentDto{

    private String firstName;

    private String middleName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    public StudentDto(String firstName,String middleName,String lastName,LocalDate dateOfBirth,String gender) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.middleName=middleName;
        this.dateOfBirth=dateOfBirth;
        this.gender=gender;
    }
}
