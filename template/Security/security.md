Ref : https://techmaster.vn/posts/36295/spring-security-ban-sau-ve-authentication-va-authorization-p1
# Spring Security

Spring Security thực sự chỉ là một loạt các bộ lọc servlet giúp bạn thêm authentication và authorization vào ứng dụng web của mình.

Nó cũng tích hợp tốt với các framework như Spring Web MVC (hay Spring Boot ), cũng như với các tiêu chuẩn như OAuth2 hoặc SAML. Và nó tự động tạo các trang login / logout và bảo vệ chống lại các hành vi khai thác thông tin như CSRF.

# Security WebApplication

1. Authentication
2. Authorization
3. Filter Servlet

## 1. Authentication
## 2. Authorization
## 3. Filter Servlet

Về cơ bản bất kỳ ứng dụng web Spring nào cũng chỉ là một servlet:
DispatcherServlet cũ của Spring, giúp chuyển hướng các yêu cầu HTTP đến (ví dụ từ trình duyệt) đến @Controllers hoặc @RestControllers

Vấn đề là: Không có mật mã bảo mật nào được mã hóa trong DispatcherServlet đó. Để cho tối ưu, việc authentication và authorization nên được thực hiện trước khi một request truy cập vào @Controllers.

=> có thể đặt bộ lọc lên trước các servlet, tức là có thể viết SecurityFilter và cấu hình nó trong Tomcat (servlet container/ application server)  để lọc mọi request HTTP trước khi nó truy cập vào servlet.

![img.png](images/filter_servlet.png)


