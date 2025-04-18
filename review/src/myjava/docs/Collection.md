# Java Collection Framework 🗂️
```
Collection
    |-- List
    |       |-- ArrayList
    |       |-- LinkedList
    |       |-- Vector
    |
    |-- Set
    |       |-- HashSet
    |       |-- LinkedHashSet
    |       |-- TreeSet
    |
    |-- Queue
            |-- PriorityQueue
            |-- ArrayDeque
Map
    |-- HashMap
    |-- LinkedHashMap
    |-- TreeMap
    |-- Hashtable
```
Java Collection Framework là một tập hợp các giao diện và lớp được sử dụng để lưu trữ và thao tác với các nhóm đối tượng. Framework này bao gồm ba khía cạnh chính:

- **Các giao diện (Interfaces**): Xác định các loại tập hợp (collections) khác nhau.
- **Các lớp triển khai (Implementations)**: Cung cấp các triển khai của các giao diện.
- **Thuật toán (Algorithms)**: Cung cấp các phương thức như tìm kiếm, sắp xếp,vv..

## 1. Các Intefaces
### a. Collection
`Collection` là giao diện gốc của hầu hết các giao diện trong Java Collection Framework. Các phương thức phổ biến:

- _add(E e)_: Thêm phần tử vào tập hợp.
- _remove(Object o)_: Xóa một phần tử.
- _size()_: Trả về số lượng phần tử.
- _contains(Object o)_: Kiểm tra phần tử có tồn tại trong tập hợp.

### b. List
`List` là một tập hợp có thứ tự, cho phép các phần tử trùng lặp. Các phần tử có thể được truy cập thông qua chỉ số (index).

So sánh ⚖️:

|**Thao tác**|**ArrayList**|**LinkedList**|
| :--- | :--- | :--- |
|Truy cập phần tử theo chỉ số (get/set)|	Nhanh, O(1), vì nó sử dụng mảng để truy cập trực tiếp|Chậm, O(n), phải duyệt qua danh sách liên kết để đến phần tử|
|Chèn/xóa ở giữa danh sách|	Chậm, O(n), vì cần phải dịch chuyển các phần tử để duy trì thứ tự|Nhanh, O(1) nếu đã tìm được vị trí, vì chỉ cần thay đổi liên kết của các nút
|Chèn/xóa ở đầu hoặc cuối| Chèn/xóa ở cuối nhanh, O(1); ở đầu chậm, O(n), vì phải dịch chuyển tất cả các phần tử| Nhanh, O(1), vì chỉ cần thay đổi liên kết của các nút đầu/cuối
|Duyệt danh sách| Nhanh hơn trong nhiều trường hợp, đặc biệt khi truy cập ngẫu nhiên|Chậm hơn khi duyệt ngẫu nhiên, nhưng tốt khi truy cập tuần tự|

`Vector` ~ `ArrayList`
- Synchronized 💥 Unsynchronized
- full → x2 size 💥 x1.5 size

### c. Set
`Set` là một tập hợp không chứa phần tử trùng lặp.

### d. Queue 🚦
`Queue` là một cấu trúc dữ liệu theo nguyên tắc FIFO (First In First Out).
Các phương thức chính của Queue bao gồm:

- _add()_: Thêm một phần tử vào cuối hàng đợi.
- _remove()_: Xóa phần tử đầu tiên trong hàng đợi.
- _peek()_: Truy cập phần tử đầu tiên trong hàng đợi mà không xóa nó ra khỏi hàng đợi.
- _poll()_: Lấy và xóa phần tử đầu tiên ra khỏi hàng đợi.
- _offer(E e)_: Thêm phần tử vào hàng đợi.
#### d.1. ArrayDeque
```json
{
  "task": "To do update later"
}
```
#### d.2. PriorityQueue
`// update later`

## 2. Algorithms
- _sort(List<T> list)_: Sắp xếp danh sách theo thứ tự tự nhiên.
- _reverse(List<?> list)_: Đảo ngược thứ tự các phần tử trong danh sách.
- _shuffle(List<?> list)_: Trộn ngẫu nhiên các phần tử trong danh sách.
- _binarySearch(List<? extends Comparable<? super T>> list, T key)_: Tìm kiếm nhị phân trên danh sách đã sắp xếp.

## 3. Iterator 
`Iterator` là một đối tượng dùng để duyệt qua các phần tử của một tập hợp.

Phương thức chính:

- _hasNext()_: Kiểm tra có phần tử tiếp theo không.
- _next()_: Lấy phần tử tiếp theo.
- _remove()_: Xóa phần tử vừa lấy r

## Comparable & Comparator
_interface cũng dùng để so sánh các đối tượng_

|Tiêu chí|Comparable|Comparator|
| :--- | :--- | :---|
|Interface|java.lang.Comparable|java.util.Comparator
|Phương thức|compareTo(Object obj)|compare(Object obj1, Object obj2)
|So sánh|So sánh đối tượng hiện tại với đối tượng được truyền vào|So sánh hai đối tượng khác nhau
|Sửa đổi lớp hiện tại|Phải sửa đổi lớp hiện tại để triển khai Comparable|Không cần sửa đổi lớp hiện tại
|Số lượng phương thức so sánh|Chỉ có một phương thức so sánh, giới hạn trong một tiêu chí|Có thể tạo ra nhiều phương thức so sánh khác nhau
|Mặc định|Dùng để cung cấp trật tự tự nhiên cho đối tượng|Dùng để định nghĩa trật tự tùy chỉnh, có thể thay đổi trật tự dễ dàng


---
[↩ 🏠︎ Home : ̗̀➛](../../../../List Contents.md)
