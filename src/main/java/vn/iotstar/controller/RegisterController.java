package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;
import vn.iotstar.util.EmailUtil;

@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/admin");
            return;
        }
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    session = req.getSession(true);
                    session.setAttribute("username", cookie.getValue());
                    resp.sendRedirect(req.getContextPath() + "/admin");
                    return;
                }
            }
        }
        req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");

        UserService service = new UserServiceImpl();
        String alertMsg = "";

        if (email == null || email.trim().isEmpty() || username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            alertMsg = "Vui lòng nhập đầy đủ các trường bắt buộc!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        if (service.checkExistEmail(email.trim())) {
            alertMsg = "Email đã tồn tại!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        if (service.checkExistUsername(username.trim())) {
            alertMsg = "Tài khoản đã tồn tại!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        // 1. Sinh mã OTP ngẫu nhiên 6 chữ số
        String otp = EmailUtil.generateOtp();

        // 2. Lưu tài khoản ở trạng thái chờ kích hoạt (status = 0, code = otp)
        boolean isSuccess = service.register(username.trim(), password.trim(), email.trim(), fullname, phone, otp);

        if (isSuccess) {
            // 3. Gửi email chứa mã OTP đến người dùng
            EmailUtil.sendOtpEmail(email.trim(), otp);

            // 4. Lưu email vào session và chuyển hướng tới màn hình xác thực OTP
            HttpSession session = req.getSession(true);
            session.setAttribute("verify_email", email.trim());
            session.setAttribute("success_msg", "Mã OTP kích hoạt tài khoản đã được gửi đến email " + email.trim() + ". Vui lòng kiểm tra hộp thư!");

            String encodedEmail = URLEncoder.encode(email.trim(), StandardCharsets.UTF_8);
            resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + encodedEmail);
        } else {
            alertMsg = "Đăng ký không thành công, vui lòng thử lại!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
        }
    }
}

