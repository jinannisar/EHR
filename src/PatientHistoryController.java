import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PatientHistoryController extends HelloApplication {
    @FXML
    private Button addButtonHistory;
    @FXML
    private Button searchButtonHistory;
    @FXML
    private Button updateButtonHistory;
    @FXML
    private Button deleteButtonHistory;
    @FXML
    private Button clearButtonHistory;
    @FXML
    private Button backButtonHistory;
    @FXML
    private TextArea actionStatusHistory;
    @FXML
    private TextField idFieldHistory;
    @FXML
    private TextField heightField;
    @FXML
    private TextField weightField;
    @FXML
    private TextArea pastIllnessField;
    @FXML
    private TextArea familyDiseaseField;
    @FXML
    private TextArea childhoodDisease;
    @FXML
    private TextArea allergiesField;
    @FXML
    private TextArea medicineHistoryField;
    @FXML
    private TextArea procedureHistoryField;
    @FXML
    private TextField sexualHistoryField;

    private List<PatientHistory> searchList = new ArrayList<>();
    private String searchString = "";

    public PatientHistoryController() {
    }

    public void initialize() {
        addButtonHistory.setOnAction(e -> createPatientHistory());
        updateButtonHistory.setOnAction(e -> updatePatientHistory());
        deleteButtonHistory.setOnAction(e -> deletePatientHistory());
        this.backButtonHistory.setOnAction((e) -> {
            try {
                changeScene("home.fxml");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        });
        clearButtonHistory.setOnAction(e -> {
            idFieldHistory.setText("");
            heightField.setText("");
            weightField.setText("");
            pastIllnessField.setText("");
            familyDiseaseField.setText("");
            childhoodDisease.setText("");
            allergiesField.setText("");
            medicineHistoryField.setText("");
            procedureHistoryField.setText("");
            sexualHistoryField.setText("");
        });
        searchButtonHistory.setOnAction(e -> {
            PatientHistory history = searchPatientHistory();
            if (history != null) {
                idFieldHistory.setText(history.getPatientID());
                heightField.setText(Double.toString(history.getHeight()));
                weightField.setText(Double.toString(history.getWeight()));
                pastIllnessField.setText(history.getHistoryOfIllness());
                familyDiseaseField.setText(history.getFamilyDiseases());
                childhoodDisease.setText(history.getChildhoodDiseases());
                allergiesField.setText(history.getAllergies());
                medicineHistoryField.setText(history.getMedicineHistory());
                procedureHistoryField.setText(history.getProcedureHistory());
                sexualHistoryField.setText(history.getSexualHistory());

            } else {
                idFieldHistory.setText("");
                heightField.setText("");
                weightField.setText("");
                pastIllnessField.setText("");
                familyDiseaseField.setText("");
                childhoodDisease.setText("");
                allergiesField.setText("");
                medicineHistoryField.setText("");
                procedureHistoryField.setText("");
                sexualHistoryField.setText("");
            }
        });
    }

    public void createPatientHistory() {
        double height = 0;
        double weight = 0;
        try {
            height = Double.parseDouble(heightField.getText());
            weight = Double.parseDouble(weightField.getText());
        } catch (NumberFormatException e) {
            actionStatusHistory.setText(actionStatusHistory.getText() + "\n" + "Error. Wrong input.");
            actionStatusHistory.positionCaret(actionStatusHistory.getLength());
            return;
        }

        String patientID = idFieldHistory.getText();
        String pastIllness = pastIllnessField.getText();
        String familyDisease = familyDiseaseField.getText();
        String childhoodDsease = childhoodDisease.getText();
        String allergies = allergiesField.getText();
        String medicineHistory = medicineHistoryField.getText();
        String procedureHistory = procedureHistoryField.getText();
        String sexualHistory = sexualHistoryField.getText();

        PatientHistory pHistory = new PatientHistory(patientID, height, weight, pastIllness, familyDisease, childhoodDsease, allergies, medicineHistory, procedureHistory, sexualHistory);
        searchString = patientID;
        if (keyExists(searchString)) {
            actionStatusHistory.setText(actionStatusHistory.getText() + "\nRecord Exists. Please click update to modify the treatment details.");
            actionStatusHistory.positionCaret(actionStatusHistory.getLength());
        } else {
            PatientHistoryCSV csv = new PatientHistoryCSV(); //change to patient history csv handler
            csv.create(pHistory);
            actionStatusHistory.setText(actionStatusHistory.getText() + "\nAdded patient history " + patientID);
            actionStatusHistory.positionCaret(actionStatusHistory.getLength());
        }
    }

    public void updatePatientHistory() {
        String patientID = idFieldHistory.getText();
        double height = Double.parseDouble(heightField.getText());
        double weight = Double.parseDouble(weightField.getText());
        String pastIllness = pastIllnessField.getText();
        String familyDisease = familyDiseaseField.getText();
        String childhoodDsease = childhoodDisease.getText();
        String allergies = allergiesField.getText();
        String medicineHistory = medicineHistoryField.getText();
        String procedureHistory = procedureHistoryField.getText();
        String sexualHistory = sexualHistoryField.getText();

        PatientHistory pHistory = new PatientHistory(patientID, height, weight, pastIllness, familyDisease, childhoodDsease, allergies, medicineHistory, procedureHistory, sexualHistory);
        PatientHistoryCSV csv = new PatientHistoryCSV();
        csv.update(pHistory, PatientHistory.filename);

        actionStatusHistory.setText(actionStatusHistory.getText() + "\nUpdated Patient History: " + patientID);
        actionStatusHistory.positionCaret(actionStatusHistory.getLength());
    }

    private boolean keyExists(String key) {
        PatientHistoryCSV csv = new PatientHistoryCSV();
        searchList = csv.readCSV(PatientHistory.filename);
        for (PatientHistory history : searchList) {
            if (history.getPatientID().equals(key)) {
                return true;
            }
        }
        return false;
    }

    private PatientHistory searchPatientHistory() {
        searchString = idFieldHistory.getText();
        PatientHistoryCSV csv = new PatientHistoryCSV();
        searchList = csv.readCSV(PatientHistory.filename);
        for (PatientHistory history : searchList) {
            if ((history.getPatientID()).contentEquals(searchString)) {
                actionStatusHistory.setText(actionStatusHistory.getText() + "\nFound: " + searchString);
                actionStatusHistory.positionCaret(actionStatusHistory.getLength());
                return new PatientHistory(history.getPatientID(),history.getHeight(),history.getWeight(),history.getHistoryOfIllness(),history.getProcedureHistory(),history.getFamilyDiseases(),history.getChildhoodDiseases(),history.getAllergies(),history.getMedicineHistory(),history.getSexualHistory());
            } else {
                idFieldHistory.setText("");
                heightField.setText("");
                weightField.setText("");
                pastIllnessField.setText("");
                familyDiseaseField.setText("");
                childhoodDisease.setText("");
                allergiesField.setText("");
                medicineHistoryField.setText("");
                procedureHistoryField.setText("");
                sexualHistoryField.setText("");
            }
        }
        actionStatusHistory.setText(actionStatusHistory.getText() + "\n" + searchString + " Not Found.");
        actionStatusHistory.positionCaret(actionStatusHistory.getLength());
        return null;
    }
    private void deletePatientHistory() {
        // logic to delete student
        PatientHistoryCSV csv = new PatientHistoryCSV();
        String id = idFieldHistory.getText() == "" ? "-" : idFieldHistory.getText();
        System.out.println(id);
        csv.delete(id, PatientHistory.filename);
        actionStatusHistory.setText(actionStatusHistory.getText() + "\n" + "deleted patient history");
        actionStatusHistory.positionCaret(actionStatusHistory.getLength());
    }

}
