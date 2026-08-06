package model;

public class CRMActivity {

    private String activityType;
    private String purpose;
    private String description;
    private String date;
    private String startTime;
    private String endTime;
    private String assignmentType;
    private String user;
    private String reason;

    public CRMActivity(String activityType,
                       String purpose,
                       String description,
                       String date,
                       String startTime,
                       String endTime,
                       String assignmentType,
                       String user,
                       String reason) {

        this.activityType = activityType;
        this.purpose = purpose;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignmentType = assignmentType;
        this.user = user;
        this.reason = reason;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public String getUser() {
        return user;
    }

    public String getReason() {
        return reason;
    }
}