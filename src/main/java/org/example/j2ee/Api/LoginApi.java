package org.example.j2ee.Api;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.SneakyThrows;

import org.example.j2ee.Controller.UserCTL;
import org.example.j2ee.Model.User;

@WebServlet(name = "login", value = "/login")
public class LoginApi extends HttpServlet {

    private UserCTL userCTL;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userCTL = new UserCTL();
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                } else if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }

            if (username != null && password != null) {
                User user = userCTL.login(username, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect("testcookie.jsp");
                    return;
                }
            }
        }

        request.getRequestDispatcher("/Template/Login/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userCTL.login(username, password);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Kiểm tra và lưu thông tin đăng nhập vào cookie nếu người dùng chọn lưu
            if (request.getParameter("rememberMe") != null && request.getParameter("rememberMe").equals("on")) {
                Cookie usernameCookie = new Cookie("username", username);
                Cookie passwordCookie = new Cookie("password", password);
                usernameCookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
                passwordCookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
                usernameCookie.setPath("/");
                passwordCookie.setPath("/");
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            } else {
                // Xóa cookie nếu không chọn lưu
                Cookie usernameCookie = new Cookie("username", "");
                Cookie passwordCookie = new Cookie("password", "");
                usernameCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);
                usernameCookie.setPath("/");
                passwordCookie.setPath("/");
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            }

            out.print("{\"success\": true}");
        } else {
            out.print("{\"success\": false, \"message\": \"Tên đăng nhập hoặc mật khẩu không đúng!\"}");
        }

        out.flush();
    }

    public void destroy() {
    }
}
