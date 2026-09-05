import java.util.Random;

/**
 * ExperimentRunner.java
 * ส่วนที่ 9: Algorithm Experiment
 * วัด Execution Time ของ enqueue ทั้งหมด n รายการ ตามด้วย dequeue ทั้งหมด n รายการ
 * n = 100, 1000, 10000, 50000
 * วิธีวัด: nanoTime, มี warm-up round, ใช้ seed คงที่ (สร้าง severity แบบสุ่ม), เฉลี่ย 5 รอบ
 */
public class ExperimentRunner {

    private static final int[] SIZES = {100, 1_000, 10_000, 50_000};
    private static final int ROUNDS = 5;
    private static final long SEED = 2026L;

    public static void run() {
        System.out.println("\n============ ALGORITHM EXPERIMENT (Execution Time) ============");
        System.out.printf("%-10s%-25s%-25s%n", "n", "Algorithm A: FIFO (ms)", "Algorithm B: Priority (ms)");

        // Warm-up JIT ก่อนวัดจริง
        warmUp();

        for (int n : SIZES) {
            double fifoAvg = measureFifo(n);
            double priAvg = measurePriority(n);
            System.out.printf("%-10d%-25.3f%-25.3f%n", n, fifoAvg, priAvg);
        }
    }

    private static void warmUp() {
        for (int i = 0; i < 3; i++) {
            measureFifo(1000);
            measurePriority(1000);
        }
    }

    private static int[] generateSeverities(int n, long seed) {
        Random rnd = new Random(seed);
        int[] sev = new int[n];
        for (int i = 0; i < n; i++) sev[i] = rnd.nextInt(3) + 1;
        return sev;
    }

    private static double measureFifo(int n) {
        long total = 0;
        for (int r = 0; r < ROUNDS; r++) {
            int[] sev = generateSeverities(n, SEED + r);
            PatientQueueADT q = new FifoPatientQueue();
            long start = System.nanoTime();
            for (int i = 0; i < n; i++) q.arrive(new Patient("P" + i, "N", i, sev[i], 5));
            while (!q.isEmpty()) q.serve();
            long end = System.nanoTime();
            total += (end - start);
        }
        return (total / (double) ROUNDS) / 1_000_000.0; // ms
    }

    private static double measurePriority(int n) {
        long total = 0;
        for (int r = 0; r < ROUNDS; r++) {
            int[] sev = generateSeverities(n, SEED + r);
            PatientQueueADT q = new PriorityPatientQueue();
            long start = System.nanoTime();
            for (int i = 0; i < n; i++) q.arrive(new Patient("P" + i, "N", i, sev[i], 5));
            while (!q.isEmpty()) q.serve();
            long end = System.nanoTime();
            total += (end - start);
        }
        return (total / (double) ROUNDS) / 1_000_000.0; // ms
    }
}
