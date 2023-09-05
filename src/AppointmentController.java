import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.*;
import java.time.format.DateTimeParseException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

public class AppointmentController extends HelloApplication{
    @FXML
    private Button addButtonAppointment;
    @FXML
    private Button searchButtonAppointment;
    @FXML
    private Button updateButtonAppointment;
    @FXML
    private Button deleteButtonAppointment;
    @FXML
    private Button clearButtonAppointment;
    @FXML
    private Button backButtonAppointment;
    @FXML
    private TextArea actionStatusAppointment;
    @FXML
    private TextField idFieldAppointment;
    @FXML
    private TextField AppointmentIDField;
    @FXML
    private TextField DoctorIDAppointment;
    @FXML
    private TextField AppointmentReasonField;
    @FXML
    private TextField SessionTimeField;
    @FXML
    private TextField AppointmentTypeField;
    @FXML
    private DatePicker AppointmentDateField;

    private List<Appointment> searchList = new ArrayList<>();
    private String searchString = "";

    public void initialize() {
        addButtonAppointment.setOnAction(e -> createAppointment());
        updateButtonAppointment.setOnAction(e -> updateAppointment());
        deleteButtonAppointment.setOnAction(e -> deleteAppointment());
        backButtonAppointment.setOnAction(e -> {
            try {
                changeScene("treatment-course.fxml");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        searchButtonAppointment.setOnAction(e -> {
            Appointment a = searchAppointment();
            if (a != null) {
                idFieldAppointment.setText(a.getPatientID());
                AppointmentIDField.setText(a.getAppointmentID());
                DoctorIDAppointment.setText(a.getDoctorID());
                AppointmentReasonField.setText(a.getAppointmentReason());
                SessionTimeField.setText(a.getAppointmentTime());
                AppointmentTypeField.setText(a.getAppointmentType());
                AppointmentDateField.setValue(a.getAppointmentDate());

            } else {
                idFieldAppointment.setText("");
                AppointmentIDField.setText("");
                DoctorIDAppointment.setText("");
                AppointmentReasonField.setText("");
                SessionTimeField.setText("");
                AppointmentTypeField.setText("");
                AppointmentDateField.setValue(null);
            }
        });
        clearButtonAppointment.setOnAction(e -> {
            idFieldAppointment.setText("");
            AppointmentIDField.setText("");
            DoctorIDAppointment.setText("");
            AppointmentReasonField.setText("");
            SessionTimeField.setText("");
            AppointmentTypeField.setText("");
            AppointmentDateField.setValue(null);
        });
    }
    private void createAppointment() {
        LocalDate date = null;
        try {
            date = AppointmentDateField.getValue();
        } catch (DateTimeParseException  e) {
            actionStatusAppointment.setText(actionStatusAppointment.getText() + "\n" + "Error. Value type is wrong.");
            actionStatusAppointment.positionCaret(actionStatusAppointment.getLength());
            return;
        }
        String patientid = idFieldAppointment.getText();
        String appointmentid = AppointmentIDField.getText();
        String doctorid = DoctorIDAppointment.getText();
        String reason = AppointmentReasonField.getText();
        String time = SessionTimeField.getText();
        String type = AppointmentTypeField.getText();

        Appointment a = new Appointment(appointmentid, patientid, doctorid, reason, type, date, time);
        searchString = appointmentid;
        if (keyExists(searchString)) {
            actionStatusAppointment.setText(actionStatusAppointment.getText() + "\nRecord Exists. Please click update to modify the appointment details.");
            actionStatusAppointment.positionCaret(actionStatusAppointment.getLength());
        } else {
            CSVAppointmentHandler csv = new CSVAppointmentHandler();
            csv.createAppointment(a);
            actionStatusAppointment.setText(actionStatusAppointment.getText() + "\nAdded Appointment " + appointmentid + " for Patient " + patientid + "\nThe appointment is in " + DatesDuration(date));
            actionStatusAppointment.positionCaret(actionStatusAppointment.getLength());
        }
    }

    private void updateAppointment() {
        String patientid = idFieldAppointment.getText();
        String appointmentid = AppointmentIDField.getText();
        String doctorid = DoctorIDAppointment.getText();
        String reason = AppointmentReasonField.getText();
        String time = SessionTimeField.getText();
        String type = AppointmentTypeField.getText();
        LocalDate date = AppointmentDateField.getValue();

        Appointment a = new Appointment(appointmentid, patientid, doctorid, reason, type, date, time);
        CSVAppointmentHandler csv = new CSVAppointmentHandler();
        csv.updateappointment2(a, Appointment.fileName);

        actionStatusAppointment.setText(actionStatusAppointment.getText() + "\nUpdated Appointment " + appointmentid + " for Patient " + patientid + "\nThe appointment is in " + DatesDuration(date));
        actionStatusAppointment.positionCaret(actionStatusAppointment.getLength());
    }
    private void deleteAppointment() {
        CSVAppointmentHandler csv = new CSVAppointmentHandler();
        String id = AppointmentIDField.getText() == "" ? "-" : AppointmentIDField.getText();
        System.out.println(id);
        csv.deleteappointment(id, Appointment.fileName);
        actionStatusAppointment.setText(actionStatusAppointment.getText() + "\n" + "Deleted appointment");
        actionStatusAppointment.positionCaret(actionStatusAppointment.getLength());
    }

    private boolean keyExists(String key) {
        CSVAppointmentHandler csv = new CSVAppointmentHandler();
        searchList = csv.readCSV(Appointment.fileName);
        for (Appointment a : searchList) {
            if (a.getAppointmentID().equals(key)) {
                return true;
            }
        }
        return false;
    }

    private String DatesDuration (LocalDate date){
        Period findDuration = Period.between(LocalDate.now(),date);
        String duration = findDuration.getDays() + "Days";
        return duration;
    }

    private Appointment searchAppointment() {
        searchString = AppointmentIDField.getText();
        CSVAppointmentHandler csv = new CSVAppointmentHandler();
        searchList = csv.readCSV(Appointment.fileName);
        for (Appointment a : searchList) {
            if ((a.getAppointmentID()).contentEquals(searchString)) {
                actionStatusAppointment.setText(actionStatusAppointment.getText() + "\nFound: " + searchString);
                actionStatusAppointment.positionCaret(actionStatusAppointment.getLength());
                return new Appointment(a.getAppointmentID(),a.getPatientID(),a.getDoctorID(),a.getAppointmentReason(),a.getAppointmentType(),a.getAppointmentDate(),a.getAppointmentTime());
            } else {
                idFieldAppointment.setText("");
                AppointmentIDField.setText("");
                DoctorIDAppointment.setText("");
                AppointmentReasonField.setText("");
                SessionTimeField.setText("");
                AppointmentTypeField.setText("");
                AppointmentDateField.setValue(null);
            }
        }
        actionStatusAppointment.setText(actionStatusAppointment.getText() + "\n" + searchString + " Not Found.");
        actionStatusAppointment.positionCaret(actionStatusAppointment.getLength());
        return null;
    }

}