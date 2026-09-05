import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Algorithm B: Priority Queue
 * ใช้ระดับความรุนแรง (Severity) เป็นลำดับความสำคัญ: 1=Critical มาก่อน, 2=Urgent, 3=Normal
 * หาก Severity เท่ากัน ให้พิจารณา Arrival Time (มาก่อนได้ก่อน) เพื่อความยุติธรรม (FIFO ภายในกลุ่มเดียวกัน)
 *
 * เลือกใช้ PriorityQueue<Patient> เพราะ:
 *  - โครงสร้างข้อมูลเป็น Binary Heap ทำให้ offer()/poll() มี Time Complexity O(log n)
 *  - เหมาะกับปัญหาที่ต้องดึง "รายการสำคัญที่สุด" ออกก่อนเสมอ โดยไม่สนลำดับการเข้ามา
 *  - Comparator ออกแบบเฉพาะสำหรับ Patient (severity แล้วตามด้วย arrivalTime)
 */
public class PriorityPatientQueue implements PatientQueueADT {

    private static final Comparator<Patient> PATIENT_COMPARATOR =
            Comparator.comparingInt(Patient::getSeverityLevel)
                      .thenComparingInt(Patient::getArrivalTime);

    private final PriorityQueue<Patient> pq = new PriorityQueue<>(PATIENT_COMPARATOR);

    @Override
    public void arrive(Patient p) {
        pq.offer(p);
    }

    @Override
    public Patient serve() {
        return pq.poll();
    }

    @Override
    public Patient peek() {
        return pq.peek();
    }

    @Override
    public int size() {
        return pq.size();
    }

    @Override
    public boolean isEmpty() {
        return pq.isEmpty();
    }

    @Override
    public boolean cancel(String patientId) {
        Iterator<Patient> it = pq.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(patientId)) {
                it.remove(); // O(n); PriorityQueue ไม่รองรับการลบตำแหน่งกลางแบบ O(log n)
                return true;
            }
        }
        return false;
    }

    @Override
    public String display() {
        // สร้างสำเนาเรียงลำดับเพื่อแสดงผลตามลำดับที่จะถูกเรียก (ไม่ใช่ลำดับภายใน heap array)
        PriorityQueue<Patient> copy = new PriorityQueue<>(pq);
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        while (!copy.isEmpty()) {
            if (!first) sb.append(", ");
            sb.append(copy.poll().getId());
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }
}
