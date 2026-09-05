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

@WebServlet(urlPatterns = {"/verify-otp", "/resend-otp"})
public class VerifyOtpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/resend-otp".equals(path)) {
            handleResendOtp(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        String email = req.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            if (session != null && session.getAttribute("verify_email") != null) {
                email = (String) session.getAttribute("verify_email");
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
        req.getRequestDispatcher(Constant.Path.VERIFY_OTP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();
        if ("/resend-otp".equals(path)) {
            handleResendOtp(req, resp);
            return;
        }

        String email = req.getParameter("email");
        String otp = req.getParameter("otp");

        if (email != null) {
            email = email.trim();
        }
        if (otp != null) {
            otp = otp.trim();
        }

        if (email == null || email.isEmpty() || otp == null || otp.isEmpty()) {
            req.setAttribute("alert", "Vui lòng nhập đầy đủ email và mã OTP!");
            req.setAttribute("email", email);
            req.getRequestDispatcher(Constant.Path.VERIFY_OTP).forward(req, resp);
            return;
        }

        if (!vn.iotstar.util.ValidationUtil.isValidOtp(otp)) {
            req.setAttribute("alert", "Mã OTP không hợp lệ! Mã OTP gồm đúng 6 chữ số.");
            req.setAttribute("email", email);
            req.getRequestDispatcher(Constant.Path.VERIFY_OTP).forward(req, resp);
            return;
        }

        boolean isVerified = userService.verifyOtp(email, otp);
        if (isVerified) {
            HttpSession session = req.getSession(true);
            session.removeAttribute("verify_email");
            session.setAttribute("alertSuccess", "Kích hoạt tài khoản thành công! Bạn có thể đăng nhập ngay bây giờ.");
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("alert", "Mã OTP không chính xác. Vui lòng kiểm tra lại hoặc nhấn 'Gửi lại mã OTP'!");
            req.setAttribute("email", email);
            req.getRequestDispatcher(Constant.Path.VERIFY_OTP).forward(req, resp);
        }
    }

    private void handleResendOtp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        HttpSession session = req.getSession(true);

        if (email == null || email.trim().isEmpty()) {
            if (session.getAttribute("verify_email") != null) {
                email = (String) session.getAttribute("verify_email");
            }
        }

        if (email != null) {
            email = email.trim();
        }

        if (email == null || email.isEmpty()) {
            session.setAttribute("alert", "Không tìm thấy thông tin email để gửi lại mã OTP!");
            resp.sendRedirect(req.getContextPath() + "/verify-otp");
            return;
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            session.setAttribute("alert", "Email không tồn tại trên hệ thống!");
            resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8));
            return;
        }

        if (user.getStatus() == 1) {
            session.setAttribute("alertSuccess", "Tài khoản của bạn đã được kích hoạt từ trước! Vui lòng đăng nhập.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Sinh OTP mới và gửi lại
        String newOtp = EmailUtil.generateOtp();
        boolean resendSuccess = userService.resendOtp(email, newOtp);

        if (resendSuccess) {
            EmailUtil.sendOtpEmail(email, newOtp);
            session.setAttribute("success_msg", "Mã OTP mới đã được gửi đến email " + email + "!");
        } else {
            session.setAttribute("alert", "Lỗi khi tạo mã OTP mới. Vui lòng thử lại sau!");
        }

        resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8));
    }
}

