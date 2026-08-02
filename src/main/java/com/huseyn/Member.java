package com.huseyn;

import java.util.Random;

public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;

    public Member() {}
    public Member(String id, String firstName, String lastName, int age, String email) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
        setEmail(email);
    }

    public Member(String firstName, String lastName, int age, String email) {
        setId();
        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
        setEmail(email);
    }
    private void setId() {
        Random random = new Random();
        this.id = String.valueOf(100000 + random.nextInt(900000));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
