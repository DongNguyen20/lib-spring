**_API doc_**: `http://localhost:8080/swagger-ui/index.html`

**Spring WebFlux** là một framework reactive được giới thiệu trong Spring 5 để xây dựng các ứng dụng Web có khả năng xử lý đồng thời rất tốt, không chặn (non-blocking). Nó sử dụng các thư viện như Reactor (dựa trên Project Reactor) để hỗ trợ lập trình phản ứng (reactive programming) trong việc xử lý các yêu cầu HTTP.

✅ Reactive Programming là gì?

Reactive Programming là một mô hình lập trình dựa trên data stream và xử lý các thay đổi (event-driven). Mục tiêu chính của reactive là giúp hệ thống:

- Phản ứng nhanh với sự kiện đến (ví dụ: người dùng click, dữ liệu từ API, message từ Kafka…)

- Không chặn (non-blocking) khi đang chờ một tác vụ (ví dụ: đọc file, gọi API, truy vấn DB)

- Khả năng mở rộng cao (scalable) vì không cần một luồng cho mỗi yêu cầu

Thay vì chờ kết quả từ một tác vụ, bạn đăng ký (subscribe) và xử lý khi kết quả đến.

✅ Reactive kết hợp với Spring WebFlux
WebFlux Controller trả về Mono<T> hoặc Flux<T>

Dùng WebClient để gọi API bất đồng bộ

Dùng R2DBC hoặc MongoReactiveTemplate để truy vấn DB reactive

⚙ Các method phổ biến khác trong Reactive Stream

| Method	 | Mô tả                                    |
|:-------|:-----------------------------------------|
|  map()  | Biến đổi từng phần tử (giống Stream API) |
|filter()	|Lọc phần tử thỏa điều kiện
|flatMap()	|Chuyển đổi async + gộp kết quả Mono/Flux vào stream
|doOnNext()	|Tác vụ phụ trợ (log, debug) mỗi phần tử
|doOnError()|	Xử lý phụ khi có lỗi
|onErrorResume()|	Nếu lỗi thì fallback sang luồng khác
|concatWith()	|Nối luồng này với luồng khác
|delayElements()|	Trì hoãn phát từng phần tử (giả lập async)
|zip()	|Gộp 2 Flux thành từng cặp (a, b)
|take(n)|	Lấy n phần tử đầu tiên

### 1. Các thành phần cơ bản của WebFlux
#### 1.1 WebFlux và Non-blocking I/O
- WebFlux hoạt động theo non-blocking và asynchronous (không đồng bộ), tức là không chặn luồng khi xử lý các yêu cầu.

- Dùng Reactive Streams (Project Reactor) để tạo ra các đối tượng như Mono và Flux thay vì các đối tượng đồng bộ truyền thống như String hoặc ResponseEntity.

### 1.2 Các Lớp Quan Trọng
- **Mono**: Đại diện cho một đối tượng phản ứng (0 hoặc 1 phần tử).

- **Flux**: Đại diện cho một chuỗi các đối tượng phản ứng (0 đến nhiều phần tử).

- **WebFlux Handler**: Tương tự như Controller trong Spring MVC nhưng sử dụng các lớp như Mono hoặc Flux để trả về kết quả.

- **WebFilter**: Tương tự Filter trong Spring MVC, dùng để kiểm tra và xử lý request/response.

### 1.3. WebFlux với Server
WebFlux có thể hoạt động trên hai loại máy chủ:
- **Reactor Netty**: Là máy chủ mặc định khi bạn sử dụng Spring WebFlux. Là máy chủ phản ứng (non-blocking).

- **Undertow**: Là một máy chủ có khả năng xử lý các ứng dụng WebFlux.

- **Tomcat**: Có thể cấu hình để hỗ trợ WebFlux nhưng không phải là lựa chọn tốt nhất vì nó dựa trên mô hình blocking I/O.

- **Jetty**: Tương tự như Undertow, Jetty cũng có thể hỗ trợ WebFlux.

### 1.4. Các Đối Tượng Chính
- **Router**: Cấu hình routing (điều hướng) trong WebFlux.

- **Handler**: Xử lý các request (thay vì controller trong Spring MVC).