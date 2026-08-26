package Utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private DateTimeUtils() {}

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter DISPLAY_TIME =
            DateTimeFormatter.ofPattern("h:mm a");

    // Date
    public static String getDate(int daysToAdd) {
        return LocalDate.now()
                .plusDays(daysToAdd)
                .format(DATE_FORMAT);
    }

    // Returns "10:00 AM"
    public static String getStartTime(int hoursToAdd) {
        return LocalTime.now()
                .plusHours(hoursToAdd)
                .withMinute(0)
                .format(DISPLAY_TIME);
    }

    // Returns "11:00 AM"
    public static String getEndTime(int hoursToAdd) {
        return LocalTime.now()
                .plusHours(hoursToAdd)
                .withMinute(0)
                .format(DISPLAY_TIME);
    }

    // Returns quick-time-10_00_AM
    public static String getQuickTimeId(int hoursToAdd) {

        String time = getStartTime(hoursToAdd);

        return "quick-time-"
                + time.replace(":", "_")
                      .replace(" ", "_");
    }
}