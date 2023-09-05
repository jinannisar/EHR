import java.time.LocalDate;

public class Diagnosis implements Comparable<Diagnosis>{
    private String patientDiagnosisID, doctorID, diagnosisType, diagnosisCategory, symptoms, severity, diagnosisCode;
    private LocalDate diagnosisDate;
    public final static String fileName = "diagnosis.csv";

    Patient p = new Patient();
    public Diagnosis(String patientDiagnosisID, String doctorID, LocalDate diagnosisDate, String diagnosisType,
                     String diagnosisCategory, String symptoms, String severity, String diagnosisCode) {
        //this.diagnosisID = diagnosisID;
        this.patientDiagnosisID = patientDiagnosisID;
        this.doctorID = doctorID;
        this.diagnosisDate = diagnosisDate;
        this.diagnosisType = diagnosisType;
        this.diagnosisCategory = diagnosisCategory;
        this.symptoms = symptoms;
        this.severity = severity;
        this.diagnosisCode = diagnosisCode;
    }

    public Diagnosis(){

    }

    public String getPatientDiagnosisID() {
        return patientDiagnosisID;
    }

    public void setPatientDiagnosisID(String patientDiagnosisID) {
        this.patientDiagnosisID = patientDiagnosisID;
    }


    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(String diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public String getDiagnosisCategory() {
        return diagnosisCategory;
    }

    public void setDiagnosisCategory(String diagnosisCategory) {
        this.diagnosisCategory = diagnosisCategory;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String note) {
        this.diagnosisCode = note;
    }

    public LocalDate getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDate diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    @Override
    public int compareTo(Diagnosis u) {
        if (getPatientDiagnosisID() == null || u.getPatientDiagnosisID() == null) {
            return 0;
        }
        return getPatientDiagnosisID().compareTo(u.getPatientDiagnosisID());
    }
}
