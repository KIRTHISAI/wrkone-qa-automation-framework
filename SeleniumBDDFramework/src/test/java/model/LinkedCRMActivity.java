package model;

public class LinkedCRMActivity {

    private String leadName;
    private String activityType;
    private String purpose;
    private String description;
    private String linkToStage;
    private String date;
    private String startTime;
    private String endTime;
    private String assignmentType;
    private String user;
    private String reason;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public LinkedCRMActivity(
            String leadName,
            String activityType,
            String purpose,
            String description,
            String linkToStage,
            String date,
            String startTime,
            String endTime,
            String assignmentType,
            String user,
            String reason) {

        this.leadName = leadName;
        this.activityType = activityType;
        this.purpose = purpose;
        this.description = description;
        this.linkToStage = linkToStage;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignmentType = assignmentType;
        this.user = user;
        this.reason = reason;
    }


    // ============================================================
    // GETTERS
    // ============================================================

    public String getLeadName() {
        return leadName;
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

    public String getLinkToStage() {
        return linkToStage;
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


    // ============================================================
    // SETTERS
    // ============================================================

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLinkToStage(String linkToStage) {
        this.linkToStage = linkToStage;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
