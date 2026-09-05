import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Algorithm A: Normal Queue (FIFO)
 * ผู้ป่วยที่มาก่อนจะได้รับบริการก่อน (First-Come-First-Served)
 * เลือกใช้ ArrayDeque เพราะ:
 *  - รองรับ operation enqueue/dequeue แบบ O(1) amortized ทั้งสองด้าน
 *  - ไม่มี overhead ของ node/pointer เหมือน LinkedList จึงเร็วและประหยัดหน่วยความจำกว่า
 *  - เป็น class ที่ Java แนะนำให้ใช้แทน Stack/LinkedList เมื่อไม่ต้องการ thread-safety
 */
public class FifoPatientQueue implements PatientQueueADT {
    private final Deque<Patient> queue = new ArrayDeque<>();

    @Override
    public void arrive(Patient p) {
        queue.addLast(p); // enqueue ท้ายคิว
    }

    @Override
    public Patient serve() {
        return queue.pollFirst(); // dequeue จากหัวคิว, คืนค่า null ถ้าว่าง
    }

    @Override
    public Patient peek() {
        return queue.peekFirst();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean cancel(String patientId) {
        Iterator<Patient> it = queue.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(patientId)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public String display() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Patient p : queue) {
            if (!first) sb.append(", ");
            sb.append(p.getId());
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }
}
