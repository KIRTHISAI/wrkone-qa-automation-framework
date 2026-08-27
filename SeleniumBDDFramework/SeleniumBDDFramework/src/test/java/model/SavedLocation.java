package model;

public class SavedLocation {

    private final String name;
    private final String address;
    private final String latitude;
    private final String longitude;

    public SavedLocation(String name, String address,
                         String latitude, String longitude) {
        this.name = requireValue(name, "name");
        this.address = requireValue(address, "address");
        this.latitude = latitude == null ? "" : latitude.trim();
        this.longitude = longitude == null ? "" : longitude.trim();
    }

    private static String requireValue(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Saved location " + field + " cannot be blank.");
        }
        return value.trim();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}