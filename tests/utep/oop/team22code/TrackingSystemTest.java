package utep.oop.team22code;

import java.util.List;

public class TrackingSystem {

    private List<SpaceObject> spaceObjects;

    public TrackingSystem(List<SpaceObject> spaceObjects) {
        this.spaceObjects = spaceObjects;
    }

    public void trackByCategory(String category) {
        System.out.println("\n--- Tracking: " + category + " ---");

        boolean found = false;

        for (SpaceObject obj : spaceObjects) {
            String nameLower = obj.satelliteName.toLowerCase();

            switch (category.toLowerCase()) {
                case "rocket body":
                    if (nameLower.contains("rocket") || nameLower.contains("body")) {
                        printInfo(obj);
                        found = true;
                    }
                    break;
                case "payload":
                    if (nameLower.contains("payload")) {
                        printInfo(obj);
                        found = true;
                    }
                    break;
                case "debris":
                    if (obj instanceof Debris) {
                        printInfo(obj);
                        found = true;
                    }
                    break;
                case "unknown":
                    if (obj.orbitType.equalsIgnoreCase("unknown") ||
                        obj.satelliteName.equalsIgnoreCase("unknown")) {
                        printInfo(obj);
                        found = true;
                    }
                    break;
                default:
                    System.out.println("Unknown category.");
                    return;
            }
        }

        if (!found) {
            System.out.println("No objects found for category: " + category);
        }
    }

    private void printInfo(SpaceObject obj) {
        System.out.println(obj.getBasicInfo());
    }
}
