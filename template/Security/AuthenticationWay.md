# Authentication Ways

### 1. Username/Password

### 2. JWT

### 3. OAuth (Open Authorization)
[OAuth](OAuth/ReadMe.md)

### 4. LDAP Authentication

- Dùng để xác thực người dùng từ hệ thống LDAP (Lightweight Directory Access Protocol).
- Thường sử dụng trong các hệ thống doanh nghiệp có cơ sở dữ liệu LDAP, chẳng hạn như Microsoft Active Directory.
- Cấu hình với http.ldapAuthentication() trong HttpSecurity.
```java
http.ldapAuthentication()
    .userDnPatterns("uid={0},ou=people") // Cấu hình pattern cho LDAP user DN
    .contextSource()
    .url("ldap://localhost:8389/dc=springframework,dc=org");

```
### 5. SAML(Security Assertion Markup Language) Authentication
- Được sử dụng trong các hệ thống lớn, cho phép xác thực giữa các hệ thống của các tổ chức khác nhau.
- Được dùng nhiều trong doanh nghiệp, trường học, hoặc hệ thống liên kết các tài khoản từ các hệ thống độc lập.
- Spring Security hỗ trợ SAML thông qua spring-security-saml

### 6. OTP(One-Time Password)

### 7. Pre-Authentication

### 8. Remember Me

- Cho phép người dùng duy trì phiên đăng nhập qua nhiều lần truy cập, ngay cả khi họ đóng và mở lại trình duyệt.
- Thông tin đăng nhập của người dùng sẽ được lưu trong cookie dưới dạng mã hóa. Phương pháp này hữu ích cho các ứng dụng cần ghi nhớ đăng nhập người dùng, nhưng có thể rủi ro nếu không được bảo mật đúng cách.
- Cấu hình rememberMe() trong HttpSecurity.
```java
http.rememberMe()
    .tokenValiditySeconds(1209600) // Thời gian cookie remember-me tồn tại (tính bằng giây)
    .key("uniqueAndSecret"); // Mã hóa cookie để đảm bảo bảo mật
```
### 9.API Key

- API Key thường được sử dụng để xác thực trong các ứng dụng RESTful hoặc dịch vụ không cần đăng nhập qua giao diện.
- API Key thường được gửi trong request header hoặc như một tham số URL.

---
[↩ 🏠︎ Home : ̗̀➛](../../List%20Contents.md)
