package com.dark.knight.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {
    private Integer id;
    private String name;
    private String address;
    private Float gpa;
    private String gender;

    public StudentDto() {}

    public StudentDto(Integer id, String name, String address, Float gpa, String gender) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gpa = gpa;
        this.gender = gender;
    }
    public StudentDto(String name, String address, Float gpa, String gender) {
        this.name = name;
        this.address = address;
        this.gpa = gpa;
        this.gender = gender;
    }
}
