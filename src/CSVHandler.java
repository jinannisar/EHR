import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class CSVHandler {
    private String delimiter = ",";
    private String newline = "\n";
    private String header = "patientid, name, ic, bloodtype, age, gender, dob, natinonality, occupation, maritalstatus, address, contactno, email"; // e.g for Patient class data

    // default constructor
    public CSVHandler() {
    }

    // You can also use a constructor to set the delimiter, header and newline
    public CSVHandler(String header, String delimiter, String newline) {
        this.header = header;
        this.delimiter = delimiter;
        this.newline = newline;
    }

    public void writeCSV(List<Patient> patient, String fileName) {
        FileWriter fileWriter = null;
        try {
            File file = new File(fileName);
            fileWriter = new FileWriter(file);
            fileWriter.append(header);
            fileWriter.append(newline);

            // process content line by line
            for (Patient patients : patient) {
                fileWriter.append(patients.getPatientId());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getName());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getIcNumber());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getBloodType());
                fileWriter.append(delimiter);                
                fileWriter.append(patients.getAge());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getGender());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getDateOfBirth().toString());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getNationality());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getOccupation());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getMaritalStatus());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getAddress());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getContactNumber());
                fileWriter.append(delimiter);
                fileWriter.append(patients.getEmail());

                fileWriter.append(newline);
            }
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                // handle exception
                e.printStackTrace();
            }
        }
    }

    public List<Patient> readCSV(String fileName) {
        BufferedReader bReader = null;
        File file = new File(fileName);
        List<Patient> patients = new ArrayList<Patient>();
        try {
            String line = "";
            bReader = new BufferedReader(new FileReader(file));
            bReader.readLine();
            while ((line = bReader.readLine()) != null) {
                String[] tokens = line.split(delimiter);
                if (tokens.length > 0) {
                    // content separated by delimeter (normally, a comma ',')
                    Patient patient = new Patient(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], LocalDate.parse(tokens[6]), tokens[7], tokens[8], tokens[9], tokens[10], tokens[11], tokens[12]);
                    patients.add(patient);
                }
            }
        } catch (FileNotFoundException e) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        } finally {
            try {
                if (bReader != null)
                    bReader.close();
            } catch (IOException e) {
                // handle exception
                e.printStackTrace();
            }
        }
        return patients;
    }

    //
    public void create(Patient p) {
        List<Patient> patients = this.readCSV(Patient.fileName);
        patients.add(p);
        Collections.sort(patients);
        writeCSV(patients, Patient.fileName);
    }

    // for update and delete, you can read the csv file into a List<Patient>,
    // perform the operations and then write back the list
    public void update(String id, String newName, String newicNumber, String newbloodType, String newAge, String newgender, LocalDate newdateofBirth, String newnationality, String newoccupation, String newmaritalStatus, String newaddress, String newcontactNumber, String newemail, String fileName) {
        List<Patient> patients = readCSV(fileName);
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(id)) {
                patient.setName(newName);
                patient.setIcNumber(newicNumber);
                patient.setBloodType(newbloodType);
                patient.setAge(newAge);
                patient.setGender(newgender);
                patient.setDateOfBirth(newdateofBirth);
                patient.setNationality(newnationality);
                patient.setOccupation(newoccupation);
                patient.setMaritalStatus(newmaritalStatus);
                patient.setAddress(newaddress);
                patient.setContactNumber(newcontactNumber);
                patient.setEmail(newemail);
                break;
            }
        }
        Collections.sort(patients);
        writeCSV(patients, fileName); // write back to file
    }

    public void update(Patient p, String fileName) {
        List<Patient> patients = readCSV(fileName);
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(p.getPatientId())) {
                patient.setName(p.getName());
                patient.setBloodType(p.getBloodType());
                patient.setAge(p.getAge());
                patient.setGender(p.getGender());
                patient.setDateOfBirth(p.getDateOfBirth());
                patient.setNationality(p.getNationality());
                patient.setOccupation(p.getOccupation());
                patient.setMaritalStatus(p.getMaritalStatus());
                patient.setAddress(p.getAddress());
                patient.setContactNumber(p.getContactNumber());
                patient.setEmail(p.getEmail());
                break;
            }
        }
        Collections.sort(patients);
        writeCSV(patients, fileName); // write back to file
    }

    public void update(List<Patient> patients, Patient p, String fileName) {
        patients = readCSV(fileName);
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(p.getPatientId())) {
                patient.setName(p.getName());
                patient.setBloodType(p.getBloodType());
                patient.setAge(p.getAge());
                patient.setGender(p.getGender());
                patient.setDateOfBirth(p.getDateOfBirth());
                patient.setNationality(p.getNationality());
                patient.setOccupation(p.getOccupation());
                patient.setMaritalStatus(p.getMaritalStatus());
                patient.setAddress(p.getAddress());
                patient.setContactNumber(p.getContactNumber());
                patient.setEmail(p.getEmail());
                break;
            }
        }
        Collections.sort(patients);
        writeCSV(patients, fileName); // write back to file
    }

    public void delete(String id, String fileName) {
        List<Patient> patients = readCSV(fileName);
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(id)) {
                patients.remove(patient);
                break;
            }
        }
        Collections.sort(patients);
        writeCSV(patients, fileName); // write back to file
    }
}
