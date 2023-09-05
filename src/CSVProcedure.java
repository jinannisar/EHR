import java.io.*;
import java.util.*;

public class CSVProcedure {
    private String delimiter = ",";
    private String newline = "\n";
    private String header = "PatientID, procedureID, procedureName, procedureType, procedureDescription, duration"; // e.g for Patient class data

    public CSVProcedure() {
    }

    // You can also use a constructor to set the delimiter, header and newline
    public CSVProcedure(String header, String delimiter, String newline) {
        this.header = header;
        this.delimiter = delimiter;
        this.newline = newline;
    }

    public void writeCSV(List<ProcedureMedicine> procedures, String fileName) {
        FileWriter fileWriter = null;
        try {
            File file = new File(fileName);
            fileWriter = new FileWriter(file);
            fileWriter.append(header);
            fileWriter.append(newline);

            // process content line by line
            for (ProcedureMedicine procedure : procedures) {
                fileWriter.append(procedure.getPatientIDprocedure());
                fileWriter.append(delimiter);
                fileWriter.append(procedure.getProcedureID());
                fileWriter.append(delimiter);
                fileWriter.append(procedure.getProcedureName());
                fileWriter.append(delimiter);
                fileWriter.append(procedure.getProcedureType());
                fileWriter.append(delimiter);
                fileWriter.append(procedure.getProcedureDescription());
                fileWriter.append(delimiter);
                fileWriter.append(Double.toString(procedure.getDuration()));

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

    public List<ProcedureMedicine> readCSV(String fileName) {
        BufferedReader bReader = null;
        File file = new File(fileName);
        List<ProcedureMedicine> procedures = new ArrayList<ProcedureMedicine>();
        try {
            String line = "";
            bReader = new BufferedReader(new FileReader(file));
            bReader.readLine();
            while ((line = bReader.readLine()) != null) {
                String[] tokens = line.split(delimiter);
                if (tokens.length > 0) {
                    // content separated by delimeter (normally, a comma ',')
                    ProcedureMedicine procedure = new ProcedureMedicine(tokens[0],tokens[1],tokens[2], tokens[3], tokens[4], Double.parseDouble(tokens[5]));
                    procedures.add(procedure);
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
        return procedures;
    }

    //
    public void create(ProcedureMedicine procedure) {
        List<ProcedureMedicine> procedures = this.readCSV(ProcedureMedicine.fileName);
        procedures.add(procedure);
        Collections.sort(procedures);
        writeCSV(procedures, ProcedureMedicine.fileName);
    }

    // for update and delete, you can read the csv file into a List<Patient>,
    // perform the operations and then write back the list
    public void update(String patientIDprocedure, String procedureID, String procedureName, String procedureType, String procedureDescription, double duration, String fileName) {
        List<ProcedureMedicine> procedures = readCSV(fileName);
        for (ProcedureMedicine procedure : procedures) {
            if (procedure.getPatientIDprocedure().equals(patientIDprocedure)) {
                procedure.setProcedureID(procedureID);
                procedure.setProcedureName(procedureName);
                procedure.setProcedureType(procedureType);
                procedure.setProcedureDescription(procedureDescription);
                procedure.setDuration(duration);
                break;
            }
        }
        Collections.sort(procedures);
        writeCSV(procedures, fileName); // write back to file
    }

    public void update(ProcedureMedicine pM, String fileName) {
        List<ProcedureMedicine> procedures = readCSV(fileName);
        for (ProcedureMedicine procedure : procedures) {
            if (procedure.getPatientIDprocedure().equals(pM.getPatientIDprocedure())) {
                procedure.setProcedureID(pM.getProcedureID());
                procedure.setProcedureType(pM.getProcedureType());
                procedure.setProcedureName(pM.getProcedureName());
                procedure.setProcedureDescription(pM.getProcedureDescription());
                procedure.setDuration(pM.getDuration());
                break;
            }
        }
        Collections.sort(procedures);
        writeCSV(procedures, fileName); // write back to file
    }

    public void update(List<ProcedureMedicine> procedures, ProcedureMedicine pM, String fileName) {
        procedures = readCSV(fileName);
        for (ProcedureMedicine procedure : procedures) {
            if (procedure.getPatientIDprocedure().equals(pM.getPatientIDprocedure())) {
                procedure.setProcedureID(pM.getProcedureID());
                procedure.setProcedureName(pM.getProcedureName());
                procedure.setProcedureType(pM.getProcedureType());
                procedure.setProcedureDescription(pM.getProcedureDescription());
                procedure.setDuration(pM.getDuration());
                break;
            }
        }
        Collections.sort(procedures);
        writeCSV(procedures, fileName); // write back to file
    }

    public void delete(String id, String fileName) {
        List<ProcedureMedicine> procedures = readCSV(fileName);
        for (ProcedureMedicine procedure : procedures) {
            if (procedure.getPatientIDprocedure().equals(id)) {
                procedures.remove(procedure);
                break;
            }
        }
        Collections.sort(procedures);
        writeCSV(procedures, fileName); // write back to file
    }
}

