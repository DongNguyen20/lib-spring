# ☕  Java

#### Mục lục:

___
- [Stream](#I-Stream-)
- [Error & Exception](#ii-error--exception-)
- [Multithreading](#iii-multithreading)
- [Java Synchronization](#iv-java-synchronization)
- [Networking](#v-networking)
- [Collections](#vi-Collections)
- [Interfaces](#VII-Interfaces)
- [Data Structures](#VIII-Data-Structures)
- [Advanced](#IX-Advanced)
- [APIs & Frameworks](#X-Apis-Framework)
___

#### I. Stream 🌌
_Java Stream API là một tính năng được giới thiệu từ Java 8, cung cấp một cách tiếp cận hiện đại để xử lý các tập hợp 
dữ liệu theo phong cách lập trình hàm (functional programming). Stream cho phép bạn thực hiện các thao tác như lọc,
sắp xếp, ánh xạ (mapping), và thu thập dữ liệu một cách đơn giản và hiệu quả mà không cần thay đổi dữ liệu gốc._

#### 1. Các đặc điểm chính
    
- **No storage**: _Stream không lưu trữ dữ liệu, nó chỉ là một chuỗi các hoạt động xử lý dữ liệu dựa trên các nguồn dữ liệu
như Collection, Arrays, I/O resources,..._ 
- **Single use**:  _Stream chỉ có thể được sử dụng một lần. Sau khi sử dụng, bạn cần tạo một Stream mới nếu muốn xử lý lại._
- **Lazy**: _Các thao tác trên Stream là lười biếng, tức là chúng chỉ thực sự được thực thi khi có một "operation kết thúc" (terminal operation)._
#### 2. Các loại `Operation`
##### a. Intermediate Operations
Các hoạt động này trả về một Stream mới và lười biếng, nghĩa là chúng sẽ không được thực thi cho đến khi có một "terminal operation". Một số intermediate operations phổ biến:

- **filter(Predicate)**: _Lọc các phần tử theo điều kiện._
- **map(Function)**: _Biến đổi các phần tử._
- **flatMap(Function)**: _Biến đổi và "phẳng hóa" các Stream lồng nhau._
- **distinct()**: _Loại bỏ các phần tử trùng lặp._
- **sorted()**: _Sắp xếp các phần tử._
- **peek(Consumer)**: _Thực thi hành động phụ trên mỗi phần tử._

##### b. Terminal Operations
Các hoạt động này thực thi chuỗi thao tác và trả về kết quả (hoặc có hiệu ứng phụ):
- **collect(Collector)**: _Thu thập các phần tử thành một Collection hoặc một giá trị._
- **forEach(Consumer)**: _Thực thi một hành động trên mỗi phần tử._
- **reduce(BinaryOperator)**: _Giảm dần các phần tử thành một giá trị duy nhất._
- **count()**: _Đếm số phần tử._
- **anyMatch(), allMatch(), noneMatch()**: _Kiểm tra điều kiện._
#### 3. Lợi ích của Stream
- Code ngắn gọn và dễ hiểu: Cú pháp rõ ràng và tập trung vào "cái gì" thay vì "như thế nào".
- Tính năng lười biếng: Các thao tác được thực thi khi cần, giúp tăng hiệu suất.
- Tối ưu hóa xử lý song song: Stream hỗ trợ xử lý song song một cách dễ dàng với parallelStream().
##### 👉🏻 VD sử dụng parallelStream 
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.parallelStream()
    .mapToInt(Integer::intValue)
    .sum();
System.out.println(sum); // Output: 15
```
##### 💡 Lưu Ý Khi Sử Dụng Stream
+ Không nên thay đổi trạng thái của phần tử trong Stream (phương thức peek() chỉ nên sử dụng cho việc ghi log hoặc debug).
+ Tránh sử dụng parallelStream() trên các tập dữ liệu nhỏ, vì chi phí tạo luồng song song có thể cao hơn lợi ích đạt được.
+ Stream là một chiều, không thể sử dụng lại.
#### II. Error & Exception ⚡
##### 1. What❓
_Trong Java, cả `Error` và `Exception` đều là các lớp con của lớp `Throwable`, được sử dụng để chỉ ra các tình huống bất thường hoặc lỗi xảy ra trong chương trình._
![img.png](img.png)

###### _a. Error_
- Error là những vấn đề nghiêm trọng, thường liên quan đến môi trường thực thi của chương trình (runtime environment) mà không thể hoặc không nên xử lý trong mã nguồn của bạn.
- Các lỗi này thường do hệ thống gây ra, ví dụ như thiếu bộ nhớ (OutOfMemoryError), lỗi trong JVM (Java Virtual Machine), hoặc các lỗi hệ thống khác.
- Chúng thường không được bắt và xử lý vì không thể phục hồi được, và việc xử lý chúng là không thực tế.

Ví dụ về các loại Error:

+ **OutOfMemoryError**: Xảy ra khi JVM không còn đủ bộ nhớ để cấp phát.
+ **StackOverflowError**: Xảy ra khi một phương thức đệ quy gọi liên tục mà không có điểm dừng, dẫn đến tràn stack.
+ **VirtualMachineError**: Xảy ra khi JVM gặp một lỗi nghiêm trọng.

Ví dụ về Error:
```java
public class ErrorExample {
    public static void main(String[] args) {
        try {
            causeStackOverflowError(1);
        } catch (StackOverflowError e) {
            System.out.println("Caught StackOverflowError: " + e.getMessage());
        }
    }

    public static void causeStackOverflowError(int num) {
        causeStackOverflowError(num); // Gọi đệ quy không có điểm dừng
    }
}
```
###### b. Exception
- Exception là các vấn đề xảy ra trong quá trình thực thi chương trình mà có thể và nên xử lý trong mã nguồn.
- Chúng thường xảy ra do các lỗi logic trong chương trình hoặc các vấn đề mà có thể dự đoán và xử lý được.
- Exception được chia thành hai loại chính: `Checked Exception` và `Unchecked Exception`.

###### b.1 Checked Exception
- Checked Exception là các ngoại lệ được kiểm tra tại thời điểm biên dịch (compile-time).
- Các ngoại lệ này yêu cầu phải xử lý chúng bằng cách sử dụng khối try-catch hoặc khai báo chúng trong chữ ký phương thức bằng throws.
- Ví dụ: IOException, SQLException, ClassNotFoundException.
###### b.2 Unchecked Exception
- Unchecked Exception là các ngoại lệ xảy ra trong thời gian chạy (runtime) và không được kiểm tra tại thời điểm biên dịch.
- Chúng thường xảy ra do lỗi logic trong chương trình, chẳng hạn như chia cho 0, truy cập vào mảng vượt quá giới hạn, hoặc dereferencing một đối tượng null.
- Ví dụ: NullPointerException, ArrayIndexOutOfBoundsException, ArithmeticException.
###### c. Xử lý
```java
try {
    // Mã có thể gây ra ngoại lệ
} catch (ExceptionType1 e1){
        // Xử lý ngoại lệ
} catch (ExceptionType2 e2) {
        // Xử lý ngoại lệ
} finally {
    // Mã sẽ luôn chạy
}
```
#### 2. So sánh
|Đặc điểm |	Error | Exception |
| :--- | :--- | :--- |
|Phạm vi |Lỗi hệ thống hoặc JVM |Lỗi logic hoặc ngoại lệ trong chương trình |
|Có thể xử lý| Thường không nên xử lý| Nên được xử lý bằng `try-catch` hoặc `throws`|
|Kiểm tra lúc biên dịch | Không được kiểm tra |checked Exception được kiểm tra, Unchecked Exception thì không|
|Ví dụ | OutOfMemoryError, StackOverflowError | IOException, SQLException, NullPointerException|
#### 2. `throws` & `throw`
- `throw` được sử dụng để "ném" một ngoại lệ (exception) cụ thể ra khỏi một khối mã.
```java
throw new Exception("Error message");
``` 
- `throws` được sử dụng trong phần khai báo của phương thức để chỉ ra rằng phương thức này có thể ném một hoặc nhiều loại ngoại lệ.
```java
public void someMethod() throws IOException, SQLException {
    // method logic
}
```
So sánh :

|Đặc điểm |	throw | throws |
| :--- | :--- | :--- |
|Vị trí |Bên trong thân phương thức |Trong phần khai báo của phương thức|
|Chức năng|Ném một ngoại lệ cụ thể|Khai báo ngoại lệ mà phương thức có thể ném|
|Số lượng|Chỉ ném được một ngoại lệ tại một thời điểm|Có thể khai báo nhiều loại ngoại lệ, cách nhau bằng dấu phẩy|
|Loại sử dụng| Thường sử dụng với cả checked và unchecked| Chủ yếu được sử dụng cho checked exceptions|

#### III. Multithreading
##### 1. 📈 Thread
- `Thread` là đơn vị nhỏ nhất của quá trình thực thi trong một ứng dụng. Một chương trình Java mặc định có ít nhất một luồng chính (main thread) thực thi phương thức `main()`.
- `Multithreading` là kỹ thuật chạy đồng thời nhiều luồng trong cùng một quá trình (process). Mỗi luồng có ngữ cảnh thực thi riêng (stack riêng), nhưng chúng chia sẻ bộ nhớ chung của quá trình.

###### Lợi ích của Multithreading
- **Tăng hiệu suất**: Multithreading giúp tận dụng tối đa CPU, đặc biệt là các CPU đa lõi, bằng cách thực thi các luồng đồng thời.
- **Đáp ứng tốt hơn**: Ứng dụng có thể xử lý nhiều tác vụ cùng một lúc, giúp giảm thời gian phản hồi và tăng trải nghiệm người dùng.
- **Đơn giản hóa thiết kế**: Một số vấn đề phức tạp (ví dụ như giao tiếp mạng hoặc xử lý I/O) trở nên dễ quản lý hơn khi được tách thành các luồng riêng biệt

###### a. Lifecycle
![img_2.png](img_2.png)
- `NEW` : Đây là trạng thái khi luồng vừa được khởi tạo bằng phương thức khởi tạo của lớp Thread nhưng chưa được start(). Ở trạng thái này, luồng được tạo ra nhưng chưa được cấp phát tài nguyên và cũng chưa chạy. Nếu luồng đang ở trạng thái này mà ta gọi các phương thức ép buộc stop,resume,suspend … sẽ là nguyên nhân sảy ra ngoại lệ IllegalThreadStateException .
- `RUNNABLE` : Sau khi gọi phương thức start() thì luồng test đã được cấp phát tài nguyên và các lịch điều phối CPU cho luồng test cũng bắt đầu có hiệu lực. Ở đây, chúng ta dùng trạng thái là Runnable chứ không phải Running, vì luồng không thực sự luôn chạy mà tùy vào hệ thống mà có sự điều phối CPU khác nhau.
- `WAITING` : Thread chờ không giới hạn cho đến khi một luồng khác đánh thức nó.
- `TIMED_WAITING` : Thread chờ trong một thời gian nhất định, hoặc là có một luồng khác đánh thức nó.
- `BLOCKED`: Đây là 1 dạng của trạng thái “Not Runnable”, là trạng thái khi Thread vẫn còn sống, nhưng hiện tại không được chọn để chạy. Thread chờ một monitor để unlock một đối tượng mà nó cần.
- `TERMINATED` : Một thread ở trong trạng thái terminated hoặc dead khi phương thức run() của nó bị thoát.
##### 2. Cách tạo luồng 
Trong java ta có thể tạo ra một luồng bằng một trong hai cách sau: tạo 1 đối tượng của lớp được `extend` từ class **Thread** hoặc `implements` từ interface **Runnable**.
###### a. Kế thừa lớp Thread
```java
class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " is running: " + i);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();

        thread1.start(); // Bắt đầu luồng 1
        thread2.start(); // Bắt đầu luồng 2
    }
}
```
`💥 Lưu ý:` Sau khi `start` một thread, nó không bao giờ có thể được start lại. Nếu cố gắng start sẽ throw `IllegalThreadStateException`

###### b. Implement interface Runnable
```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " is running: " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyRunnable());
        Thread thread2 = new Thread(new MyRunnable());

        thread1.start(); // Bắt đầu luồng 1
        thread2.start(); // Bắt đầu luồng 2
    }
}
```

##### ⚔️So sánh
|Tiêu chí|extends Thread | 	implements Runnable |
| :--- | :--- | :--- |
|Kế thừa |Đã kế thừa từ Thread, không thể kế thừa lớp khác |Có thể kế thừa một lớp khác do không bị hạn chế|
|Tách biệt logic|Logic của luồng và việc quản lý luồng là cùng một lớp|Tách biệt rõ ràng logic của luồng và quản lý luồng|
|Khi nào sử dụng|Đơn giản hóa việc mã hóa, thay đổi hành vi của method trong Thread|Sử dụng các dịch vụ quản lý luồng như `ExecutorService`|
###### c. Một số method thường dùng
- **suspend()**: tam dùng hoạt động của 1 luồng bằng cách ngưng cung cấp CPU cho luồng này.(không ngừng ngay tức thì)
- **resume()**: làm luồng chạy lại sau khi bị dừng do `suspend()`
- **stop()**: kết thúc method `run()` bằng cách ném ra 1 ngoại lệ ThreadDeath. Có thể gây deadlock
- **destroy()**: dừng hẳn luồng.
- **yeild()**: khi gọi phương thức này luồng sẽ bị ngừng cấp CPU và nhường cho luồng tiếp theo trong hàng chờ Ready. Luồng không phải ngưng cấp CPU như suspend mà chỉ ngưng cấp trong lần nhận CPU đó mà thôi.
- **sleep()**: tạm dừng luồng trong một khoảng thời gian millisecond.
- **join()**: thông báo rằng hãy chờ thread này hoàn thành rồi thread cha mới được tiếp tục chạy.
- **join(long)** : Thread cha cần phải đợi millisecond mới được tiếp tục chạy, kể từ lúc gọi join(long). Nếu tham số millis = 0 nghĩa là đợi cho tới khi luồng này kết thúc.
- **getPriority()** : Trả về mức độ ưu tiên của thread.
>Java có định nghĩa sẵn 3 mức ưu tiên chuẩn như sau:
>
>Thread.**MIN_PRIORITY** (giá trị 01);
>Thread.**NORM_PRIORITY** (giá trị 05);
>Thread.**MAX_PRIORITY** (giá trị 10)

#### IV. Java Synchronization
#### V. Networking
#### VI. Collections
#### VII. Interfaces
#### VIII. Data Structures
#### IX. Advanced
#### X. Apis Framework
