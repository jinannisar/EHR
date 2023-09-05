import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class CSVMedicine {
    private String delimiter = ",";
    private String newline = "\n";
    private String header = "patientID, medicineID, medicineName, dosage, duration, sideEffect, datePrescibed"; // e.g for Patient class data

    // default constructor
    public CSVMedicine() {
    }

    // You can also use a constructor to set the delimiter, header and newline
    public CSVMedicine(String header, String delimiter, String newline) {
        this.header = header;
        this.delimiter = delimiter;
        this.newline = newline;
    }

    public void writeCSV(List<Medicine> medicines, String fileName) {
        FileWriter fileWriter = null;
        try {
            File file = new File(fileName);
            fileWriter = new FileWriter(file);
            fileWriter.append(header);
            fileWriter.append(newline);

            // process content line by line
            for (Medicine med : medicines) {
                fileWriter.append(med.getPatientID());
                fileWriter.append(delimiter);
                fileWriter.append(med.getMedicineID());
                fileWriter.append(delimiter);
                fileWriter.append(med.getMedicineName());
                fileWriter.append(delimiter);
                fileWriter.append(Integer.toString(med.getDosage()));
                fileWriter.append(delimiter);
                fileWriter.append(Double.toString(med.getDuration()));
                fileWriter.append(delimiter);
                fileWriter.append(med.getSideEffect());
                fileWriter.append(delimiter);
                fileWriter.append(med.getDatePrescribed().toString());

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

    public List<Medicine> readCSV(String fileName) {
        BufferedReader bReader = null;
        File file = new File(fileName);
        List<Medicine> medicines = new ArrayList<Medicine>();
        try {
            String line = "";
            bReader = new BufferedReader(new FileReader(file));
            bReader.readLine();
            while ((line = bReader.readLine()) != null) {
                String[] tokens = line.split(delimiter);
                if (tokens.length > 0) {
                    // content separated by delimeter (normally, a comma ',')
                    Medicine medicine = new Medicine(tokens[0], tokens[1], tokens[2], Integer.parseInt(tokens[3]), Double.parseDouble(tokens[4]), tokens[5], LocalDate.parse(tokens[6]));
                    medicines.add(medicine);
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
        return medicines;
    }

    //
    public void create(Medicine med) {
        List<Medicine> medicines = this.readCSV(Medicine.fileName);
        medicines.add(med);
        Collections.sort(medicines);
        writeCSV(medicines, Medicine.fileName);
    }

    // for update and delete, you can read the csv file into a List<Patient>,
    // perform the operations and then write back the list
    public void update(String patientID, String medicineID, String medicineName, int dosage, double duration, String sideEffect,LocalDate datePrescribed, String fileName) {
        List<Medicine> medicines = readCSV(fileName);
        for (Medicine med : medicines) {
            if (med.getPatientID().equals(patientID)) {
                med.setMedicineID(medicineID);
                med.setMedicineName(medicineName);
                med.setDosage(dosage);
                med.setDuration(duration);
                med.setSideEffect(sideEffect);
                med.setDatePrescribed(datePrescribed);
                break;
            }
        }
        Collections.sort(medicines);
        writeCSV(medicines, fileName); // write back to file
    }

    public void update(Medicine medicine, String fileName) {
        List<Medicine> medicines = readCSV(fileName);
        for (Medicine med : medicines) {
            if (med.getPatientID().equals(medicine.getPatientID())) {
                med.setMedicineID(medicine.getMedicineID());
                med.setMedicineName(medicine.getMedicineName());
                med.setDosage(medicine.getDosage());
                med.setDuration(medicine.getDuration());
                med.setSideEffect(medicine.getSideEffect());
                med.setDatePrescribed(medicine.getDatePrescribed());
                break;
            }
        }
        Collections.sort(medicines);
        writeCSV(medicines, fileName); // write back to file
    }

    public void update(List<Medicine> medicines, Medicine med, String fileName) {
        medicines = readCSV(fileName);
        for (Medicine medicine : medicines) {
            if (medicine.getPatientID().equals(med.getPatientID())) {
                medicine.setMedicineID(med.getMedicineID());
                medicine.setMedicineName(med.getMedicineName());
                medicine.setDosage(med.getDosage());
                medicine.setDuration(med.getDuration());
                medicine.setSideEffect(med.getSideEffect());
                medicine.setDatePrescribed(med.getDatePrescribed());
                break;
            }
        }
        Collections.sort(medicines);
        writeCSV(medicines, fileName); // write back to file
    }

    public void delete(String id, String fileName) {
        List<Medicine> medicines = readCSV(fileName);
        for (Medicine medicine : medicines) {
            if (medicine.getPatientID().equals(id)) {
                medicines.remove(medicine);
                break;
            }
        }
        Collections.sort(medicines);
        writeCSV(medicines, fileName); // write back to file
    }

}