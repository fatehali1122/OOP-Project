package com.bloodBank;
import java.util.*;

public class AcceptorService {
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