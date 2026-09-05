/**
 * PatientQueueADT.java
 * Interface กลางสำหรับ Algorithm A และ B เพื่อให้ทดสอบ/เปรียบเทียบด้วยโค้ดชุดเดียวกันได้
 * คำสั่งที่ระบบต้องรองรับ: ARRIVE, SERVE, PEEK, DISPLAY, QUEUE_SIZE (+ CANCEL สำหรับ Test Case ที่ 6)
 */
public interface PatientQueueADT {
    void arrive(Patient p);      // ARRIVE
    Patient serve();             // SERVE  -> คืน null ถ้า queue ว่าง
    Patient peek();               // PEEK   -> คืน null ถ้า queue ว่าง
    int size();                  // QUEUE_SIZE
    boolean isEmpty();
    boolean cancel(String patientId); // ยกเลิกรายการ (Test Case 6)
    String display();            // DISPLAY
}
