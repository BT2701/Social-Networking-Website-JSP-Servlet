<%@ page import="org.example.j2ee.Model.User, jakarta.servlet.http.HttpSession, jakarta.servlet.http.Cookie" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
  <script type="text/javascript">
      function confirmLogout(event) {
          if (!confirm('Bạn có chắc chắn muốn đăng xuất không?')) {
              event.preventDefault(); // Ngăn chặn hành động mặc định nếu người dùng chọn "Hủy"
          }
      }
  </script>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="search">Hello Servlet</a>
<br><br>
<a href="profile?userId=1">Profile</a>
<a href="login">login</a>

<% 
    // Kiểm tra xem session có tồn tại và có đối tượng "user" không
    if (session != null) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // Hiển thị thông tin người dùng nếu có
            out.println("<br><b>Welcome, " + user.getName() + "!</b>");
            out.println("<br>Email: " + user.getEmail());
            out.println("<br>Phone: " + user.getPhone());
            
            // Thêm nút đăng xuất với hàm JavaScript xác nhận
            out.println("<br><br><a href='logout' onclick='confirmLogout(event)'>Log out</a>");
        } else {
            out.println("<br>No user is logged in.");
        }
    } else {
        out.println("<br>No session found.");
    }

    // Kiểm tra và hiển thị thông tin cookie
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        out.println("<br><b>Cookies:</b>");
        for (Cookie cookie : cookies) {
            out.println("<br>" + cookie.getName() + ": " + cookie.getValue());
        }
    } else {
        out.println("<br>No cookies found.");
    }
%>

</body>
</html>
