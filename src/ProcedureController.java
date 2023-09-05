import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ProcedureController extends HelloApplication {
    @FXML
    private Button addButtonProcedure;
    @FXML
    private Button searchButtonProcedure;
    @FXML
    private Button updateButtonProcedure;
    @FXML
    private Button deleteButtonProcedure;
    @FXML
    private Button clearButtonProcedure;
    @FXML
    private Button backButtonProcedure;
    @FXML
    private Button MedicinesButton;
    @FXML
    private TextArea actionStatusProcedure;
    @FXML
    private TextField idFieldProcedure;
    @FXML
    private TextField procedureIDField;
    @FXML
    private TextField procedureNameField;
    @FXML
    private TextField procedureTypeField;
    @FXML
    private TextField procedureDurationField;
    @FXML
    private TextArea procedureDescriptionField;

    private List<ProcedureMedicine> searchList = new ArrayList<>();
    private String searchString = "";
    public ProcedureController() {
    }

    public void initialize() {
        addButtonProcedure.setOnAction(e -> createProcedure());
        updateButtonProcedure.setOnAction(e -> updateProcedure());
        deleteButtonProcedure.setOnAction(e-> deleteProcedure());
        this.backButtonProcedure.setOnAction((e) -> {
            try {
                changeScene("home.fxml");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        });
        this.MedicinesButton.setOnAction((e) -> {
            try {
                changeScene("medicines.fxml");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        });

        searchButtonProcedure.setOnAction(e -> {
            ProcedureMedicine pm = searchProcedureMedicine();
            if (pm != null) {
                idFieldProcedure.setText(pm.getPatientIDprocedure());
                procedureIDField.setText(pm.getProcedureID());
                procedureNameField.setText(pm.getProcedureName());
                procedureDescriptionField.setText(pm.getProcedureDescription());
                procedureTypeField.setText(pm.getProcedureType());
                procedureDurationField.setText(Double.toString(pm.getDuration()));
            } else {
                idFieldProcedure.setText("");
                procedureIDField.setText("");
                procedureNameField.setText("");
                procedureDescriptionField.setText("");
                procedureTypeField.setText("");
                procedureDurationField.setText("");
            }
        });
        clearButtonProcedure.setOnAction(e -> {
            idFieldProcedure.setText("");
            procedureIDField.setText("");
            procedureNameField.setText("");
            procedureDescriptionField.setText("");
            procedureTypeField.setText("");
            procedureDurationField.setText("");
        });
    }

    private void createProcedure() {
        double duration = 0;
        try {
            duration = Double.parseDouble(procedureDurationField.getText());
        } catch (NumberFormatException e) {
            actionStatusProcedure.setText(actionStatusProcedure.getText() + "\n" + "Error. Value type is wrong.");
            actionStatusProcedure.positionCaret(actionStatusProcedure.getLength());
            return;
        }
        String patientIDprocedure = idFieldProcedure.getText();
        String procedureID = procedureIDField.getText();
        String procedureName = procedureNameField.getText();
        String procedureType = procedureTypeField.getText();
        String procedureDescription = procedureDescriptionField.getText();

        ProcedureMedicine pm = new ProcedureMedicine(patientIDprocedure,procedureID,procedureName,procedureType, procedureDescription, duration);
        searchString = patientIDprocedure;
        if (keyExists(searchString)) {
            actionStatusProcedure.setText(actionStatusProcedure.getText() + "\nRecord Exists. Please click update to modify the treatment details.");
            actionStatusProcedure.positionCaret(actionStatusProcedure.getLength());
        } else {
            CSVProcedure csv = new CSVProcedure(); //change later
            csv.create(pm);
            actionStatusProcedure.setText(actionStatusProcedure.getText() + "\nAdded procedure for patient " + patientIDprocedure);
            actionStatusProcedure.positionCaret(actionStatusProcedure.getLength());
        }
    }


    private void updateProcedure() {
        String patientIDprocedure = idFieldProcedure.getText();
        String procedureID = idFieldProcedure.getText();
        String procedureName = procedureNameField.getText();
        String procedureType = procedureTypeField.getText();
        String procedureDescription = procedureDescriptionField.getText();
        double duration = Double.parseDouble(procedureDurationField.getText());

        ProcedureMedicine pm = new ProcedureMedicine(patientIDprocedure,procedureID,procedureName,procedureType, procedureDescription, duration);
        CSVProcedure csv = new CSVProcedure();
        csv.update(pm, ProcedureMedicine.fileName);

        actionStatusProcedure.setText(actionStatusProcedure.getText() + "\nUpdated: " + patientIDprocedure);
        actionStatusProcedure.positionCaret(actionStatusProcedure.getLength());
    }

    private void deleteProcedure() {
        // logic to delete student
        CSVProcedure csv = new CSVProcedure();
        String id = idFieldProcedure.getText() == "" ? "-" : idFieldProcedure.getText();
        System.out.println(id);
        csv.delete(id, ProcedureMedicine.fileName);
        actionStatusProcedure.setText(actionStatusProcedure.getText() + "\nProcedure Deleted");
        actionStatusProcedure.positionCaret(actionStatusProcedure.getLength());
    }

    private boolean keyExists(String key) {
        CSVProcedure csv = new CSVProcedure();
        searchList = csv.readCSV(ProcedureMedicine.fileName);
        for (ProcedureMedicine pm : searchList) {
            if (pm.getPatientIDprocedure().equals(key)) {
                return true;
            }
        }
        return false;
    }

    private ProcedureMedicine searchProcedureMedicine() {
        // logic to search student
        searchString = idFieldProcedure.getText();
        CSVProcedure csv = new CSVProcedure();
        searchList = csv.readCSV(ProcedureMedicine.fileName);
        for (ProcedureMedicine pm : searchList) {
            if ((pm.getPatientIDprocedure()).contentEquals(searchString)) {
                actionStatusProcedure.setText(actionStatusProcedure.getText() + "\nFound: " + searchString);
                actionStatusProcedure.positionCaret(actionStatusProcedure.getLength());
                return new ProcedureMedicine(pm.getPatientIDprocedure(),pm.procedureID,pm.getProcedureName(),pm.getProcedureType(), pm.getProcedureDescription(), pm.getDuration());
            } else {
                idFieldProcedure.setText("");
                procedureIDField.setText("");
                procedureNameField.setText("");
                procedureDescriptionField.setText("");
                procedureTypeField.setText("");
                procedureDurationField.setText("");
            }
        }
        actionStatusProcedure.setText(actionStatusProcedure.getText() + "\n" + searchString + " Not Found.");
        actionStatusProcedure.positionCaret(actionStatusProcedure.getLength());
        return null;
    }


}