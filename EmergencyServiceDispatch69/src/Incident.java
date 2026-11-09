
 

public class Incident {
    private String incidentId;
    private String reporterName;
    private String location;
    private String emergencyType;
    private String description;
    private int priority;
    private boolean dispatched;

    public Incident(String incidentId, String reporterName,
            String location, String emergencyType,
            String description, int priority) {

        this.incidentId = incidentId;
        this.reporterName = reporterName;
        this.location = location;
        this.emergencyType = emergencyType;
        this.description = description;
        this.priority = priority;
        this.dispatched = false;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getLocation() {
        return location;
    }

    public String getEmergencyType() {
        return emergencyType;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isDispatched() {
        return dispatched;
    }

    public void setDispatched(boolean dispatched) {
        this.dispatched = dispatched;
    }

    public String getPriorityLabel() {
        switch (priority) {
            case 1:
                return "HIGH";
            case 2:
                return "MEDIUM";
            default:
                return "LOW";
        }
    }
}