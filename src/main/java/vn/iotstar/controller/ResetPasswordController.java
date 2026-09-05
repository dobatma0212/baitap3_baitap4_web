package vn.iotstar.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;
import vn.iotstar.util.EmailUtil;

@WebServlet(urlPatterns = {"/reset-password", "/resend-forgot-otp"})
public class ResetPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/resend-forgot-otp".equals(path)) {
            handleResendForgotOtp(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        String email = req.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            if (session != null && session.getAttribute("reset_email") != null) {
                email = (String) session.getAttribute("reset_email");
            }
        }

        if (session != null) {
            String successMsg = (String) session.getAttribute("success_msg");
            if (successMsg != null) {
                req.setAttribute("success_msg", successMsg);
                session.removeAttribute("success_msg");
            }
            String alertMsg = (String) session.getAttribute("alert");
            if (alertMsg != null) {
                req.setAttribute("alert", alertMsg);
                session.removeAttribute("alert");
            }
        }

        req.setAttribute("email", email);
        req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();
        if ("/resend-forgot-otp".equals(path)) {
            handleResendForgotOtp(req, resp);
            return;
        }

        String email = req.getParameter("email");
        String otp = req.getParameter("otp");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (email != null) email = email.trim();
        if (otp != null) otp = otp.trim();
        if (newPassword != null) newPassword = newPassword.trim();
        if (confirmPassword != null) confirmPassword = confirmPassword.trim();

        req.setAttribute("email", email);

        if (email == null || email.isEmpty() || otp == null || otp.isEmpty()
                || newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            req.setAttribute("alert", "Vui lòng điền đầy đủ tất cả các thông tin!");
            req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
            return;
        }

        if (!vn.iotstar.util.ValidationUtil.isValidEmail(email)) {
            req.setAttribute("alert", "Địa chỉ email không đúng định dạng!");
            req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
            return;
        }

        if (!vn.iotstar.util.ValidationUtil.isValidOtp(otp)) {
            req.setAttribute("alert", "Mã OTP không hợp lệ! Mã OTP gồm đúng 6 chữ số.");
            req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("alert", "Mật khẩu xác nhận không khớp với mật khẩu mới!");
            req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
            return;
        }

        if (newPassword.length() < 6) {
            req.setAttribute("alert", "Mật khẩu mới phải có độ dài từ 6 ký tự trở lên!");
            req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
            return;
        }

        boolean isReset = userService.resetPassword(email, otp, newPassword);
        if (isReset) {
            HttpSession session = req.getSession(true);
            session.removeAttribute("reset_email");
            session.setAttribute("alertSuccess", "Đặt lại mật khẩu thành công! Bạn có thể đăng nhập bằng mật khẩu mới.");
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("alert", "Mã OTP không chính xác hoặc đã hết hạn. Vui lòng thử lại hoặc nhấn 'Gửi lại mã OTP'!");
            req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
        }
    }

    private void handleResendForgotOtp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        HttpSession session = req.getSession(true);

        if (email == null || email.trim().isEmpty()) {
            if (session.getAttribute("reset_email") != null) {
                email = (String) session.getAttribute("reset_email");
            }
        }

        if (email != null) {
            email = email.trim();
        }

        if (email == null || email.isEmpty()) {
            session.setAttribute("alert", "Không tìm thấy thông tin email để gửi lại mã OTP!");
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            session.setAttribute("alert", "Email không tồn tại trên hệ thống!");
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }

        // Sinh OTP mới và cập nhật
        String newOtp = EmailUtil.generateOtp();
        boolean resendSuccess = userService.sendForgotPasswordOtp(email, newOtp);

        if (resendSuccess) {
            EmailUtil.sendForgotPasswordOtpEmail(email, newOtp);
            session.setAttribute("success_msg", "Mã OTP mới đã được gửi đến email " + email + "!");
        } else {
            session.setAttribute("alert", "Lỗi khi gửi lại mã OTP. Vui lòng thử lại sau!");
        }

        resp.sendRedirect(req.getContextPath() + "/reset-password?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8));
    }
}

