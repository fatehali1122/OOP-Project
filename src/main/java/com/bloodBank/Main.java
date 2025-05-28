// (UPDATED MAIN GUI FILE)
package com.bloodBank;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main extends Application {

    private VBox donateFormContent;
    private boolean isLoggedIn = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Blood Donation & Reservation System");

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:background.jpg", 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        BorderPane root = new BorderPane();
        root.setBackground(new Background(backgroundImage));

        VBox homeContent = new VBox(10);
        homeContent.setPadding(new Insets(20));
        homeContent.getChildren().add(new Label("\u2764\ufe0f Welcome to Blood Bank Management System!"));
        homeContent.getChildren().add(new Label("\nThis application helps users donate and reserve blood efficiently in emergencies.\n"));
        root.setTop(homeContent);

        TabPane tabPane = new TabPane();

        // Home Tab
        Tab homeTab = new Tab("Home");
        VBox homeTabContent = new VBox(10);
        homeTabContent.setPadding(new Insets(20));
        homeTabContent.getChildren().add(new Label("This is the general welcome page.\nFeel free to explore the features from the tabs above."));
        homeTab.setContent(homeTabContent);
        homeTab.setClosable(false);

        // Donate Blood Tab
        Tab donateTab = new Tab("Donate Blood");
        donateFormContent = getDonateForm();
        donateTab.setContent(new VBox());
        donateTab.setClosable(false);
        donateTab.setOnSelectionChanged(e -> {
            if (donateTab.isSelected() && !isLoggedIn) {
                boolean success = showLoginDialog();
                if (success) {
                    donateTab.setContent(new ScrollPane(donateFormContent));
                } else {
                    tabPane.getSelectionModel().select(homeTab);
                }
            }
        });

        // Reserve Blood Tab
        Tab reserveTab = new Tab("Reserve Blood");
        reserveTab.setContent(getReserveTabContent());
        reserveTab.setClosable(false);

        // Contact Tab
        Tab contactTab = new Tab("Contact");
        contactTab.setContent(getContactTabContent());
        contactTab.setClosable(false);

        // About Tab
        Tab aboutTab = new Tab("About");
        aboutTab.setContent(getAboutTabContent());
        aboutTab.setClosable(false);

        tabPane.getTabs().addAll(homeTab, donateTab, reserveTab, contactTab, aboutTab);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean showLoginDialog() {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Login or Sign Up");
        dialog.initModality(Modality.APPLICATION_MODAL);

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        ButtonType signUpButtonType = new ButtonType("Sign Up");
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, signUpButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            String user = username.getText();
            String pass = password.getText();
            if (dialogButton == loginButtonType) {
                if (UserManager.validateLogin(user, pass)) {
                    isLoggedIn = true;
                    return true;
                } else {
                    showAlert("Login Failed", "Invalid username or password.");
                    return false;
                }
            } else if (dialogButton == signUpButtonType) {
                if (UserManager.isUsernameTaken(user)) {
                    showAlert("Signup Failed", "Username already taken.");
                    return false;
                } else {
                    UserManager.registerUser(user, pass);
                    showAlert("Signup Successful", "Account created. Now login.");
                    return false;
                }
            }
            return false;
        });

        return dialog.showAndWait().orElse(false);
    }

    private VBox getReserveTabContent() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        ComboBox<String> bloodGroupBox = new ComboBox<>();
        bloodGroupBox.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodGroupBox.setPromptText("Select Blood Group");

        TextField cityField = new TextField();
        cityField.setPrefWidth(100); // or whatever width fits your layout
        cityField.setPromptText("Enter City");

        Button searchButton = new Button("Search Donors");
        Button viewAllButton = new Button("View All Available Blood");

        TableView<Donor> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Donor, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getName()));

        TableColumn<Donor, String> groupCol = new TableColumn<>("Blood Group");
        groupCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getBloodGroup()));

        TableColumn<Donor, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCity()));

        TableColumn<Donor, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button reserveBtn = new Button("Reserve");
            {
                reserveBtn.setOnAction(event -> {
                    Donor donor = getTableView().getItems().get(getIndex());
                    Dialog<List<String>> reserveDialog = new Dialog<>();
                    reserveDialog.setTitle("Reserve Blood");
                    GridPane grid = new GridPane();
                    grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
                    TextField name = new TextField();
                    TextField contact = new TextField();
                    grid.add(new Label("Your Name:"), 0, 0);
                    grid.add(name, 1, 0);
                    grid.add(new Label("Your Contact:"), 0, 1);
                    grid.add(contact, 1, 1);
                    reserveDialog.getDialogPane().setContent(grid);
                    reserveDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                    reserveDialog.setResultConverter(dialogBtn -> {
                        if (dialogBtn == ButtonType.OK) return Arrays.asList(name.getText(), contact.getText());
                        return null;
                    });
                    reserveDialog.showAndWait().ifPresent(result -> {
                        DonorService ds = new DonorService(new DataManager());
                        ds.reserveDonor(donor.getContact());
                        table.getItems().remove(donor);
                    });
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null); else setGraphic(reserveBtn);
            }
        });

        table.getColumns().addAll(nameCol, groupCol, cityCol, actionCol);

        searchButton.setOnAction(e -> {
            DataManager dm = new DataManager();
            SearchService ss = new SearchService(dm);
            table.getItems().setAll(ss.searchByBloodGroupAndCity(bloodGroupBox.getValue(), cityField.getText().trim()));
        });

        viewAllButton.setOnAction(e -> {
            DataManager dm = new DataManager();
            SearchService ss = new SearchService(dm);
            table.getItems().setAll(ss.getAllAvailableDonors());
        });

        layout.getChildren().addAll(
                new Label("Reserve Blood - Search Available Donors"),
                bloodGroupBox, cityField,
                new HBox(10, searchButton, viewAllButton),
                table
        );

        return layout;
    }

    private VBox getDonateForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));

        TextField nameField = new TextField(); nameField.setPromptText("Full Name"); nameField.setPrefWidth(300);
        TextField ageField = new TextField(); ageField.setPromptText("Age"); ageField.setPrefWidth(300);
        ComboBox<String> genderBox = new ComboBox<>(); genderBox.getItems().addAll("Male", "Female", "Other");
        ComboBox<String> bloodGroupBox = new ComboBox<>(); bloodGroupBox.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        TextField cityField = new TextField(); cityField.setPromptText("City"); cityField.setPrefWidth(300);
        TextField contactField = new TextField(); contactField.setPromptText("Contact Number"); contactField.setPrefWidth(300);
        DatePicker donationDatePicker = new DatePicker();
        CheckBox availableCheck = new CheckBox("Available to donate now");
        Button submitButton = new Button("Submit");
        Label statusLabel = new Label();

        submitButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String gender = genderBox.getValue();
                String bloodGroup = bloodGroupBox.getValue();
                String city = cityField.getText();
                String contact = contactField.getText();
                LocalDate lastDonation = donationDatePicker.getValue();
                boolean available = availableCheck.isSelected();

                if (!Validator.isValidBloodGroup(bloodGroup)) {
                    statusLabel.setText("Invalid blood group!");
                    return;
                }
                if (!Validator.isValidPhoneNumber(contact)) {
                    statusLabel.setText("Invalid contact number!");
                    return;
                }

                Donor donor = new Donor(name, age, gender, bloodGroup, contact, city, lastDonation, available);
                DonorService ds = new DonorService(new DataManager());
                ds.registerDonor(donor);

                statusLabel.setText("Donor registered successfully!");
                nameField.clear(); ageField.clear(); genderBox.setValue(null);
                bloodGroupBox.setValue(null); cityField.clear(); contactField.clear();
                donationDatePicker.setValue(null); availableCheck.setSelected(false);

            } catch (Exception ex) {
                statusLabel.setText("Error: Please fill all fields correctly.");
            }
        });

        form.getChildren().addAll(
                new Label("Donor Registration Form"),
                new Label("Name"), nameField,
                new Label("Age"), ageField,
                new Label("Gender"), genderBox,
                new Label("Blood Group"), bloodGroupBox,
                new Label("City"), cityField,
                new Label("Contact Number"), contactField,
                new Label("Last Donation Date"), donationDatePicker,
                availableCheck,
                submitButton,
                statusLabel
        );

        return form;
    }

    private VBox getContactTabContent() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.getChildren().add(new Label("Contact Us:\nEmail: support@bloodbank.com\nPhone: 123-456-7890"));
        return box;
    }

    private VBox getAboutTabContent() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.getChildren().add(new Label("About:\nThis application helps users donate and reserve blood in emergencies."));
        return box;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}