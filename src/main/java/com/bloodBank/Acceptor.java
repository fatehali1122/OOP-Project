package com.bloodBank;

import java.time.LocalDate;

public class Acceptor extends User {
    private LocalDate requiredDate;

    public Acceptor(String name, int age, String gender, String bloodGroup, String contact, String city, LocalDate requiredDate) {
        super(name, age, gender, bloodGroup, contact, city);
        this.requiredDate = requiredDate;
    }

    public LocalDate getRequiredDate() {

        return requiredDate;
    }

    public void setRequiredDate(LocalDate requiredDate) {

        this.requiredDate = requiredDate;
    }
}