# Java Send Mail

### 1. Dependencies

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 2. application.properties
- Tài khoản bật Xác minh 2 bước;
- vào https://myaccount.google.com/apppasswords tạo app-password
```properties
#config mail-server
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${GMAIL:email@gmail.com}
spring.mail.password=${GMAIL_PASS:app-password}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000
```

### 3. Template 
```html
<html>
<body>
<h3>Xin chào, [[${name}]]!</h3>
<p>Chào mừng bạn đến với dịch vụ của chúng tôi.</p>
</body>
</html>
```
### 4. Services

```java
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    public void sendThymeleafEmail(String toEmail, String subject, String name) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        Context context = new Context();
        context.setVariable("name", name);
        String htmlBody = templateEngine.process("emailTemplate", context);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // `true` để cho phép HTML
        helper.setFrom("your-email@gmail.com");

        mailSender.send(mimeMessage);
        System.out.println("Thymeleaf email sent successfully.");
    }
}
```