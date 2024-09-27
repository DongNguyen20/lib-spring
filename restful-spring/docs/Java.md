# ☕  Java

#### Mục lục:

___
- [Stream](####-I-Stream-🥇)
- [Error & Exception](####-ii-error-&-xception)
- [Multithreading](####-iii-multithreading)
- [Java Synchronization](####-iv-java-synchronization)
- [Networking](####-v-networking)
- [Collections](####-vi-Collections)
- [Interfaces](####-VII-Interfaces)
- [Data Structures](####-VIII-Data-Structures)
- [Advanced](####-IX-Advanced)
- [APIs & Frameworks](####-X-Apis-Framework)
___

#### I. Stream 🥇
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
#### II. Error & Exception
#### III. Multithreading
#### IV. Java Synchronization
#### V. Networking
#### VI. Collections
#### VII. Interfaces
#### VIII. Data Structures
#### IX. Advanced
#### X. Apis Framework


