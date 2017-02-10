package com.edvantis.training.parking.models;

import java.time.LocalDate;

/**
 * Created by taras.fihurnyak on 2/2/2017.
 */
public class Owner {

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dob;

    public Owner() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDOB() {
        return dob;
    }

    public void setDOB(LocalDate age) {
        this.dob = age;
    }
}
