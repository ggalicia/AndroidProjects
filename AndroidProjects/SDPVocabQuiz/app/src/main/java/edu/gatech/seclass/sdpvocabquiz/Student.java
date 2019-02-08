package edu.gatech.seclass.sdpvocabquiz;

/**
 * Created by Dylan Barrett on 10/8/2018.
 */

public class Student {

    String username;
    String major;
    Seniority seniority;
    String email;

    public String getUsername() {
        return username;
    }

    public String getMajor() {
        return major;
    }

    public Seniority getSeniority() {
        return seniority;
    }

    public String getEmail() {
        return email;
    }


    public Student(String username, String major, Seniority seniority, String email) {
        this.username = username;
        this.major = major;
        this.seniority = seniority;
        this.email = email;
    }

    @Override
    public String toString() {
        return username;
    }
}

enum Seniority
{
    Freshman("Freshman"), Sophomore("Sophomore"), Junior("Junior"), Senior("Senior"), Graduate("Graduate");

    private String friendlyName;

    Seniority(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override public String toString(){
        return friendlyName;
    }
}
