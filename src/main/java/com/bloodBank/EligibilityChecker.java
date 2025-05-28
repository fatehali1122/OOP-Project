package com.bloodBank;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EligibilityChecker {
    public static boolean isEligible(LocalDate lastDonationDate) {
        return ChronoUnit.DAYS.between(lastDonationDate, LocalDate.now()) >= 90;
    }
}