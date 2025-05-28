package com.bloodBank;
import java.util.*;

public class SearchService {
    private DataManager dataManager;

    public SearchService(DataManager dataManager) {

        this.dataManager = dataManager;
    }

    public List<Donor> searchByBloodGroupAndCity(String bloodGroup, String city) {
        List<Donor> result = new ArrayList<>();
        for (Donor donor : dataManager.getDonors()) {
            if (donor.getBloodGroup().equalsIgnoreCase(bloodGroup) && donor.getCity().equalsIgnoreCase(city)) {
                result.add(donor);
            }
        }
        return result;
    }
    public List<Donor> searchByCity(String city) {
        List<Donor> result = new ArrayList<>();
        for (Donor donor : dataManager.getDonors()) {
            if (donor.getCity().equalsIgnoreCase(city)) {
                result.add(donor);
            }
        }
        return result;
    }

    public List<Donor> searchByBloodGroup(String bloodGroup) {
        List<Donor> result = new ArrayList<>();
        for (Donor donor : dataManager.getDonors()) {
            if (donor.getBloodGroup().equalsIgnoreCase(bloodGroup)) {
                result.add(donor);
            }
        }
        return result;
    }

    public List<Donor> getAllAvailableDonors() {
        List<Donor> result = new ArrayList<>();
        for (Donor donor : dataManager.getDonors()) {
            if (donor.isAvailable()) {
                result.add(donor);
            }
        }
        return result;
    }
}