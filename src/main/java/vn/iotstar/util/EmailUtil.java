package vn.iotstar.util;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Sinh mã OTP ngẫu nhiên gồm 6 chữ số (từ 100000 đến 999999)
     */
    public static String generateOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Gửi email chứa mã OTP kích hoạt tài khoản
     * @param toEmail Email người nhận
     * @param otp Mã OTP gồm 6 chữ số
     * @return true nếu gửi thành công, false nếu có lỗi
     */
    public static boolean sendOtpEmail(String toEmail, String otp) {
        // Luôn in ra console để phục vụ kiểm thử / demo dễ dàng
        System.out.println("=================================================");
        System.out.println(">> [OTP SYSTEM LOG]");
        System.out.println(">> Gửi mã OTP tới Email: " + toEmail);
        System.out.println(">> MÃ OTP XÁC THỰC LÀ: [" + otp + "]");
        System.out.println("=================================================");

        Properties props = new Properties();
        props.put("mail.smtp.host", Constant.Mail.HOST);
        props.put("mail.smtp.port", Constant.Mail.PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constant.Mail.USERNAME, Constant.Mail.PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constant.Mail.USERNAME, Constant.Mail.FROM_NAME, "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Mã Xác Thực Kích Hoạt Tài Khoản - OTP", "UTF-8");

            String htmlContent = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px;\">"
                    + "<div style=\"text-align: center; margin-bottom: 20px;\">"
                    + "<h2 style=\"color: #007bff; margin-bottom: 5px;\">Xác Thực Tài Khoản</h2>"
                    + "<p style=\"color: #666;\">Cảm ơn bạn đã đăng ký tài khoản trên hệ thống của chúng tôi</p>"
                    + "</div>"
                    + "<p>Xin chào,</p>"
                    + "<p>Để hoàn tất quá trình đăng ký và kích hoạt tài khoản của bạn, vui lòng sử dụng mã OTP dưới đây:</p>"
                    + "<div style=\"background-color: #f8f9fa; border: 2px dashed #007bff; border-radius: 8px; padding: 15px; text-align: center; margin: 25px 0;\">"
                    + "<span style=\"font-size: 32px; font-weight: bold; letter-spacing: 8px; color: #d9534f;\">" + otp + "</span>"
                    + "</div>"
                    + "<p style=\"color: #555;\">Mã xác thực có hiệu lực trong vòng <strong>5 phút</strong>. Vui lòng không chia sẻ mã này cho bất kỳ ai.</p>"
                    + "<p style=\"color: #888; font-size: 13px; margin-top: 30px; border-top: 1px solid #eee; padding-top: 15px;\">Nếu bạn không yêu cầu đăng ký tài khoản này, vui lòng bỏ qua email này.</p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println(">> Đã gửi email OTP thành công tới: " + toEmail);
            return true;
        } catch (UnsupportedEncodingException | MessagingException e) {
            System.err.println(">> Gửi email qua SMTP thất bại: " + e.getMessage());
            // Trả về false nhưng mã OTP đã được in ra console và lưu vào database
            return false;
        }
    }

    /**
     * Gửi email chứa mã OTP đặt lại mật khẩu
     * @param toEmail Email người nhận
     * @param otp Mã OTP gồm 6 chữ số
     * @return true nếu gửi thành công, false nếu có lỗi
     */
    public static boolean sendForgotPasswordOtpEmail(String toEmail, String otp) {
        // Luôn in ra console để phục vụ kiểm thử / demo dễ dàng
        System.out.println("=================================================");
        System.out.println(">> [FORGOT PASSWORD OTP LOG]");
        System.out.println(">> Yêu cầu đặt lại mật khẩu cho Email: " + toEmail);
        System.out.println(">> MÃ OTP ĐẶT LẠI MẬT KHẨU LÀ: [" + otp + "]");
        System.out.println("=================================================");

        Properties props = new Properties();
        props.put("mail.smtp.host", Constant.Mail.HOST);
        props.put("mail.smtp.port", Constant.Mail.PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constant.Mail.USERNAME, Constant.Mail.PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constant.Mail.USERNAME, "Hỗ Trợ Tài Khoản", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Mã Xác Thực Đặt Lại Mật Khẩu - OTP", "UTF-8");

            String htmlContent = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px;\">"
                    + "<div style=\"text-align: center; margin-bottom: 20px;\">"
                    + "<h2 style=\"color: #dc3545; margin-bottom: 5px;\">Yêu Cầu Đặt Lại Mật Khẩu</h2>"
                    + "<p style=\"color: #666;\">Bạn vừa gửi yêu cầu đặt lại mật khẩu cho tài khoản của mình</p>"
                    + "</div>"
                    + "<p>Xin chào,</p>"
                    + "<p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu liên kết với địa chỉ email này. Vui lòng sử dụng mã OTP dưới đây để xác nhận:</p>"
                    + "<div style=\"background-color: #fff3cd; border: 2px dashed #ffc107; border-radius: 8px; padding: 15px; text-align: center; margin: 25px 0;\">"
                    + "<span style=\"font-size: 32px; font-weight: bold; letter-spacing: 8px; color: #dc3545;\">" + otp + "</span>"
                    + "</div>"
                    + "<p style=\"color: #555;\">Mã xác thực có hiệu lực trong vòng <strong>5 phút</strong>. <strong>Tuyệt đối không chia sẻ mã này cho bất kỳ ai</strong> để bảo vệ tài khoản của bạn.</p>"
                    + "<p style=\"color: #888; font-size: 13px; margin-top: 30px; border-top: 1px solid #eee; padding-top: 15px;\">Nếu bạn không gửi yêu cầu này, vui lòng bỏ qua email và đảm bảo tài khoản của bạn vẫn an toàn.</p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println(">> Đã gửi email OTP đặt lại mật khẩu thành công tới: " + toEmail);
            return true;
        } catch (UnsupportedEncodingException | MessagingException e) {
            System.err.println(">> Gửi email qua SMTP thất bại: " + e.getMessage());
            return false;
        }
    }
}

