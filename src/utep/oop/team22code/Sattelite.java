package utep.oop.team22code;

public class Satellite extends SpaceObject {

    public Satellite(String recordId, String satelliteName, String country, String orbitType,
                     int launchYear, String launchSite, double longitude, double avgLongitude,
                     String geohash, int daysOld, int conjunctionCount) {
        super(recordId, satelliteName, country, orbitType, launchYear, launchSite,
              longitude, avgLongitude, geohash, daysOld, conjunctionCount);
    }

    @Override
    public String getObjectType() {
        return "Satellite";
    }
}
