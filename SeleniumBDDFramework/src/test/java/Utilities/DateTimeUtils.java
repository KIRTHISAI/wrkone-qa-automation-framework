package Utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private DateTimeUtils() {}

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm a");

    // Current date or future date
    public static String getDate(int daysToAdd) {
        return LocalDate.now()
                .plusDays(daysToAdd)
                .format(DATE_FORMAT);
    }

    // Start Time
    public static String getStartTime(int minutesToAdd) {
        return LocalTime.now()
                .plusMinutes(minutesToAdd)
                .format(TIME_FORMAT);
    }

    // End Time
    public static String getEndTime(int hoursToAdd) {
        return LocalTime.now()
                .plusHours(hoursToAdd)
                .format(TIME_FORMAT);
    }
}