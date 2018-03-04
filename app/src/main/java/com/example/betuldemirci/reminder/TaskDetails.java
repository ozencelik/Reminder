package com.example.betuldemirci.reminder;



/**
 * Created by Betul Demirci on 3/4/2018.
 */

public class TaskDetails {


    private String name;
    private String phoneNumber;

    public TaskDetails() {
        // This is default constructor.
    }

    public String getStudentName() {

        return name;
    }

    public void setStudentName(String name) {

        this.name = name;
    }

    public String getStudentPhoneNumber() {

        return phoneNumber;
    }

    public void setStudentPhoneNumber(String phonenumber) {

        this.phoneNumber = phonenumber;
    }

}