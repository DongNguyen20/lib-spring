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

```java
import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityServletFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        UsernamePasswordToken token = extractUsernameAndPasswordFrom(request);  // (1)

        if (notAuthenticated(token)) {  // (2)
            // either no or wrong username/password
            // unfortunately the HTTP status code is called "unauthorized", instead of "unauthenticated"
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401.
            return;
        }

        if (notAuthorized(token, request)) { // (3)
            // you are logged in, but don<t have the proper rights
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // HTTP 403
            return;
        }

        // allow the HttpRequest to go to Spring<s DispatcherServlet
        // and @RestControllers/@Controllers.
        chain.doFilter(request, response); // (4)
    }

    private UsernamePasswordToken extractUsernameAndPasswordFrom(HttpServletRequest request) {
        // Either try and read in a Basic Auth HTTP Header, which comes in the form of user:password
        // Or try and find form login request parameters or POST bodies, i.e. "username=me" & "password="myPass"
        return checkVariousLoginOptions(request);
    }

    private boolean notAuthenticated(UsernamePasswordToken token) {
        // compare the token with what you have in your database...or in-memory...or in LDAP...
        return false;
    }

    private boolean notAuthorized(UsernamePasswordToken token, HttpServletRequest request) {
       // check if currently authenticated user has the permission/role to access this request<s /URI
       // e.g. /admin needs a ROLE_ADMIN , /callcenter needs ROLE_CALLCENTER, etc.
       return false;
    }
}
```

1. Đầu tiên, bộ lọc cần extract username/password từ request. Nó có thể thông qua Basic Auth Http Header, hoặc các field trong form, hoặc cookie, v.v.
2. Sau đó, bộ lọc cần xác thực bằng cách đối chiếu tổ hợp username/password đó với một thứ gì đó , chẳng hạn như database.
3. Sau khi authenticate thành công, bộ lọc cần kiểm tra user có được phép truy cập requested URI hay không.
4. Nếu request vẫn tồn tại sau tất cả các lần kiểm tra này, thì bộ lọc có thể cho phép request chuyển đến DispatcherServlet của bạn, tức là @Controllers.

### FilterChains
Xác minh trong thực tế: Trong khi code trên có thể hoạt động khi biên dịch, nó sớm hay muộn cũng dẫn đến một bộ lọc chất đầy hàng tấn code cho các cơ chế authentication và authorization khác nhau.

Trong thế giới thực, ta sẽ chia bộ lọc này thành nhiều bộ lọc, sau đó ta liên ket với nhau.

Ví dụ: một request HTTP sẽ…

1. Đầu tiên, đi qua LoginMethodFilter…

2. Sau đó, đi qua AuthenticationFilter…

3. Sau đó, chuyển qua AuthorizationFilter…

4. Cuối cùng, chạm vào servlet.

Khái niệm này được gọi là FilterChain

### FilterChain & Security Configuration DSL
