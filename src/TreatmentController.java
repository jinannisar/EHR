import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TreatmentController extends HelloApplication {
    @FXML
    private Button addButtonTreatment;
    @FXML
    private Button searchButtonTreatment;
    @FXML
    private Button updateButtonTreatment;
    @FXML
    private Button deleteButtonTreatment;
    @FXML
    private Button clearButtonTreatment;
    @FXML
    private Button backButtonTreatment;
    @FXML
    private Button AppointmentsButton;
    @FXML
    private TextArea actionStatusTreatment;
    @FXML
    private TextField idFieldTreatment;
    @FXML
    private TextField TreatmentIDField;
    @FXML
    private TextField TreatmentTypeField;
    @FXML
    private TextArea TreatmentDescriptionField;
    @FXML
    private DatePicker EndDateTreatmentField;
    @FXML
    private DatePicker StartDateTreatmentField;

    private List<TreatmentCourse> searchList = new ArrayList<>();
    private String searchString = "";

    public void initialize() {
        addButtonTreatment.setOnAction(e -> createTreatment());
        updateButtonTreatment.setOnAction(e -> updateTreatment());
        deleteButtonTreatment.setOnAction(e -> deleteTreatment());
        backButtonTreatment.setOnAction(e -> {
            try {
                changeScene("home.fxml");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        
        AppointmentsButton.setOnAction(e -> {
            try {
                changeScene("appointments.fxml");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        searchButtonTreatment.setOnAction(e -> {
            TreatmentCourse t = searchTreatment();
            if (t != null) {
                idFieldTreatment.setText(t.getPatientID());
                TreatmentIDField.setText(t.getTreatmentID());
                TreatmentTypeField.setText(t.getTreatmentType());
                TreatmentDescriptionField.setText(t.getTreatmentDescription());
                StartDateTreatmentField.setValue(t.getStartDate());
                EndDateTreatmentField.setValue(t.getEndDate());

            } else {
                idFieldTreatment.setText("");
                TreatmentIDField.setText("");
                TreatmentTypeField.setText("");
                TreatmentDescriptionField.setText("");
                StartDateTreatmentField.setValue(null);
                EndDateTreatmentField.setValue(null);
            }
        });
        clearButtonTreatment.setOnAction(e -> {
            idFieldTreatment.setText("");
            TreatmentIDField.setText("");
            TreatmentTypeField.setText("");
            TreatmentDescriptionField.setText("");
            StartDateTreatmentField.setValue(null);
            EndDateTreatmentField.setValue(null);
        }); 
    }

    private void createTreatment() {
        LocalDate startdate = null;
        LocalDate enddate = null;
        try {
            startdate = StartDateTreatmentField.getValue();
            enddate = EndDateTreatmentField.getValue();
        } catch (DateTimeParseException  e) {
            actionStatusTreatment.setText(actionStatusTreatment.getText() + "\n" + "Error. Value type is wrong.");
            actionStatusTreatment.positionCaret(actionStatusTreatment.getLength());
            return;
        }
        String patientid = idFieldTreatment.getText();
        String treatmentID = TreatmentIDField.getText();
        String type = TreatmentTypeField.getText();
        String desc = TreatmentDescriptionField.getText();

        TreatmentCourse t = new TreatmentCourse(treatmentID, patientid, type, desc,startdate, enddate);
        searchString = treatmentID;
        if (keyExists(searchString)) {
            actionStatusTreatment.setText(actionStatusTreatment.getText() + "\nRecord Exists. Please click update to modify the treatment details.");
            actionStatusTreatment.positionCaret(actionStatusTreatment.getLength());
        } else {
            CSVTreatmentHandler csv = new CSVTreatmentHandler();
            csv.createTreatment(t);
            actionStatusTreatment.setText(actionStatusTreatment.getText() + "\nAdded TreatmentCourse " + treatmentID + " for Patient " + patientid + "\nThe duration of the treatment is around " + DatesDuration(startdate, enddate) );
            actionStatusTreatment.positionCaret(actionStatusTreatment.getLength());
        }
        
    }

    private void updateTreatment() {
        String patientid = idFieldTreatment.getText();
        String treatmentID = TreatmentIDField.getText();
        String type = TreatmentTypeField.getText();
        String desc = TreatmentDescriptionField.getText();
        LocalDate startdate = StartDateTreatmentField.getValue();
        LocalDate enddate = EndDateTreatmentField.getValue();

        TreatmentCourse t = new TreatmentCourse(treatmentID, patientid, type, desc,startdate, enddate);
        CSVTreatmentHandler csv = new CSVTreatmentHandler();
        csv.updateTreatment2(t, TreatmentCourse.fileName);

        actionStatusTreatment.setText(actionStatusTreatment.getText() + "\nUpdated Treatment Course " + treatmentID + " for Patient " + patientid + "\nThe duration of the treatment is around " + DatesDuration(startdate, enddate));
        actionStatusTreatment.positionCaret(actionStatusTreatment.getLength());
    }
    private void deleteTreatment() {
        // logic to delete student
        CSVTreatmentHandler csv = new CSVTreatmentHandler();
        String id = TreatmentIDField.getText() == "" ? "-" : TreatmentIDField.getText();
        System.out.println(id);
        csv.deleteTreatment(id, TreatmentCourse.fileName);
    }

    private boolean keyExists(String key) {
        CSVTreatmentHandler csv = new CSVTreatmentHandler();
        searchList = csv.readCSV(TreatmentCourse.fileName);
        for (TreatmentCourse t : searchList) {
            if (t.getTreatmentID().equals(key)) {
                return true;
            }
        }
        return false;
    }

    private String DatesDuration (LocalDate start, LocalDate end){
        Period findDuration = Period.between(start,end);
        String duration = findDuration.getYears() + "Years " + findDuration.getMonths() + "Months " + findDuration.getDays() + "Days";
        return duration;
    }

    private TreatmentCourse searchTreatment() {
        searchString = TreatmentIDField.getText();
        CSVTreatmentHandler csv = new CSVTreatmentHandler();
        searchList = csv.readCSV(TreatmentCourse.fileName);
        for (TreatmentCourse t : searchList) {
            if ((t.getTreatmentID()).contentEquals(searchString)) {
                actionStatusTreatment.setText(actionStatusTreatment.getText() + "\nFound: " + searchString);
                actionStatusTreatment.positionCaret(actionStatusTreatment.getLength());
                return new TreatmentCourse(t.getTreatmentID(),t.getPatientID(),t.getTreatmentType(),t.getTreatmentDescription(),t.getStartDate(),t.getEndDate());
            } else {
                idFieldTreatment.setText("");
                TreatmentIDField.setText("");
                TreatmentTypeField.setText("");
                TreatmentDescriptionField.setText("");
                StartDateTreatmentField.setValue(null);
                EndDateTreatmentField.setValue(null);
            }
        }
        actionStatusTreatment.setText(actionStatusTreatment.getText() + "\n" + searchString + " Not Found.");
        actionStatusTreatment.positionCaret(actionStatusTreatment.getLength());
        return null;
    }
}