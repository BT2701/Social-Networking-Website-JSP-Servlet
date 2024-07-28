package org.example.j2ee.Service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {
    private final String username = "web2sgu@gmail.com";
    private final String password = "ymqd khmh ibvs jjyt"; // SGU SOCIAL 

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }

    public boolean sendEmail(String to, String subject, String body) {
        // Thiết lập phiên làm việc với thông tin đăng nhập
        Session session = Session.getInstance(getMailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo đối tượng Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // Gửi email
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            // Ghi lại lỗi hoặc xử lý lỗi nếu cần
            e.printStackTrace();
            return false;
        }
    }
}
