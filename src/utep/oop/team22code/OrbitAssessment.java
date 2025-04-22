import java.io.*;
import java.util.*;

public class OrbitAssessment {

    private List<SpaceObject> spaceObjects;
    private final List<SpaceObject> exitedObjects = new ArrayList<>();
    private int inOrbitCount = 0;
    private int exitedCount = 0;

    public OrbitAssessment(List<SpaceObject> spaceObjects) {
        this.spaceObjects = spaceObjects;
    }

    public void assessAndSave() {
        for (SpaceObject obj : spaceObjects) {
            boolean stillInOrbit = isStillInOrbit(obj);
            String riskLevel = classifyRisk(obj.getOrbitalDrift());

            if (!stillInOrbit) {
                exitedObjects.add(obj);
                exitedCount++;
            } else {
                inOrbitCount++;
            }

        }

        saveCSV("updated_orbit_status.csv");
        saveExitedTXT("exited_debris_report.txt");
        System.out.println("Assessment complete. Reports generated.");
    }

    private boolean isStillInOrbit(SpaceObject obj) {
        boolean hasValidOrbit = obj.orbitType != null && !obj.orbitType.equalsIgnoreCase("unknown");
        boolean hasValidLongitude = obj.longitude != 0;
        boolean recent = obj.daysOld < 15000;
        boolean hasConjunctions = obj.conjunctionCount >= 1;

        return hasValidOrbit && hasValidLongitude && recent && hasConjunctions;
    }

    private String classifyRisk(double drift) {
        if (drift > 50) return "High";
        if (drift > 10) return "Moderate";
        return "Low";
    }

    private void saveCSV(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("RecordID,SatelliteName,Country,OrbitType,LaunchYear,LaunchSite,Longitude,AvgLongitude,Geohash,DaysOld,ConjunctionCount,StillInOrbit,RiskLevel\n");

            for (SpaceObject obj : spaceObjects) {
                boolean still = isStillInOrbit(obj);
                String risk = classifyRisk(obj.getOrbitalDrift());

                writer.write(obj.recordId + "," + obj.satelliteName + "," + obj.country + "," +
                             obj.orbitType + "," + obj.launchYear + "," + obj.launchSite + "," +
                             obj.longitude + "," + obj.avgLongitude + "," + obj.geohash + "," +
                             obj.daysOld + "," + obj.conjunctionCount + "," +
                             still + "," + risk + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }

    private void saveExitedTXT(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Exited Debris Report\n");
            writer.write("Total in orbit: " + inOrbitCount + "\n");
            writer.write("Total exited: " + exitedCount + "\n\n");

            for (SpaceObject obj : exitedObjects) {
                writer.write("RecordID: " + obj.recordId + ", Name: " + obj.satelliteName +
                             ", Country: " + obj.country + ", OrbitType: " + obj.orbitType +
                             ", LaunchYear: " + obj.launchYear + ", LaunchSite: " + obj.launchSite +
                             ", Longitude: " + obj.longitude + ", AvgLongitude: " + obj.avgLongitude +
                             ", Geohash: " + obj.geohash + ", DaysOld: " + obj.daysOld + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error writing TXT: " + e.getMessage());
        }
    }
}
