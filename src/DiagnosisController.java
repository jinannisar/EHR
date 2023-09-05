import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DiagnosisController extends HelloApplication {
    @FXML
    private Button addButtonDiagnosis;
    @FXML
    private Button searchButtonDiagnosis;
    @FXML
    private Button updateButtonDiagnosis;
    @FXML
    private Button deleteButtonDiagnosis;
    @FXML
    private Button clearButtonDiagnosis;
    @FXML
    private Button backButtonDiagnosis;
    @FXML
    private TextArea actionStatusDiagnosis;
    @FXML
    private TextField idFieldDiagnosis;
    @FXML
    private TextField DiagnosisCodeField;
    @FXML
    private TextField DoctorIDDiagnosis;
    @FXML
    private TextField DiagnosisCategoryField;
    @FXML
    private TextField DiagnosisTypeField;
    @FXML
    private TextArea SymptomsField;
    @FXML
    private TextField SeverityField;
    @FXML
    private DatePicker DiagnosisDate;

    private List<Diagnosis> searchList = new ArrayList<>();
    private String searchString = "";


    public void initialize() {
        addButtonDiagnosis.setOnAction(e -> createDiagnosis());
        updateButtonDiagnosis.setOnAction(e -> updateDiagnosis());
        deleteButtonDiagnosis.setOnAction(e -> deleteDiagnosis());
        this.backButtonDiagnosis.setOnAction((e) -> {
            try {
                changeScene("home.fxml");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        });
        clearButtonDiagnosis.setOnAction(e -> {
            idFieldDiagnosis.setText("");
            DiagnosisCodeField.setText("");
            DoctorIDDiagnosis.setText("");
            DiagnosisCategoryField.setText("");
            DiagnosisTypeField.setText("");
            SymptomsField.setText("");
            SeverityField.setText("");
            DiagnosisDate.setValue(null);
        });
        searchButtonDiagnosis.setOnAction(e -> {
            Diagnosis d = searchDiagnosis();
            if (d != null) {
                idFieldDiagnosis.setText(d.getPatientDiagnosisID());
                DiagnosisCodeField.setText(d.getDiagnosisCode());
                DoctorIDDiagnosis.setText(d.getDoctorID());
                DiagnosisCategoryField.setText(d.getDiagnosisCategory());
                DiagnosisTypeField.setText(d.getDiagnosisType());
                SymptomsField.setText(d.getSymptoms());
                SeverityField.setText(d.getSeverity());
                DiagnosisDate.setValue(d.getDiagnosisDate());
            } else {
                idFieldDiagnosis.setText("");
                DiagnosisCodeField.setText("");
                DoctorIDDiagnosis.setText("");
                DiagnosisCategoryField.setText("");
                DiagnosisTypeField.setText("");
                SymptomsField.setText("");
                SeverityField.setText("");
                DiagnosisDate.setValue(null);
            }
        });

    }

    private void createDiagnosis() {
        LocalDate dateOfDiagnosis = null;
        try {
            dateOfDiagnosis = DiagnosisDate.getValue();
        } catch (DateTimeParseException  e) {
            actionStatusDiagnosis.setText(actionStatusDiagnosis.getText() + "\n" + "Error. Value type is wrong.");
            actionStatusDiagnosis.positionCaret(actionStatusDiagnosis.getLength());
            return;
        }
        String diagnosisID = idFieldDiagnosis.getText();
        String DiagnosisCode = DiagnosisCodeField.getText();
        String doctorID = DoctorIDDiagnosis.getText();
        String diagnosisCategory = DiagnosisCategoryField.getText();
        String diagnosisType = DiagnosisTypeField.getText();
        String symptoms = SymptomsField.getText();
        String severity = SeverityField.getText();

        Diagnosis diagnosis = new Diagnosis(diagnosisID, doctorID, dateOfDiagnosis, diagnosisType, diagnosisCategory, symptoms, severity, DiagnosisCode);
        searchString = diagnosisID;
        if (keyExists(searchString)) {
            actionStatusDiagnosis.setText(actionStatusDiagnosis.getText() + "\nRecord Exists. Please click update to modify");
            actionStatusDiagnosis.positionCaret(actionStatusDiagnosis.getLength());
        } else {
            CSVDiagnosis csv = new CSVDiagnosis(); //change to individual csvhandler later
            csv.create(diagnosis);
            actionStatusDiagnosis.setText(actionStatusDiagnosis.getText() + "\nAdded Diagnosis Type: " + diagnosisType);
            actionStatusDiagnosis.positionCaret(actionStatusDiagnosis.getLength());
        }
    }

    private void updateDiagnosis() {
        LocalDate dateOfDiagnosis = null;
        try {
            dateOfDiagnosis = DiagnosisDate.getValue();
        } catch (DateTimeParseException  e) {
            actionStatusDiagnosis.setText(actionStatusDiagnosis.getText() + "\n" + "Error. Value type is wrong.");
            actionStatusDiagnosis.positionCaret(actionStatusDiagnosis.getLength());
            return;
        }
        String diagnosisID = idFieldDiagnosis.getText();
        String DiagnosisCode = DiagnosisCodeField.getText();
        String doctorID = DoctorIDDiagnosis.getText();
        String diagnosisCategory = DiagnosisCategoryField.getText();
        String diagnosisType = DiagnosisTypeField.getText();
        String symptoms = SymptomsField.getText();
        String severity = SeverityField.getText();

        Diagnosis diagnosis = new Diagnosis(diagnosisID, doctorID, dateOfDiagnosis, diagnosisType, diagnosisCategory, symptoms, severity, DiagnosisCode);
        CSVDiagnosis csv = new CSVDiagnosis(); //change to individual csvhandler later
        csv.update(diagnosis, Diagnosis.fileName);
        actionStatusDiagnosis.setText(actionStatusDiagnosis.getText() + "\nUpdate: " + diagnosisID);
        actionStatusDiagnosis.positionCaret(actionStatusDiagnosis.getLength());
    }

    private void deleteDiagnosis() throws DateTimeException {
        try {
            CSVDiagnosis csv = new CSVDiagnosis(); //change to individual csvhandler later
            String id = idFieldDiagnosis.getText() == "" ? "-" : idFieldDiagnosis.getText();
            System.out.println(id);
            csv.delete(id, Diagnosis.fileName);
            actionStatusDiagnosis.setText(actionStatusDiagnosis.getText() + "\nDiagnosis deleted");
            actionStatusDiagnosis.positionCaret(actionStatusDiagnosis.getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean keyExists(String key) {
        CSVDiagnosis csv = new CSVDiagnosis(); //change to diagnosis csvhandler
        searchList = csv.readCSV(Diagnosis.fileName);
        for (Diagnosis diagnosis : searchList) {
            if (diagnosis.getPatientDiagnosisID().equals(key)) {
                return true;
            }
        }
        return false;
    }

    private Diagnosis searchDiagnosis() {
        searchString = idFieldDiagnosis.getText();
        CSVDiagnosis csv = new CSVDiagnosis(); //change to diagnosis handler later
        searchList = csv.readCSV(Diagnosis.fileName);
        for (Diagnosis diagnosis : searchList) {
            if ((diagnosis.getPatientDiagnosisID()).contentEquals(searchString)) {
                actionStatusDiagnosis.setText(actionStatusDiagnosis.getText() + "\nFound: " + searchString);
                actionStatusDiagnosis.positionCaret(actionStatusDiagnosis.getLength());
                return new Diagnosis(diagnosis.getPatientDiagnosisID(), diagnosis.getDoctorID(), diagnosis.getDiagnosisDate(),
                        diagnosis.getDiagnosisType(), diagnosis.getDiagnosisCategory(), diagnosis.getSymptoms(),
                        diagnosis.getSeverity(), diagnosis.getDiagnosisCode());
            } else {
                idFieldDiagnosis.setText("");
                DiagnosisCodeField.setText("");
                DoctorIDDiagnosis.setText("");
                DiagnosisCategoryField.setText("");
                DiagnosisTypeField.setText("");
                SymptomsField.setText("");
                SeverityField.setText("");
                DiagnosisDate.setValue(null);

            }
        }
        actionStatusDiagnosis.setText(actionStatusDiagnosis.getText() + "\n" + searchString + " Not Found.");
        actionStatusDiagnosis.positionCaret(actionStatusDiagnosis.getLength());
        return null;
    }
}
