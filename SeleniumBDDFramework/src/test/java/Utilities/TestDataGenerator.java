package Utilities;

import java.util.Random;

public class TestDataGenerator {

    private static Random random = new Random();

    public static String getFirstName() {
        return "User" + random.nextInt(1000);
    }

    public static String getLastName() {
        return "Test" + random.nextInt(1000);
    }

    public static String getEmail() {
        return "user" + System.currentTimeMillis() + "@onelern.com";
    }

    public static String getEmployeeId() {
        return String.valueOf(10000 + random.nextInt(90000));
    }

    public static String getPassword() {
        return "123456";
    }
}