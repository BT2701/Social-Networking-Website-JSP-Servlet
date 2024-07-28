package org.example.j2ee.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class OtpService {

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_SECONDS = 300; // Thời gian hết hạn OTP (5 phút)

    private final ConcurrentHashMap<String, OtpEntry> otpCache;

    public OtpService() {
        this.otpCache = new ConcurrentHashMap<>();
    }

    // Phương thức tạo mã OTP ngẫu nhiên
    public String generateRandomOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Chọn số ngẫu nhiên từ 0 đến 9
        }
        return otp.toString();
    }

    // Phương thức lưu OTP vào bộ nhớ cache
    public void saveOtpToCache(String email, String otp) {
        long expiryTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(OTP_EXPIRY_SECONDS);
        otpCache.put(email, new OtpEntry(otp, expiryTime));
        System.out.println("Saved OTP: " + otp + " for email: " + email); // Log thông tin OTP đã lưu

    }

    // Phương thức kiểm tra OTP từ bộ nhớ cache
    public boolean verifyOtp(String email, String otp) {
        OtpEntry entry = otpCache.get(email);
        if (entry == null || System.currentTimeMillis() > entry.expiryTime) {
            return false; // OTP không tồn tại hoặc đã hết hạn
        }
        return entry.otp.equals(otp);
    }

    // Phương thức để lấy giá trị cache OTP (chỉ dùng cho mục đích kiểm tra)
    public ConcurrentHashMap<String, OtpEntry> getOtpCache() {
        return otpCache;
    }

    // Lớp nội bộ để lưu OTP và thời gian hết hạn
    private static class OtpEntry {

        final String otp;
        final long expiryTime;

        OtpEntry(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
}
