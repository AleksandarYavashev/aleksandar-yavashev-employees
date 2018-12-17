package sirma.solutions.intern.task;

import java.util.Date;

public class Record {

    private String employerID;
    private String projectID;
    private Date beginning;
    private Date end;

    Record(String employerID, String projectID, Date beginning, Date end){
        this.employerID = employerID;
        this.projectID = projectID;
        this.beginning = beginning;
        this.end = end;
    }

    public Date getBeginning() {
        return beginning;
    }

    public Date getEnd() {
        return end;
    }

    public String getProjectID(){
        return projectID;
    }

    public String getEmployerID(){
        return employerID;
    }
}
