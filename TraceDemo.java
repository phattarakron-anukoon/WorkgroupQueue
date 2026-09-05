/**
 * TraceDemo.java
 * ส่วนที่ 3: Queue Trace อย่างน้อย 10 Operations สำหรับทั้ง Algorithm A (FIFO) และ B (Priority)
 * ใช้ชุดข้อมูลบังคับ: P01 Normal, P02 Urgent, P03 Normal, P04 Critical, P05 Urgent
 */
public class TraceDemo {

    private static Patient[] dataset() {
        return new Patient[]{
                new Patient("P01", "Somchai", 1, 3, 5),  // Normal
                new Patient("P02", "Malee",   2, 2, 8),  // Urgent
                new Patient("P03", "Anong",   3, 3, 4),  // Normal
                new Patient("P04", "Wichai",  4, 1, 10), // Critical
                new Patient("P05", "Sunee",   5, 2, 6)   // Urgent
        };
    }

    public static void run(PatientQueueADT q, String label) {
        Patient[] d = dataset();
        System.out.println("\n=== Queue Trace: " + label + " ===");
        System.out.printf("%-5s%-14s%-22s%-22s%-10s%n", "Step", "Operation", "Queue Before", "Queue After", "Output");

        int step = 1;
        step = arrive(q, d[0], step);
        step = arrive(q, d[1], step);
        step = arrive(q, d[2], step);
        step = peek(q, step);
        step = arrive(q, d[3], step);
        step = serve(q, step);
        step = arrive(q, d[4], step);
        step = serve(q, step);
        step = size(q, step);
        step = serve(q, step);
        step = serve(q, step);
        step = serve(q, step);
    }

    private static int arrive(PatientQueueADT q, Patient p, int step) {
        String before = q.display();
        q.arrive(p);
        String after = q.display();
        System.out.printf("%-5d%-14s%-22s%-22s%-10s%n", step, "ARRIVE " + p.getId(), before, after, "-");
        return step + 1;
    }

    private static int serve(PatientQueueADT q, int step) {
        String before = q.display();
        Patient served = q.serve();
        String after = q.display();
        String out = served == null ? "EMPTY" : served.getId();
        System.out.printf("%-5d%-14s%-22s%-22s%-10s%n", step, "SERVE", before, after, out);
        return step + 1;
    }

    private static int peek(PatientQueueADT q, int step) {
        String before = q.display();
        Patient p = q.peek();
        String out = p == null ? "EMPTY" : p.getId();
        System.out.printf("%-5d%-14s%-22s%-22s%-10s%n", step, "PEEK", before, before, out);
        return step + 1;
    }

    private static int size(PatientQueueADT q, int step) {
        String before = q.display();
        int s = q.size();
        System.out.printf("%-5d%-14s%-22s%-22s%-10s%n", step, "QUEUE_SIZE", before, before, String.valueOf(s));
        return step + 1;
    }
}
