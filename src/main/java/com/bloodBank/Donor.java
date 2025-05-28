package com.bloodBank;
import java.time.LocalDate;

public class Donor extends User {
    private LocalDate lastDonationDate;
    private boolean isAvailable;

    public Donor(String name, int age, String gender, String bloodGroup, String contact, String city, LocalDate lastDonationDate, boolean isAvailable) {
        super(name, age, gender, bloodGroup, contact, city);
        this.lastDonationDate = lastDonationDate;
        this.isAvailable = isAvailable;
    }

    public LocalDate getLastDonationDate() {

        return lastDonationDate;
    }

    public void setLastDonationDate(LocalDate lastDonationDate) {

        this.lastDonationDate = lastDonationDate;
    }

    public boolean isAvailable() {

        return isAvailable;
    }

    public void setAvailable(boolean available) {

        isAvailable = available;
    }
}