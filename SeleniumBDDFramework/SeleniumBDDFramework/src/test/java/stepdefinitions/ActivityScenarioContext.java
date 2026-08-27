package stepdefinitions;

public final class ActivityScenarioContext {

    private static final ThreadLocal<String> activityId = new ThreadLocal<>();

    private ActivityScenarioContext() {
    }

    public static void setActivityId(String value) {
        activityId.set(value);
    }

    public static String getActivityId() {
        String value = activityId.get();
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Created activity ID is not available for this scenario.");
        }
        return value;
    }
}