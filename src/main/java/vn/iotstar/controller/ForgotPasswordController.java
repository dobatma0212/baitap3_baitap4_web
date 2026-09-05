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

@WebServlet(urlPatterns = "/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String alertMsg = (String) session.getAttribute("alert");
            if (alertMsg != null) {
                req.setAttribute("alert", alertMsg);
                session.removeAttribute("alert");
            }
            String successMsg = (String) session.getAttribute("success_msg");
            if (successMsg != null) {
                req.setAttribute("success_msg", successMsg);
                session.removeAttribute("success_msg");
            }
        }

        String email = req.getParameter("email");
        if (email != null && !email.trim().isEmpty()) {
            req.setAttribute("email", email.trim());
        }

        req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        if (email != null) {
            email = email.trim();
        }

        if (email == null || email.isEmpty()) {
            req.setAttribute("alert", "Vui lòng nhập địa chỉ email của bạn!");
            req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
            return;
        }

        if (!vn.iotstar.util.ValidationUtil.isValidEmail(email)) {
            req.setAttribute("alert", "Địa chỉ email không đúng định dạng!");
            req.setAttribute("email", email);
            req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
            return;
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            req.setAttribute("alert", "Địa chỉ email không tồn tại trên hệ thống!");
            req.setAttribute("email", email);
            req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
            return;
        }

        // 1. Sinh mã OTP 6 chữ số
        String otp = EmailUtil.generateOtp();

        // 2. Lưu mã OTP vào CSDL
        boolean isSaved = userService.sendForgotPasswordOtp(email, otp);
        if (isSaved) {
            // 3. Gửi email chứa mã OTP đến người dùng
            EmailUtil.sendForgotPasswordOtpEmail(email, otp);

            // 4. Lưu email và thông báo vào session
            HttpSession session = req.getSession(true);
            session.setAttribute("reset_email", email);
            session.setAttribute("success_msg", "Mã xác thực OTP đã được gửi đến email " + email + ". Vui lòng kiểm tra hộp thư!");

            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            resp.sendRedirect(req.getContextPath() + "/reset-password?email=" + encodedEmail);
        } else {
            req.setAttribute("alert", "Có lỗi xảy ra khi tạo mã OTP. Vui lòng thử lại sau!");
            req.setAttribute("email", email);
            req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
        }
    }
}

