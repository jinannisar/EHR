import java.time.LocalDate;
public class Medicine implements Comparable<Medicine>{

    private String patientID, medicineID, medicineName, sideEffect;
    private int dosage;
    private double duration;
    private LocalDate datePrescribed;
    public final static String fileName = "medicine.csv";

    public Medicine(String patientID, String medicineID, String medicineName, int dosage, double duration, String sideEffect,LocalDate datePrescribed){
        this.patientID = patientID;
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.duration = duration;
        this.sideEffect = sideEffect;
        this.datePrescribed = datePrescribed;
    }
    public Medicine(){}

    public String getPatientID(){
        return patientID;
    }
    public void setPatientID(String newID) {
        patientID = newID;
    }
    public String getMedicineID(){
        return medicineID;
    }
    public void setMedicineID(String newID) {
        medicineID = newID;
    }
    public String getMedicineName() {
        return medicineName;
    }
    public void setMedicineName(String newName) {
        medicineName = newName;
    }
    public int getDosage() {
        return dosage;
    }
    public void setDosage(int newDosage) {
        dosage = newDosage;
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double newDuration) {
        duration = newDuration;
    }
    public String getSideEffect() {
        return sideEffect;
    }
    public void setSideEffect(String newEffect) {
        sideEffect = newEffect;
    }
    public LocalDate getDatePrescribed() {
        return datePrescribed;
    }
    public void setDatePrescribed(LocalDate date) {
        datePrescribed = date;
    }
    @Override
    public int compareTo(Medicine medicine) {
        if (getPatientID() == null || medicine.getPatientID() == null) {
            return 0;
        }
        return getPatientID().compareTo(medicine.getPatientID());
    }
}
