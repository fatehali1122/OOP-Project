package com.bloodBank;
import java.util.Iterator;
import java.util.List;

public class DonorService {
    private DataManager dataManager;

    public DonorService(DataManager dataManager) {

        this.dataManager = dataManager;
    }
    public void reserveDonor(String contact) {
        List<Donor> donors = dataManager.getDonors();
        Donor reserved = null;

        Iterator<Donor> iterator = donors.iterator();
        while (iterator.hasNext()) {
            Donor d = iterator.next();
            if (d.getContact().equals(contact)) {
                reserved = d;
                iterator.remove();
                break;
            }
        }

        if (reserved != null) {
            List<Donor> reservedList = FileHandler.loadReservedDonors();
            reservedList.add(reserved);
            FileHandler.saveDonors(donors);
            FileHandler.saveReservedDonors(reservedList);
        }
    }



    public void registerDonor(Donor donor) {

        dataManager.addDonor(donor);
    }

    public List<Donor> getAllDonors() {

        return dataManager.getDonors();
    }

    public void updateDonor(Donor updatedDonor) {

        dataManager.updateDonor(updatedDonor);
    }
}

