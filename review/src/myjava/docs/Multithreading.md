# .˖🛸── .✦ Multithreading Trong Java

---

## 🌐 1. Giới Thiệu

_**Multithreading** là kỹ thuật cho phép một chương trình thực thi nhiều luồng (thread) cùng lúc, giúp tận dụng tốt hơn tài nguyên CPU và cải thiện hiệu suất chương trình._


### 👣  So sánh: Multithreading, Parallelism,  Concurrency vs Asynchronization vs Synchronization

| Khái niệm             | Định nghĩa                                                                 | Mục tiêu chính                          | Ví dụ thực tế                                                                 | Java hỗ trợ qua                                                                 |
|-----------------------|----------------------------------------------------------------------------|----------------------------------------|--------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| **Multithreading**     | Kỹ thuật cho phép một chương trình tạo và thực thi nhiều luồng (thread)   | Tăng hiệu năng bằng cách chia nhỏ công việc | Một app vừa tải ảnh vừa hiển thị UI                                        | `Thread`, `Runnable`, `ExecutorService`, `Future`                               |
| **Concurrency**        | Nhiều task cùng *tiến trình* nhưng không cần *chạy song song*             | Quản lý nhiều tác vụ đồng thời          | Đầu bếp nấu 3 món nhưng chuyển qua lại giữa các món                         | `Thread`, `Executor`, `CompletableFuture`, `synchronized`                       |
| **Parallelism**        | Nhiều task được *thực thi cùng lúc trên nhiều CPU/nhân xử lý*            | Tối đa hóa sử dụng CPU                 | 3 đầu bếp, mỗi người nấu một món cùng lúc                                    | `ForkJoinPool`, `parallelStream()`, `CompletableFuture`                        |
| **Asynchronization**   | Task được xử lý không đồng bộ (không chờ kết quả)                        | Không block dòng chính, tăng phản hồi  | Gửi email → không cần đợi kết quả mới làm việc tiếp                         | `CompletableFuture`, `Future`, callback, event-loop                            |
| **Synchronization**    | Điều khiển việc truy cập dữ liệu dùng chung giữa nhiều thread            | Tránh lỗi race condition               | Nhiều người ghi vào sổ quỹ → cần khóa lại khi ghi để không ghi đè nhau     | `synchronized`, `Lock`, `ReentrantLock`, `Semaphore`                           |

---

## 📌 Hình ảnh hóa nhanh

- **Multithreading**: Một người làm nhiều việc nhưng chuyển qua lại liên tục.
- **Concurrency**: Nhiều việc được lập lịch xen kẽ nhau (không cần song song).
- **Parallelism**: Nhiều người làm việc **đúng nghĩa cùng lúc** (đa nhân CPU).
- **Asynchronous**: Giao việc rồi bỏ đó, không cần chờ kết quả.
- **Synchronization**: Khi nhiều người cùng muốn sửa 1 tờ giấy → cần **khóa** lại trước khi sửa.

---

## 🔁 Mối quan hệ giữa các khái niệm

- `Multithreading` là **một kỹ thuật để hiện thực Concurrency**.
- `Parallelism` là **một dạng đặc biệt của Concurrency** khi có nhiều nhân xử lý.
- `Asynchronous` không nhất thiết dùng nhiều thread, có thể là callback/event.
- `Synchronization` là **cách để điều phối hoạt động giữa các thread** nhằm tránh lỗi.

---

## ✅ Tóm gọn bằng một câu

> **Concurrency** là xử lý *nhiều việc cùng lúc* (dù không song song),  
> **Parallelism** là thực sự *chạy nhiều việc cùng lúc*.  
> **Multithreading** là một công cụ để làm điều đó.  
> **Asynchronous** là không chờ kết quả.  
> **Synchronization** là đảm bảo mọi thứ không bị "đánh nhau".

---

## 🧠 2. Kiến Thức Cơ Bản

### 🔹 2.1 Thread là gì?
- `Thread` là đơn vị nhỏ nhất của một tiến trình có thể được lập lịch và thực thi độc lập.
- Mỗi thread có **stack riêng**, nhưng chia sẻ **heap** với các thread khác trong cùng một process.

