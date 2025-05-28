package com.bloodBank;
import java.util.*;

public class DataManager {
    private List<Donor> donors;
    private List<Acceptor> acceptors;

    public DataManager() {
        donors = FileHandler.loadDonors();
        acceptors = FileHandler.loadAcceptors();
    }

    public void addDonor(Donor donor) {
        donors.add(donor);
        FileHandler.saveDonors(donors);
    }

    public void updateDonor(Donor updatedDonor) {
        for (int i = 0; i < donors.size(); i++) {
            Donor d = donors.get(i);
            if (d.getContact().equals(updatedDonor.getContact())) {
                donors.set(i, updatedDonor);
                FileHandler.saveDonors(donors);
                return;
            }
        }
    }

    public void addAcceptor(Acceptor acceptor) {
        acceptors.add(acceptor);
        FileHandler.saveAcceptors(acceptors);
    }

    public List<Donor> getDonors() {

        return donors;
    }

    public List<Acceptor> getAcceptors() {

        return acceptors;
    }

    public static class AcceptorService {
        private DataManager dataManager;

        public AcceptorService(DataManager dataManager) {

            this.dataManager = dataManager;
        }

        public void registerAcceptor(Acceptor acceptor) {
            dataManager.addAcceptor(acceptor);
        }

        public List<Acceptor> getAllAcceptors() {

            return dataManager.getAcceptors();
        }
    }
}
