package org.example.j2ee.Api;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.google.gson.Gson;
import org.example.j2ee.Controller.UserCTL;
import org.example.j2ee.Model.User;

@WebServlet(name = "forgotPassword", value = "/forgotPassword")
public class ForgotPasswordApi extends HttpServlet {

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
        ForgotPasswordRequest regRequest = gson.fromJson(reader, ForgotPasswordRequest.class);

        String action = regRequest.action;
        String username = regRequest.username;

        boolean success = false;
        String message = "";
        User user = null;

        try {
            switch (action) {
                case "findAccountByUsername":
                    user = userCTL.findAccountByUsername(username);
                    if (user != null) {
                        success = true;
                        message = "Tìm tài khoản thành công!";
                    } else {
                        message = "Tài khoản không tồn tại!";
                    }
                    response.setContentType("application/json");
                    PrintWriter out1 = response.getWriter();
                    out1.print("{\"success\": " + success
                            + ", \"id\": \"" + (user != null ? user.getId() : "")
                            + "\", \"name\": \"" + (user != null ? user.getName() : "")
                            + "\", \"avt\": \"" + (user != null ? user.getAvt() : "")
                            + "\", \"email\": \"" + (user != null ? user.getEmail() : "")
                            + "\", \"message\": \"" + message + "\"}");
                    out1.flush();
                    break;

                case "sendOtp":
                    String email = regRequest.email;
                    user = userCTL.findAccountByUsername(username);
                    userCTL.sendOTP(email);
                    success = true;
                    message = "Gửi OTP thành công!";
                    response.setContentType("application/json");
                    PrintWriter out2 = response.getWriter();
                    out2.print("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
                    out2.flush();
                    break;
                case "ConfirmOtp":
                    String email1 = regRequest.email;
                    String otp = regRequest.otp;
                    String id = regRequest.id;

                    // Xác thực OTP
                    boolean isOtpValid = userCTL.verifyOtp(email1, otp,id);
                    if (isOtpValid) {
                        success = true;
                        message = "OTP hợp lệ!";
                    } else {
                        message = "Mã OTP không hợp lệ!";
                    }

                    response.setContentType("application/json");
                    PrintWriter out3 = response.getWriter();
                    out3.print("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
                    out3.flush();
                    break;

                default:
                    message = "Hành động không hợp lệ!";
                    response.setContentType("application/json");
                    PrintWriter out4 = response.getWriter();
                    out4.print("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
                    out4.flush();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để kiểm tra
            message = "Đã xảy ra lỗi!";
            response.setContentType("application/json");
            PrintWriter out5 = response.getWriter();
            out5.print("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
            out5.flush();
        }
    }

    private class ForgotPasswordRequest {

        String action;
        String username;
        String newPassword;
        String email;
        String otp;
        String id;

    }

    public void destroy() {
    }
}
