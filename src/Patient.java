import java.time.LocalDate;

public class Patient implements Comparable<Patient> {
    private String patientId;
    private String name;
    private String icNumber;
    private String bloodType;
    private String age;
    private String gender;
    private LocalDate dateOfBirth;
    private String nationality;
    private String occupation;
    private String maritalStatus;
    private String address;
    private String contactNumber;
    private String email;
    public final static String fileName = "patient.csv";

    // constructors
    public Patient() {
    }

    public Patient(String patientId, String name, String icNumber, String bloodType, String age, String gender,
                   LocalDate dateOfBirth, String nationality, String occupation, String maritalStatus, String address,
                   String contactNumber, String email) {
        this.patientId = patientId;
        this.name = name;
        this.icNumber = icNumber;
        this.bloodType = bloodType;
        this.age = age;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.occupation = occupation;
        this.maritalStatus = maritalStatus;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // getter and setter methods
    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //implements the interface Comparable so that we can sort the student data
    @Override
    public int compareTo(Patient u) {
        if (getPatientId() == null || u.getPatientId() == null) {
            return 0;
        }
        return getPatientId().compareTo(u.getPatientId());
    }

    public int compare(Patient a, Patient b)
    {
        return a.name.compareTo(b.name);
    }
}