### 🔹 2.2 Ưu điểm của Multithreading
- ✅ Tăng hiệu năng
- ✅ Phản hồi tốt hơn trong GUI
- ✅ Tận dụng đa nhân CPU

---

### 🔀 2.3. So sánh Process vs Thread

| Thuộc Tính      | Process                          | Thread                           |
|------------------|-----------------------------------|----------------------------------|
| Bộ nhớ           | Không chia sẻ                    | Chia sẻ heap với thread khác     |
| Độc lập           | Có                              | Không                             |
| Tạo mới           | Tốn tài nguyên                  | Nhẹ, nhanh                       |
| Giao tiếp        | IPC (chậm hơn)                  | Truy cập trực tiếp               |

---

### 🔧 2.4 Cách Tạo Thread Trong Java

```java
// Cách 1: extends Thread
class MyThread extends Thread {
    public void run() {
        System.out.println("Hello from MyThread");
    }
}

// Cách 2: implements Runnable
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Hello from Runnable");
    }
}

// Cách 3: dùng Lambda với Runnable
Runnable r = () -> System.out.println("Hello Lambda");
new Thread(r).start();

```

### 2.4 ⏱️ Trạng thái của Thread

- **NEW** → **RUNNABLE** → **RUNNING**

- Có thể vào: **BLOCKED**, **WAITING**, **TIMED_WAITING**

- Cuối cùng: **TERMINATED** (DEAD)

```
NEW → RUNNABLE → RUNNING → WAITING / BLOCKED / TIMED_WAITING → TERMINATED
```

### 2.5 ⚠️ Các Vấn Đề Trong Lập Trình Đa Luồng

|Vấn đề	|Mô tả|
|:--|:--|
|❗ Race Condition|	Hai thread cùng truy cập và thay đổi dữ liệu → kết quả không xác định.|
|❗ Deadlock|	Hai thread chờ nhau mãi mãi vì khóa lẫn nhau.|
|❗ Livelock|	Thread vẫn hoạt động nhưng không hoàn thành được công việc.|
|❗ Starvation|	Một thread không bao giờ được thực thi vì bị các thread khác lấn át.|

### 2.6 🔐 Cơ Chế Đồng Bộ Hóa (Synchronization)

```java
//🔸 Synchronized Method
public synchronized void doSomething() {
    // code an toàn
}

//🔸 Synchronized Block
synchronized (this) {
    // chỉ đoạn code cần thiết
}

// Volatie
private volatile boolean isRunning = true;

```

### 2.7 📦 Java Concurrency Utilities

|Công cụ|	Mô tả|
|:--|:--|
|ExecutorService|	Tạo và quản lý thread pool|
|Callable & Future|	Xử lý đa luồng có trả về giá trị|
|CountDownLatch|	Đồng bộ nhiều thread đợi nhau hoàn thành|
|CyclicBarrier|	Tất cả thread cùng chờ đến điểm barrier|
|Semaphore|	Kiểm soát số thread truy cập đồng thời|
|BlockingQueue|	Hàng đợi an toàn cho multithreading|
|ReentrantLock|	Linh hoạt hơn synchronized|
|AtomicInteger|	Đảm bảo atomic operations cho biến số nguyên|

VD:
```java
// Thread Pool
ExecutorService executor = Executors.newFixedThreadPool(3);
executor.submit(() -> System.out.println("Task chạy"));
executor.shutdown();

//Callable
Callable<Integer> task = () -> 42;
Future<Integer> future = executor.submit(task);
System.out.println("Kết quả: " + future.get());

```

## 🚀 3. Advances
- ThreadLocal
- ForkJoinPool
- CompletableFuture
- Thread-safe:
  + ConcurrentHashMap
  + CopyOnWriteArrayList
  + ConcurrentLinkedQueue

## 🧭 4. Best Practices

    - ✅ Dùng ExecutorService, tránh tạo new Thread() trực tiếp.
    - ✅ Tránh dùng biến dùng chung giữa các thread (shared mutable state).
    - ✅ Dùng volatile hoặc các lớp Atomic nếu không dùng synchronized.
    - ✅ Ghi log hoặc gán tên thread để dễ debug.
    - ✅ Luôn xử lý InterruptedException.

---
[↩ 🏠︎ Home : ̗̀➛](../../../../List%20Contents.md)