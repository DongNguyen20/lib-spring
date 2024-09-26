#Logging

_**Mục đích**: theo dõi hoạt động của ứng dụng, phát hiện lỗi và ghi lại thông tin hữu ích cho việc bảo trì._

_**Một số thư viện:**_ `Java Util Logging(JUL)`, `Log4j`, `Logback`, `SLF4J`


###I. Cấu hình logging: 
####1. Sử dụng `application.properties`
```properties
logging.level.root=INFO
logging.level.com.example=DEBUG
logging.file.name=logs/app.log
```
####2. Sử dụng `logback-spring.xml`
```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Appender để ghi log vào file với log rotation -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- Đường dẫn đến file log chính -->
        <file>restful-spring/logs/application.log</file>

        <!-- Chính sách quay vòng log dựa trên kích thước -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- Định dạng tên file log quay vòng -->
            <fileNamePattern>logs/application.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxFileSize>1MB</maxFileSize> <!-- Quay vòng khi file log vượt quá 1MB -->
            <maxHistory>30</maxHistory> <!-- Giữ lại log trong 30 ngày -->
            <totalSizeCap>10GB</totalSizeCap> <!-- Giới hạn tổng dung lượng log là 10GB -->
        </rollingPolicy>

        <encoder>
            <!-- Định dạng log -->
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Thiết lập root logger để ghi log -->
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>
</configuration>
```
####3. Sử dụng Logging trong ứng dụng 

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    public void performAction() {
        logger.info("Action performed");
        try {
            // Code that may throw an exception
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
        }
    }
}
```
####4. Mức độ Log
`level: ERROR > WARN > INFO > DEBUG > TRACE`
   - **ERROR** : Mức cao nhất, chỉ nên sử dụng khi thực sự có lỗi nghiêm trọng.
   - **WARN**  : Dùng để cảnh báo về vấn đề có thể xảy ra nhưng không ảnh hưởng ngay lập tức đến hoạt động.
   - **INFO**  : Thông báo về những sự kiện quan trọng trong ứng dụng.
   - **DEBUG**  : Thích hợp cho việc phát triển và gỡ lỗi, không nên sử dụng trong môi trường sản xuất trừ khi cần thiết.
   - **TRACE**  : Thông tin chi tiết nhất, thường chỉ dùng khi cần theo dõi chính xác luồng thực thi của mã.

