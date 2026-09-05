/**
 * Patient.java
 * แทนข้อมูลผู้ป่วยหนึ่งคนในระบบคิวผู้ป่วย (Hospital Patient Queue)
 */
public class Patient {
    private final String id;          // Patient ID เช่น P01
    private final String name;        // ชื่อผู้ป่วย
    private final int arrivalTime;    // เวลาที่มาถึง (หน่วยนามธรรม เช่น นาทีที่ n)
    private final int severityLevel;  // ระดับความรุนแรง: 1=Critical, 2=Urgent, 3=Normal
    private final int serviceTime;    // เวลาที่ใช้ในการให้บริการ (นาที)

    public Patient(String id, String name, int arrivalTime, int severityLevel, int serviceTime) {
        this.id = id;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.severityLevel = severityLevel;
        this.serviceTime = serviceTime;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getArrivalTime() { return arrivalTime; }
    public int getSeverityLevel() { return severityLevel; }
    public int getServiceTime() { return serviceTime; }

    public String severityLabel() {
        switch (severityLevel) {
            case 1: return "Critical";
            case 2: return "Urgent";
            case 3: return "Normal";
            default: return "Unknown";
        }
    }

    @Override
    public String toString() {
        return id + "(" + severityLabel() + ",t=" + arrivalTime + ")";
    }
}
