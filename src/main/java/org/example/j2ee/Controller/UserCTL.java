package org.example.j2ee.Controller;

import jakarta.mail.MessagingException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.security.SecureRandom;

import org.example.j2ee.Model.User;
import org.example.j2ee.Service.EmailService;
import org.example.j2ee.Service.OtpService;
import org.example.j2ee.Service.UserSV;

public class UserCTL {

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String PHONE_REGEX = "^\\d{10}$";
    private UserSV userSV;
    private EmailService emailService;
    private OtpService otpService;

    public UserCTL() {
        this.userSV = new UserSV(); // Khởi tạo UserSV hoặc lấy từ DI
        this.emailService = new EmailService(); // Khởi tạo dịch vụ gửi email
        this.otpService = new OtpService(); // Khởi tạo OtpService
    }

    public User login(String username, String password) {
        return userSV.login(username, password);
    }

    public boolean register(String name, String username, String password, String birthday, String gender) {
        // Tạo đối tượng User mới với thông tin từ form đăng ký
        User newUser = new User();
        newUser.setName(name); // Set tên đầy đủ từ firstName và surName
        newUser.setPassword(password); // Set password

        // Kiểm tra xem username là email hay số điện thoại
        if (username.matches(EMAIL_REGEX)) {
            newUser.setEmail(username); // Nếu là email
            newUser.setPhone(null); // Đặt số điện thoại thành null
        } else if (username.matches(PHONE_REGEX)) {
            newUser.setPhone(username); // Nếu là số điện thoại
            newUser.setEmail(null); // Đặt email thành null
        } else {
            // Nếu không phải email hay số điện thoại hợp lệ, trả về false hoặc xử lý lỗi
            return false;
        }

        // Chuyển đổi chuỗi ngày sinh thành đối tượng LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate birthDate = LocalDate.parse(birthday, formatter);
            newUser.setBirth(java.sql.Date.valueOf(birthDate)); // Chuyển đổi LocalDate thành java.sql.Date
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi phân tích ngày sinh nếu cần
            return false;
        }

        newUser.setGender(gender); // Set gender

        // Đặt giá trị cho các trường còn lại
        newUser.setLastActive(new Timestamp(System.currentTimeMillis())); // Đặt thời gian hiện tại cho lastActive
        newUser.setIsOnline(1); // Đặt trạng thái online mặc định là 1

        // Gọi phương thức register từ lớp UserSV để thực hiện đăng ký
        boolean success = userSV.register(newUser);

        // Nếu đăng ký thành công và email không phải null, gửi email thông báo
        if (success && newUser.getEmail() != null) {
            emailService.sendEmail(newUser.getEmail(), "Đăng ký thành công",
                    "Chào " + newUser.getName() + ",\n\nBạn đã đăng ký thành công trên trang web của chúng tôi!"); // Xử lý lỗi gửi email nếu cần
        }

        return success;
    }

    public boolean isUsernameExists(String username) {
        return userSV.isUsernameExists(username);
    }

    public User findAccountByUsername(String username) {
        return userSV.findAccountByUsername(username);
    }

    // Phương thức gửi OTP qua email
    public void sendOTP(String email) {
        // Tạo mã OTP
        String otp = otpService.generateRandomOtp();

        // Lưu mã OTP vào bộ nhớ tạm thời
        otpService.saveOtpToCache(email, otp);

        // Gửi mã OTP qua email
        emailService.sendEmail(email, "Your OTP Code", "Your OTP code is: " + otp);
    }

    // Phương thức xác thực OTP
    public boolean verifyOtp(String email, String otp, String userId) {
        if (otpService.verifyOtp(email, otp)) {
            String newPassword = generateRandomPassword(10); // Tạo mật khẩu mới dài 10 ký tự
            if (userSV.updatePassword(userId, newPassword)) {
                emailService.sendEmail(email, "Reset Password", "Hi,New Password is: " + newPassword);
                return true;
            }
        }
        return false;
    }

    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

}
