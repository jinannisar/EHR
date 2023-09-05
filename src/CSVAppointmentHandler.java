import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CSVAppointmentHandler {
    private String delimiter = ",";
    private String newline = "\n";
    private String header = "appointmentID, patientID, doctorID, appointmentType, appointmentDescription,Date, time";

    // default constructor
    public CSVAppointmentHandler() {
    }

    public void writeCSV(List<Appointment> appointments, String fileName) {
        FileWriter fileWriter = null;
        try {
            File file = new File(fileName);
            fileWriter = new FileWriter(file);
            fileWriter.append(header);
            fileWriter.append(newline);

            // process content line by line
            for (Appointment appointment : appointments) {
                fileWriter.append(appointment.getAppointmentID());
                fileWriter.append(delimiter);
                fileWriter.append(appointment.getPatientID());
                fileWriter.append(delimiter);
                fileWriter.append(appointment.getDoctorID());
                fileWriter.append(delimiter);
                fileWriter.append(appointment.getAppointmentReason());
                fileWriter.append(delimiter);
                fileWriter.append(appointment.getAppointmentType());
                fileWriter.append(delimiter);
                fileWriter.append(appointment.getAppointmentDate().toString());
                fileWriter.append(delimiter);
                fileWriter.append(appointment.getAppointmentTime());
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

    public List<Appointment> readCSV(String fileName) {
        BufferedReader bReader = null;
        File file = new File(fileName);
        List<Appointment> appointments = new ArrayList<Appointment>();
        try {
            String line = "";
            bReader = new BufferedReader(new FileReader(file));
            bReader.readLine();
            while ((line = bReader.readLine()) != null) {
                String[] data = line.split(delimiter);
                if (data.length > 0) {
                    // content separated by delimeter (normally, a comma ',')
                    Appointment appointment = new Appointment(data[0],data[1],data[2],data[3],data[4],LocalDate.parse(data[5]),data[6]);
                    appointments.add(appointment);
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
        return appointments;
    }

    public String dateConverter(LocalDate date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String localDateString = date.format(dateTimeFormatter);
        return localDateString;
    }

    public void createAppointment(Appointment a) {
        List<Appointment> appointment = this.readCSV(Appointment.fileName);
        appointment.add(a);
        Collections.sort(appointment);
        writeCSV(appointment, Appointment.fileName);
    }

    // for update and delete, you can read the csv file into a List<Student>,
    // perform the operations and then write back the list
    public void updateappointment(String id, String patient, String doctor, String type, String reason, String time, String fileName) {
        List<Appointment> appointments = readCSV(fileName);
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(id)) {
                appointment.setPatientID(patient);
                appointment.setDoctorID(doctor);
                appointment.setAppointmentType(type);
                appointment.setAppointmentReason(reason);
                appointment.setAppointmentTime(time);
                break;
            }
        }
        Collections.sort(appointments);
        writeCSV(appointments, fileName); //write back to file
    }

    public void updateappointment2(Appointment Appointments, String fileName) {
        List<Appointment> appointments = readCSV(fileName);
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(Appointments.getAppointmentID())) {
                appointment.setPatientID(Appointments.getPatientID());
                appointment.setDoctorID(Appointments.getDoctorID());
                appointment.setAppointmentType(Appointments.getAppointmentType());
                appointment.setAppointmentReason(Appointments.getAppointmentReason());
                appointment.setAppointmentTime(Appointments.getAppointmentTime());
                break;
            }
        }
        Collections.sort(appointments);
        writeCSV(appointments, fileName); //write back to file
    }

    public void updateappointment3(List<Appointment> appointments, Appointment Appointments, String fileName) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(Appointments.getAppointmentID())) {
                appointment.setPatientID(Appointments.getPatientID());
                appointment.setDoctorID(Appointments.getDoctorID());
                appointment.setAppointmentType(Appointments.getAppointmentType());
                appointment.setAppointmentReason(Appointments.getAppointmentReason());
                appointment.setAppointmentTime(Appointments.getAppointmentTime());
                break;
            }
        }
        Collections.sort(appointments);
        writeCSV(appointments, fileName); //write back to file
    }

    public void deleteappointment(String id, String fileName) {
        List<Appointment> appointments = readCSV(fileName);
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(id)) {
                appointments.remove(appointment);
                break;
            }
        }
        Collections.sort(appointments);
        writeCSV(appointments, fileName); //write back to file

    }
}
