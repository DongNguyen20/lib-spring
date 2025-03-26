# 🚀 Xử lý lỗi trong gRPC (Error Handling)

Trong gRPC, lỗi không được trả về như các exception thông thường mà sử dụng **status codes** (tương tự HTTP). Mỗi lỗi đều có một **status code** và một **chi tiết lỗi** (message).

---

## 1️⃣ Các loại lỗi trong gRPC
gRPC sử dụng các **status codes** để xác định lỗi, có thể chia thành 3 nhóm:

| **Code** | **Ý nghĩa** | **Khi nào xảy ra?** |
|----------|------------|----------------------|
| ✅ `OK (0)` | Thành công | Khi không có lỗi |
| ❌ `INVALID_ARGUMENT (3)` | Tham số không hợp lệ | Nhập sai giá trị, thiếu dữ liệu |
| ❌ `NOT_FOUND (5)` | Không tìm thấy tài nguyên | ID không tồn tại trong database |
| ❌ `ALREADY_EXISTS (6)` | Đã tồn tại | Dữ liệu bị trùng |
| ❌ `PERMISSION_DENIED (7)` | Không có quyền | Người dùng không có quyền truy cập |
| ❌ `UNAUTHENTICATED (16)` | Chưa xác thực | Token không hợp lệ |
| ❌ `RESOURCE_EXHAUSTED (8)` | Hết tài nguyên | Quá tải, giới hạn API bị vượt quá |
| ❌ `FAILED_PRECONDITION (9)` | Điều kiện chưa đúng | Giao dịch không hợp lệ, trạng thái không đúng |
| ❌ `ABORTED (10)` | Giao dịch bị huỷ | Xung đột dữ liệu, retry |
| ❌ `UNIMPLEMENTED (12)` | API chưa được triển khai | Server chưa implement phương thức |
| ❌ `INTERNAL (13)` | Lỗi server | Lỗi không mong đợi, exception bên trong server |
| ❌ `UNAVAILABLE (14)` | Server không phản hồi | Server bị down, mất kết nối |
| ❌ `DEADLINE_EXCEEDED (4)` | Timeout | Request chạy quá lâu |

---

## 2️⃣ Cách xử lý lỗi trong Server (Java)
Khi một lỗi xảy ra trong server, ta cần **throw lỗi với `StatusRuntimeException` hoặc `StatusException`**.

### 📌 Ví dụ Server xử lý lỗi
```java
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {
    @Override
    public void getOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        String orderId = request.getOrderId();

        // Giả sử order không tồn tại
        if (orderId.isEmpty()) {
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                      .withDescription("Order ID không được để trống")
                      .asRuntimeException()
            );
            return;
        }

        if (!orderExists(orderId)) {
            responseObserver.onError(
                Status.NOT_FOUND
                      .withDescription("Không tìm thấy đơn hàng với ID: " + orderId)
                      .asRuntimeException()
            );
            return;
        }

        // Nếu không có lỗi, trả về kết quả bình thường
        OrderResponse response = OrderResponse.newBuilder()
                .setOrderId(orderId)
                .setStatus("Đang xử lý")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private boolean orderExists(String orderId) {
        return orderId.equals("12345"); // Giả sử chỉ có ID 12345 tồn tại
    }
}
```

## 3️⃣ Cách xử lý lỗi trong Client (Java)
Ở phía client, ta phải bắt lỗi từ server bằng cách xử lý `StatusRuntimeException`.

```java
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class OrderClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        OrderServiceGrpc.OrderServiceBlockingStub stub = OrderServiceGrpc.newBlockingStub(channel);

        try {
            OrderRequest request = OrderRequest.newBuilder().setOrderId("9999").build();
            OrderResponse response = stub.getOrder(request);
            System.out.println("Order Status: " + response.getStatus());
        } catch (StatusRuntimeException e) {
            System.err.println("Lỗi gRPC: " + e.getStatus().getCode());
            System.err.println("Mô tả: " + e.getStatus().getDescription());
        }

        channel.shutdown();
    }
}
```

## 4️⃣ Timeout và Retry
Nếu server quá tải hoặc request quá lâu, ta cần đặt timeout và retry khi cần.

📌 Ví dụ Client đặt Timeout

```java
OrderServiceGrpc.OrderServiceBlockingStub stub = 
    OrderServiceGrpc.newBlockingStub(channel)
    .withDeadlineAfter(5, TimeUnit.SECONDS); // Timeout sau 5 giây

try {
    OrderRequest request = OrderRequest.newBuilder().setOrderId("12345").build();
    OrderResponse response = stub.getOrder(request);
    System.out.println("Order Status: " + response.getStatus());
} catch (StatusRuntimeException e) {
    if (e.getStatus().getCode() == Status.Code.DEADLINE_EXCEEDED) {
        System.err.println("Request bị timeout!");
    } else {
        System.err.println("Lỗi gRPC: " + e.getStatus().getCode());
    }
}
```

## 5️⃣ Bắt lỗi trong StreamObserver
Khi dùng streaming gRPC (StreamObserver), ta phải xử lý lỗi trong onError().

📌 Ví dụ Server Streaming
```java
@Override
public void streamOrders(OrderStreamRequest request, StreamObserver<OrderResponse> responseObserver) {
    try {
        for (int i = 1; i <= 5; i++) {
            if (i == 3) {
                throw new RuntimeException("Lỗi giả lập tại order thứ 3");
            }
            OrderResponse response = OrderResponse.newBuilder()
                    .setOrderId("Order" + i)
                    .setStatus("Đang xử lý")
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    } catch (Exception e) {
        responseObserver.onError(Status.INTERNAL.withDescription("Lỗi hệ thống").asRuntimeException());
    }
}
```