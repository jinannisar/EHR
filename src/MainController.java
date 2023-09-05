import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController  extends HelloApplication{
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField bloodTypeField;
    @FXML
    private TextField occupationField;
    @FXML
    private TextField ICField;
    @FXML
    private DatePicker DOBField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField conditionField;
    @FXML
    private TextField nationalityField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField marryField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextArea actionStatus;
    @FXML
    private Button backButton;

    // List to store our students
    //private List<Patient> students = new ArrayList<>();
    private List<Patient> searchList = new ArrayList<>();
    private String searchString = "";

    public void initialize() {
        // attach all the event handlers during initialization
        addButton.setOnAction(e -> createPatient());
        updateButton.setOnAction(e -> updatePatient());
        deleteButton.setOnAction(e -> deletePatient());
        backButton.setOnAction(e -> {
            try {
                changeScene("home.fxml");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        searchButton.setOnAction(e -> {
            Patient p = searchPatient();
            if (p != null) {
                idField.setText(p.getPatientId());
                nameField.setText(p.getName());
                ICField.setText(p.getIcNumber());
                bloodTypeField.setText(p.getBloodType());
                ageField.setText(p.getAge());
                genderField.setText(p.getGender());
                DOBField.setValue(p.getDateOfBirth());
                nationalityField.setText(p.getNationality());
                occupationField.setText(p.getOccupation());
                conditionField.setText("");
                marryField.setText(p.getMaritalStatus());
                addressField.setText(p.getAddress());
                phoneField.setText(p.getContactNumber());
                emailField.setText(p.getEmail());
            } else {
                idField.setText("");
                nameField.setText("");
                ICField.setText("");
                bloodTypeField.setText("");
                ageField.setText("");
                genderField.setText("");
                DOBField.setValue(null);
                nationalityField.setText("");
                occupationField.setText("");
                conditionField.setText("");
                marryField.setText("");
                addressField.setText("");
                phoneField.setText("");
                emailField.setText("");
            }
        });
        clearButton.setOnAction(e -> {
            idField.setText("");
            nameField.setText("");
            ICField.setText("");
            bloodTypeField.setText("");
            ageField.setText("");
            genderField.setText("");
            DOBField.setValue(null);
            nationalityField.setText("");
            occupationField.setText("");
            conditionField.setText("");
            marryField.setText("");
            addressField.setText("");
            phoneField.setText("");
            emailField.setText("");
        });
    }

    private void createPatient() {
        String patientId = idField.getText();
        String name = nameField.getText();
        String icNumber = ICField.getText();
        String bloodType = bloodTypeField.getText();
        String age = ageField.getText();
        String gender = genderField.getText();
        LocalDate dateOfBirth = DOBField.getValue();
        String nationality = nationalityField.getText();
        String occupation = occupationField.getText();
        String maritalStatus = marryField.getText();
        String address = addressField.getText();
        String contactNumber = phoneField.getText();
        String email = emailField.getText();

       // int age = Integer.parseInt(ageField.getText() == "" ? "0" : ageField.getText());
        Patient p = new Patient(patientId, name, age, icNumber, bloodType, gender, dateOfBirth, nationality, occupation, maritalStatus, address, contactNumber, email);
        searchString = patientId;
        if (keyExists(searchString)) {
            actionStatus.setText(actionStatus.getText() + "\nRecord Exists. Please click update to modify");
            actionStatus.positionCaret(actionStatus.getLength());
        } else {
            CSVHandler csv = new CSVHandler();
            csv.create(p);
            actionStatus.setText(actionStatus.getText() + "\nAdded Patient: " + name);
            actionStatus.positionCaret(actionStatus.getLength());
        }
    }

    private void updatePatient() {
        // logic to update student
        String patientId = idField.getText();
        String name = nameField.getText();
        String icNumber = ICField.getText();
        String bloodType = bloodTypeField.getText();
        String age = ageField.getText();
        String gender = genderField.getText();
        LocalDate dateOfBirth = DOBField.getValue();
        String nationality = nationalityField.getText();
        String occupation = occupationField.getText();
        String maritalStatus = marryField.getText();
        String address = addressField.getText();
        String contactNumber = phoneField.getText();
        String email = emailField.getText();

        Patient p = new Patient(patientId, name, age, icNumber, bloodType, gender, dateOfBirth, nationality, occupation, maritalStatus, address, contactNumber, email);
        CSVHandler csv = new CSVHandler();
        csv.update(p, Patient.fileName);

        actionStatus.setText(actionStatus.getText() + "\nUpdated Patient: " + name);
        actionStatus.positionCaret(actionStatus.getLength());
    }
    private void deletePatient() {
        // logic to delete student
        CSVHandler csv = new CSVHandler();
        String id = idField.getText() == "" ? "-" : idField.getText();
        System.out.println(id);
        csv.delete(id, Patient.fileName);
    }

    private boolean keyExists(String key) {
        CSVHandler csv = new CSVHandler();
        searchList = csv.readCSV(Patient.fileName);
        for (Patient p : searchList) {
            if (p.getPatientId().equals(key)) {
                return true;
            }
        }
        return false;
    }

    private Patient searchPatient() {
        // logic to search student
        searchString = idField.getText();
        CSVHandler csv = new CSVHandler();
        searchList = csv.readCSV(Patient.fileName);
        for (Patient p : searchList) {
            if ((p.getPatientId()).contentEquals(searchString)) {
                actionStatus.setText(actionStatus.getText() + "\nFound: " + searchString);
                actionStatus.positionCaret(actionStatus.getLength());
                return new Patient(p.getPatientId(),p.getName(),p.getIcNumber(),p.getBloodType(),p.getAge(),p.getGender(),p.getDateOfBirth(),p.getNationality(),p.getOccupation(),p.getMaritalStatus(),p.getAddress(),p.getContactNumber(),p.getEmail());
            } else {
                nameField.setText("");
                ageField.setText("");
            }
        }
        actionStatus.setText(actionStatus.getText() + "\n" + searchString + " Not Found.");
        actionStatus.positionCaret(actionStatus.getLength());
        return null;
    }


    // private void cancelAction() {
    // // logic to cancel action - only applicable for search etc.
    // }
}
