package model;

public class CRMActivity {

    // =========================================================
    // LOGIN DATA
    // =========================================================

    private String loginEmail;
    private String loginPassword;

    // =========================================================
    // CREATE ACTIVITY DATA
    // =========================================================

    private String activityType;
    private String purpose;
    private String description;
    private String location;
    private String date;
    private String startTime;
    private String endTime;
    private String assignmentType;
    private String user;
    private String reason;

    // =========================================================
    // EDIT ACTIVITY DATA
    // =========================================================

    private String editRequired;
    private String editPurpose;
    private String editDescription;

    // =========================================================
    // FULL CONSTRUCTOR
    // =========================================================

    public CRMActivity(
            String loginEmail,
            String loginPassword,
            String activityType,
            String purpose,
            String description,
            String location,
            String date,
            String startTime,
            String endTime,
            String assignmentType,
            String user,
            String reason) {

        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;

        this.activityType = activityType;
        this.purpose = purpose;
        this.description = description;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignmentType = assignmentType;
        this.user = user;
        this.reason = reason;
    }

    // =========================================================
    // BACKWARD COMPATIBILITY - OLD 11 FIELD CONSTRUCTOR
    // =========================================================

    public CRMActivity(
            String loginEmail,
            String loginPassword,
            String activityType,
            String purpose,
            String description,
            String date,
            String startTime,
            String endTime,
            String assignmentType,
            String user,
            String reason) {

        this(
                loginEmail,
                loginPassword,
                activityType,
                purpose,
                description,
                "",
                date,
                startTime,
                endTime,
                assignmentType,
                user,
                reason
        );
    }

    // =========================================================
    // BACKWARD COMPATIBILITY - OLD 10 FIELD CONSTRUCTOR
    // =========================================================

    public CRMActivity(
            String loginEmail,
            String loginPassword,
            String activityType,
            String purpose,
            String description,
            String date,
            String startTime,
            String endTime,
            String assignmentType,
            String user) {

        this(
                loginEmail,
                loginPassword,
                activityType,
                purpose,
                description,
                "",
                date,
                startTime,
                endTime,
                assignmentType,
                user,
                ""
        );
    }

    // =========================================================
    // LOGIN GETTERS
    // =========================================================

    public String getLoginEmail() {
        return loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    // =========================================================
    // CREATE ACTIVITY GETTERS
    // =========================================================

    public String getActivityType() {
        return activityType;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
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

    // Older code compatibility
    public String getUsers() {
        return user;
    }

    public String getReason() {
        return reason;
    }

    // =========================================================
    // EDIT GETTERS
    // =========================================================

    public String getEditRequired() {
        return editRequired;
    }

    public String getEditPurpose() {
        return editPurpose;
    }

    public String getEditDescription() {
        return editDescription;
    }

    // =========================================================
    // SETTERS
    // =========================================================

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
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

    public void setLocation(String location) {
        this.location = location;
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
}