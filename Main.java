/**
 * Main.java
 * โปรแกรมหลักสำหรับสาธิตระบบ Hospital Patient Queue
 * รวม: Queue Trace, Test Cases, Algorithm Experiment
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("############################################################");
        System.out.println("# Hospital Patient Queue: Algorithm A (FIFO) vs Algorithm B (Priority)");
        System.out.println("############################################################");

        TraceDemo.run(new FifoPatientQueue(), "Algorithm A (FIFO Queue)");
        TraceDemo.run(new PriorityPatientQueue(), "Algorithm B (Priority Queue)");

        TestRunner.runAll();

        ExperimentRunner.run();
    }
}
