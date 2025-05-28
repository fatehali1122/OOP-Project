package com.bloodBank;
public class User {
    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private String contact;
    private String city;

    public User(String name, int age, String gender, String bloodGroup, String contact, String city) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.contact = contact;
        this.city = city;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    public String getBloodGroup() {

        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {

        this.bloodGroup = bloodGroup;
    }

    public String getContact() {

        return contact;
    }

    public void setContact(String contact) {

        this.contact = contact;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }
}