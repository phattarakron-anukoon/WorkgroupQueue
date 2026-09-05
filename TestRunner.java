import java.util.Random;

/**
 * TestRunner.java
 * ส่วนที่ 8: Test Cases อย่างน้อย 6 กรณี ทดสอบทั้ง Algorithm A (FIFO) และ B (Priority)
 */
public class TestRunner {

    public static void runAll() {
        System.out.println("\n================ TEST CASES ================");
        testNormalCase();
        testEmptyQueue();
        testSingleItem();
        testLargeQueue();
        testSpecialEqualPriority();
        testCancelCase();
    }

    // 1. Normal Case: ชุดข้อมูลบังคับ 5 ผู้ป่วย
    private static void testNormalCase() {
        System.out.println("\n[Test 1] Normal Case");
        Patient[] d = {
                new Patient("P01", "Somchai", 1, 3, 5),
                new Patient("P02", "Malee",   2, 2, 8),
                new Patient("P03", "Anong",   3, 3, 4),
                new Patient("P04", "Wichai",  4, 1, 10),
                new Patient("P05", "Sunee",   5, 2, 6)
        };
        PatientQueueADT fifo = new FifoPatientQueue();
        PatientQueueADT pri = new PriorityPatientQueue();
        for (Patient p : d) { fifo.arrive(p); pri.arrive(p); }

        System.out.print("  Algorithm A (FIFO) service order:     ");
        printServiceOrder(fifo);
        System.out.print("  Algorithm B (Priority) service order: ");
        printServiceOrder(pri);
        System.out.println("  ผลลัพธ์: A ให้บริการตามลำดับมาถึง (P01..P05), B ให้บริการ Critical/Urgent ก่อน Normal");
    }

    // 2. Empty Queue: เรียก SERVE/PEEK ตอน queue ว่าง ต้องไม่ throw exception และคืนค่า null
    private static void testEmptyQueue() {
        System.out.println("\n[Test 2] Empty Queue");
        PatientQueueADT fifo = new FifoPatientQueue();
        PatientQueueADT pri = new PriorityPatientQueue();
        boolean pass = fifo.serve() == null && fifo.peek() == null && fifo.size() == 0
                && pri.serve() == null && pri.peek() == null && pri.size() == 0;
        System.out.println("  serve()/peek() on empty queue return null, size=0 -> " + (pass ? "PASS" : "FAIL"));
    }

    // 3. Single Item: มีผู้ป่วยเพียงคนเดียว
    private static void testSingleItem() {
        System.out.println("\n[Test 3] Single Item");
        Patient p = new Patient("P99", "Nok", 1, 1, 5);
        PatientQueueADT fifo = new FifoPatientQueue();
        PatientQueueADT pri = new PriorityPatientQueue();
        fifo.arrive(p); pri.arrive(p);
        boolean pass = fifo.peek().getId().equals("P99") && fifo.serve().getId().equals("P99") && fifo.isEmpty()
                && pri.peek().getId().equals("P99") && pri.serve().getId().equals("P99") && pri.isEmpty();
        System.out.println("  peek/serve คืนผู้ป่วยเดียวที่มี และ queue ว่างหลัง serve -> " + (pass ? "PASS" : "FAIL"));
    }

    // 4. Large Queue: ผู้ป่วยจำนวนมาก (10,000 คน) สุ่ม severity แล้วตรวจว่าลำดับการ serve ของ B ไม่ลดลง (non-decreasing severity)
    private static void testLargeQueue() {
        System.out.println("\n[Test 4] Large Queue (n = 10,000)");
        Random rnd = new Random(42);
        PatientQueueADT pri = new PriorityPatientQueue();
        for (int i = 0; i < 10_000; i++) {
            int sev = rnd.nextInt(3) + 1;
            pri.arrive(new Patient("P" + i, "N" + i, i, sev, 5));
        }
        int prevSeverity = 0;
        boolean sorted = true;
        int count = 0;
        while (!pri.isEmpty()) {
            Patient p = pri.serve();
            if (p.getSeverityLevel() < prevSeverity) { sorted = false; break; }
            prevSeverity = p.getSeverityLevel();
            count++;
        }
        System.out.println("  Served " + count + " patients, non-decreasing severity order -> " + (sorted ? "PASS" : "FAIL"));
    }

    // 5. Special/Edge Case: priority เท่ากันหมด -> ต้อง tie-break ด้วย arrival time (FIFO ภายในกลุ่ม)
    private static void testSpecialEqualPriority() {
        System.out.println("\n[Test 5] Special/Edge Case: severity เท่ากันทั้งหมด");
        PatientQueueADT pri = new PriorityPatientQueue();
        String[] ids = {"P01", "P02", "P03", "P04"};
        for (int i = 0; i < ids.length; i++) {
            pri.arrive(new Patient(ids[i], "N", i + 1, 2, 5)); // severity เท่ากันหมด = 2 (Urgent)
        }
        StringBuilder order = new StringBuilder();
        while (!pri.isEmpty()) order.append(pri.serve().getId()).append(" ");
        boolean pass = order.toString().trim().equals("P01 P02 P03 P04");
        System.out.println("  ลำดับที่ได้: " + order.toString().trim() + " (คาดหวัง P01 P02 P03 P04 ตาม Arrival Time) -> " + (pass ? "PASS" : "FAIL"));
    }

    // 6. Cancel Case: ยกเลิกรายการกลาง queue และยกเลิกรายการที่ไม่มีอยู่จริง
    private static void testCancelCase() {
        System.out.println("\n[Test 6] Cancel Case");
        PatientQueueADT fifo = new FifoPatientQueue();
        PatientQueueADT pri = new PriorityPatientQueue();
        Patient[] d = {
                new Patient("P01", "A", 1, 3, 5),
                new Patient("P02", "B", 2, 2, 5),
                new Patient("P03", "C", 3, 1, 5)
        };
        for (Patient p : d) { fifo.arrive(p); pri.arrive(p); }

        boolean cancelMiddleFifo = fifo.cancel("P02");   // รายการอยู่กลาง queue
        boolean cancelGhostFifo = fifo.cancel("P999");   // รายการไม่มีอยู่จริง
        boolean cancelMiddlePri = pri.cancel("P02");
        boolean cancelGhostPri = pri.cancel("P999");

        System.out.println("  FIFO: cancel P02(exists)=" + cancelMiddleFifo + ", cancel P999(ไม่มี)=" + cancelGhostFifo
                + ", queue หลังยกเลิก=" + fifo.display());
        System.out.println("  Priority: cancel P02(exists)=" + cancelMiddlePri + ", cancel P999(ไม่มี)=" + cancelGhostPri
                + ", queue หลังยกเลิก=" + pri.display());
        boolean pass = cancelMiddleFifo && !cancelGhostFifo && cancelMiddlePri && !cancelGhostPri;
        System.out.println("  ผลรวม -> " + (pass ? "PASS" : "FAIL"));
    }

    private static void printServiceOrder(PatientQueueADT q) {
        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) sb.append(q.serve().getId()).append(" ");
        System.out.println(sb.toString().trim());
    }
}
