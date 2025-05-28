package com.bloodBank;
import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class FileHandler {
    private static final String DONOR_FILE = "donors.txt";
    private static final String ACCEPTOR_FILE = "acceptors.txt";

    public static void saveDonors(List<Donor> donors) {
        List<String> lines = new ArrayList<>();
        for (Donor d : donors) {
            lines.add(String.join(",",
                    d.getName(),
                    String.valueOf(d.getAge()),
                    d.getGender(),
                    d.getBloodGroup(),
                    d.getContact(),
                    d.getCity(),
                    d.getLastDonationDate().toString(),  // format: yyyy-MM-dd
                    String.valueOf(d.isAvailable())
            ));
        }
        saveToFile(DONOR_FILE, lines);
    }

    public static List<Donor> loadDonors() {
        List<String> lines = readFromFile(DONOR_FILE);
        List<Donor> donors = new ArrayList<>();
        for (String line : lines) {
            try {
                String[] parts = line.split(",");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                String gender = parts[2];
                String bloodGroup = parts[3];
                String phone = parts[4];
                String city = parts[5];
                LocalDate lastDonationDate = LocalDate.parse(parts[6]);
                boolean available = Boolean.parseBoolean(parts[7]);

                donors.add(new Donor(name, age, gender, bloodGroup, phone, city, lastDonationDate, available));
            } catch (Exception e) {
                System.err.println("Error parsing donor: " + line);
            }
        }
        return donors;
    }

    public static void saveAcceptors(List<Acceptor> acceptors) {
        List<String> lines = new ArrayList<>();
        for (Acceptor a : acceptors) {
            lines.add(String.join(",",
                    a.getName(),
                    String.valueOf(a.getAge()),
                    a.getGender(),
                    a.getBloodGroup(),
                    a.getCity(),
                    a.getContact(),
                    a.getRequiredDate().toString()  // format: yyyy-MM-dd
            ));
        }
        saveToFile(ACCEPTOR_FILE, lines);
    }

    public static List<Acceptor> loadAcceptors() {
        List<String> lines = readFromFile(ACCEPTOR_FILE);
        List<Acceptor> acceptors = new ArrayList<>();
        for (String line : lines) {
            try {
                String[] parts = line.split(",");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                String gender = parts[2];
                String bloodGroup = parts[3];
                String city = parts[4];
                String phone = parts[5];
                LocalDate needDate = LocalDate.parse(parts[6]);

                acceptors.add(new Acceptor(name, age, gender, bloodGroup, city, phone, needDate));
            } catch (Exception e) {
                System.err.println("Error parsing acceptor: " + line);
            }
        }
        return acceptors;
    }

    // Existing utility methods
    public static void saveToFile(String fileName, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) return lines;  // avoid FileNotFound

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    private static final String RESERVED_DONOR_FILE = "reserved_donors.txt";

    public static void saveReservedDonors(List<Donor> donors) {
        List<String> lines = new ArrayList<>();
        for (Donor d : donors) {
            lines.add(String.join(",",
                    d.getName(),
                    String.valueOf(d.getAge()),
                    d.getGender(),
                    d.getBloodGroup(),
                    d.getContact(),
                    d.getCity(),
                    d.getLastDonationDate().toString(),
                    String.valueOf(d.isAvailable())
            ));
        }
        saveToFile(RESERVED_DONOR_FILE, lines);
    }

    public static List<Donor> loadReservedDonors() {
        List<String> lines = readFromFile(RESERVED_DONOR_FILE);
        List<Donor> donors = new ArrayList<>();
        for (String line : lines) {
            try {
                String[] parts = line.split(",");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                String gender = parts[2];
                String bloodGroup = parts[3];
                String phone = parts[4];
                String city = parts[5];
                LocalDate lastDonationDate = LocalDate.parse(parts[6]);
                boolean available = Boolean.parseBoolean(parts[7]);

                donors.add(new Donor(name, age, gender, bloodGroup, city, phone, lastDonationDate, available));
            } catch (Exception e) {
                System.err.println("Error parsing reserved donor: " + line);
            }
        }
        return donors;
    }

}
