import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CSVTreatmentHandler {
    private String delimiter = ",";
    private String newline = "\n";
    private String header = "treatmentID, patientID, treatmentType, treatmentDescription,startDate, endDate";

    // default constructor
    public CSVTreatmentHandler() {
    }

    public void writeCSV(List<TreatmentCourse> treatmentcourse, String fileName) {
        FileWriter fileWriter = null;
        try {
            File file = new File(fileName);
            fileWriter = new FileWriter(file);
            fileWriter.append(header);
            fileWriter.append(newline);

            // process content line by line
            for (TreatmentCourse treatment : treatmentcourse) {
                fileWriter.append(treatment.getTreatmentID());
                fileWriter.append(delimiter);
                fileWriter.append(treatment.getPatientID());
                fileWriter.append(delimiter);
                fileWriter.append(treatment.getTreatmentType());
                fileWriter.append(delimiter);
                fileWriter.append(treatment.getTreatmentDescription());
                fileWriter.append(delimiter);
                fileWriter.append(treatment.getStartDate().toString());
                fileWriter.append(delimiter);
                fileWriter.append(treatment.getEndDate().toString());
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

    public List<TreatmentCourse> readCSV(String fileName) {
        BufferedReader bReader = null;
        File file = new File(fileName);
        List<TreatmentCourse> treaments = new ArrayList<TreatmentCourse>();
        try {
            String line = "";
            bReader = new BufferedReader(new FileReader(file));
            bReader.readLine();
            while ((line = bReader.readLine()) != null) {
                String[] data = line.split(delimiter);
                if (data.length > 0) {
                    // content separated by delimeter (normally, a comma ',')
                    TreatmentCourse treatment = new TreatmentCourse(data[0],data[1],data[2],data[3],LocalDate.parse(data[4]),LocalDate.parse(data[5]));
                    treaments.add(treatment);
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
        return treaments;
    }

    public String dateConverter(LocalDate date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String localDateString = date.format(dateTimeFormatter);
        return localDateString;
    }

    public void createTreatment(TreatmentCourse t) {
        List<TreatmentCourse> treatments = this.readCSV(TreatmentCourse.fileName);
        treatments.add(t);
        Collections.sort(treatments);
        writeCSV(treatments, TreatmentCourse.fileName);
    }

    // for update and delete, you can read the csv file into a List<Student>,
    // perform the operations and then write back the list
    public void updateTreatment(String id, String patient, String type, String description,LocalDate start, LocalDate end, String fileName) {
        List<TreatmentCourse> treatments = readCSV(fileName);
        for (TreatmentCourse treatment : treatments) {
            if (treatment.getTreatmentID().equals(id)) {
                treatment.setPatientID(patient);
                treatment.setTreatmentType(type);
                treatment.setTreatmentDescription(description);
                treatment.setStartDate(start);
                treatment.setEndDate(end);
                break;
            }
        }
        Collections.sort(treatments);
        writeCSV(treatments, fileName); //write back to file
    }

    public void updateTreatment2(TreatmentCourse Treatment, String fileName) {
        List<TreatmentCourse> treatments = readCSV(fileName);
        for (TreatmentCourse treatment : treatments) {
            if (treatment.getTreatmentID().equals(Treatment.getTreatmentID())) {
                treatment.setPatientID(Treatment.getPatientID());
                treatment.setTreatmentType(Treatment.getTreatmentType());
                treatment.setTreatmentDescription(Treatment.getTreatmentDescription());
                treatment.setStartDate(Treatment.getStartDate());
                treatment.setEndDate(Treatment.getEndDate());
                break;
            }
        }
        Collections.sort(treatments);
        writeCSV(treatments, fileName); //write back to file
    }

    public void updateTreatment3(List<TreatmentCourse> treatments, TreatmentCourse Treatment, String fileName) {
        for (TreatmentCourse treatment : treatments) {
            if (treatment.getTreatmentID().equals(Treatment.getTreatmentID())) {
                treatment.setPatientID(Treatment.getPatientID());
                treatment.setTreatmentType(Treatment.getTreatmentType());
                treatment.setTreatmentDescription(Treatment.getTreatmentDescription());
                treatment.setStartDate(Treatment.getStartDate());
                treatment.setEndDate(Treatment.getEndDate());
                break;
            }
        }
        Collections.sort(treatments);
        writeCSV(treatments, fileName); //write back to file
    }

    public void deleteTreatment(String id, String fileName) {
        List<TreatmentCourse> treatments = readCSV(fileName);
        for (TreatmentCourse treatment : treatments) {
            if (treatment.getTreatmentID().equals(id)) {
                treatments.remove(treatment);
                break;
            }
        }
        Collections.sort(treatments);
        writeCSV(treatments, fileName); //write back to file

    }
}
