package com.bloodBank;
public class Validator {
    public static boolean isValidBloodGroup(String bloodGroup) {
        String[] validGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String bg : validGroups) {
            if (bg.equalsIgnoreCase(bloodGroup)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{10,15}");
    }

}
