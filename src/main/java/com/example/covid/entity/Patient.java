package com.example.covid.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
public class Patient {

    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "Health status is required")
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth ;


    public Patient() {}

    public Patient(String name, String surname,String status, Date birth) {
        this.name = name;
        this.surname = surname;
        this.status = status;
        this.birth = birth;

    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() { return id; }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() { return name; }

    public void setSurname(String surname) { this.surname = surname; }
    public String getSurname() { return surname; }

    public void setStatus(String status) { this.status = status; }
    public String getStatus() {return status; }

    public void setBirth(Date birth) { this.birth = birth; }
    public Date getBirth() { return birth; }



}