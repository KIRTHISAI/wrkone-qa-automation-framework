package model;

public class CRMActivity {

    // ============================================================
    // CREATE ACTIVITY DATA
    // ============================================================

    private String activityType;
    private String purpose;
    private String description;
    private String date;
    private String startTime;
    private String endTime;
    private String assignmentType;
    private String user;
    private String reason;

    // ============================================================
    // EDIT ACTIVITY DATA
    // ============================================================

    private String editRequired;
    private String editPurpose;
    private String editDescription;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public CRMActivity(
            String activityType,
            String purpose,
            String description,
            String date,
            String startTime,
            String endTime,
            String assignmentType,
            String user,
            String reason,
            String editRequired,
            String editPurpose,
            String editDescription) {

        this.activityType = activityType;
        this.purpose = purpose;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignmentType = assignmentType;
        this.user = user;
        this.reason = reason;

        this.editRequired = editRequired;
        this.editPurpose = editPurpose;
        this.editDescription = editDescription;
    }

    // ============================================================
    // GETTERS
    // ============================================================

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

    public String getEditRequired() {
        return editRequired;
    }

    public String getEditPurpose() {
        return editPurpose;
    }

    public String getEditDescription() {
        return editDescription;
    }

    // ============================================================
    // SETTERS
    // ============================================================

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setEditRequired(String editRequired) {
        this.editRequired = editRequired;
    }

    public void setEditPurpose(String editPurpose) {
        this.editPurpose = editPurpose;
    }

    public void setEditDescription(String editDescription) {
        this.editDescription = editDescription;
    }

    // ============================================================
    // TO STRING
    // ============================================================

    @Override
    public String toString() {

        return "CRMActivity{" +
                "activityType='" + activityType + '\'' +
                ", purpose='" + purpose + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", assignmentType='" + assignmentType + '\'' +
                ", user='" + user + '\'' +
                ", reason='" + reason + '\'' +
                ", editRequired='" + editRequired + '\'' +
                ", editPurpose='" + editPurpose + '\'' +
                ", editDescription='" + editDescription + '\'' +
                '}';
    }
}