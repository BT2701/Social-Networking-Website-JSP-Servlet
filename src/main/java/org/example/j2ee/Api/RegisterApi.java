package org.example.j2ee.Api;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.google.gson.Gson;
import org.example.j2ee.Controller.UserCTL;

@WebServlet(name = "register", value = "/register")
public class RegisterApi extends HttpServlet {

    private UserCTL userCTL;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userCTL = new UserCTL();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy thông tin từ request
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        RegistrationRequest regRequest = gson.fromJson(reader, RegistrationRequest.class);

        String name = regRequest.name;
        String username = regRequest.username;
        String password = regRequest.password;
        String birthDate = regRequest.birthDate;
        String gender = regRequest.gender;

        boolean success = false;
        String message = "Đăng ký không thành công!";

        try {
            // Kiểm tra sự tồn tại của email hoặc số điện thoại
            if (userCTL.isUsernameExists(username)) {
                message = "Email hoặc số điện thoại đã tồn tại!";
            } else {
                // Thực hiện các bước kiểm tra và lưu vào cơ sở dữ liệu
                success = userCTL.register(name, username, password, birthDate, gender);

                if (success) {
                    message = "Đăng ký thành công!";
                } else {
                    message = "Đăng ký không thành công!";
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để kiểm tra
            message = "Đã xảy ra lỗi khi đăng ký!";
        }

        // Thiết lập kiểu nội dung phản hồi là JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
        out.flush();
    }

    private class RegistrationRequest {
        String name;
        String username;
        String password;
        String birthDate;
        String gender;
    }

    public void destroy() {
    }
}
