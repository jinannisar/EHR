
public class ProcedureMedicine implements Comparable<ProcedureMedicine>{
    String patientIDprocedure, procedureID, procedureName, procedureType, procedureDescription;
    double duration;
    public final static String fileName = "procedure.csv";

    public ProcedureMedicine(String patientIDprocedure,String procedureID, String procedureName, String procedureType, String procedureDescription, double duration) {
        this.patientIDprocedure = patientIDprocedure;
        this.procedureID = procedureID;
        this.procedureName = procedureName;
        this.procedureType = procedureType;
        this.procedureDescription = procedureDescription;
        this.duration = duration;
    }
    public ProcedureMedicine(){}

    public String getPatientIDprocedure() {
        return patientIDprocedure;
    }

    public void setPatientIDprocedure(String patientIDprocedure) {
        this.patientIDprocedure = patientIDprocedure;
    }

    public String getProcedureID() {
        return procedureID;
    }

    public void setProcedureID(String procedureID) {
        this.procedureID = procedureID;
    }
    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    public String getProcedureDescription() {
        return procedureDescription;
    }

    public void setProcedureDescription(String procedureDescription) {
        this.procedureDescription = procedureDescription;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public int compareTo(ProcedureMedicine pMedicine) {
        if (getPatientIDprocedure() == null || pMedicine.getPatientIDprocedure() == null) {
            return 0;
        }
        return getPatientIDprocedure().compareTo(pMedicine.getPatientIDprocedure());
    }
}