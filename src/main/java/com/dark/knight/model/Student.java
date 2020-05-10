package com.dark.knight.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @Column
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="address_id", nullable=false)
    private Address address;
    @Column
    private Float gpa;
    @Column
    @Enumerated
    private Gender gender;
}
