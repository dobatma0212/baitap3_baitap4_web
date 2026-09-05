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
        String confirmPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");

        // Giữ lại giá trị người dùng đã nhập
        req.setAttribute("username", username);
        req.setAttribute("email", email);
        req.setAttribute("fullname", fullname);
        req.setAttribute("phone", phone);

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || fullname == null || fullname.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()) {
            req.setAttribute("alert", "Vui lòng nhập đầy đủ tất cả các trường thông tin!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        username = username.trim();
        email = email.trim();
        password = password.trim();
        fullname = fullname.trim();
        phone = phone.trim();

        if (!vn.iotstar.util.ValidationUtil.isValidUsername(username)) {
            req.setAttribute("alert", "Tên đăng nhập từ 3 đến 50 ký tự, chỉ gồm chữ cái, chữ số và dấu gạch dưới!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        if (!vn.iotstar.util.ValidationUtil.isValidEmail(email)) {
            req.setAttribute("alert", "Địa chỉ email không đúng định dạng!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        if (!vn.iotstar.util.ValidationUtil.isValidPhone(phone)) {
            req.setAttribute("alert", "Số điện thoại không hợp lệ (phải gồm 10 chữ số, bắt đầu bằng 03, 05, 07, 08, 09)!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        if (password.length() < 6) {
            req.setAttribute("alert", "Mật khẩu phải có độ dài tối thiểu 6 ký tự!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        if (confirmPassword != null && !password.equals(confirmPassword.trim())) {
            req.setAttribute("alert", "Mật khẩu xác nhận không khớp với mật khẩu!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        UserService service = new UserServiceImpl();

        if (service.checkExistUsername(username)) {
            req.setAttribute("alert", "Tên tài khoản này đã tồn tại! Vui lòng chọn tên khác.");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        if (service.checkExistEmail(email)) {
            req.setAttribute("alert", "Địa chỉ email này đã được sử dụng!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }

        // 1. Sinh mã OTP ngẫu nhiên 6 chữ số
        String otp = EmailUtil.generateOtp();

        // 2. Lưu tài khoản ở trạng thái chờ kích hoạt (status = 0, code = otp)
        boolean isSuccess = service.register(username, password, email, fullname, phone, otp);

        if (isSuccess) {
            // 3. Gửi email chứa mã OTP đến người dùng
            EmailUtil.sendOtpEmail(email, otp);

            // 4. Lưu email vào session và chuyển hướng tới màn hình xác thực OTP
            HttpSession session = req.getSession(true);
            session.setAttribute("verify_email", email);
            session.setAttribute("success_msg", "Mã OTP kích hoạt tài khoản đã được gửi đến email " + email + ". Vui lòng kiểm tra hộp thư!");

            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + encodedEmail);
        } else {
            req.setAttribute("alert", "Đăng ký không thành công, vui lòng thử lại!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
        }
    }
}

