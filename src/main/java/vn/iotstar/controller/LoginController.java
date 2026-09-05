package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }
        if (session != null) {
            String alertSuccess = (String) session.getAttribute("alertSuccess");
            if (alertSuccess != null) {
                req.setAttribute("alertSuccess", alertSuccess);
                session.removeAttribute("alertSuccess");
            }
            String alert = (String) session.getAttribute("alert");
            if (alert != null) {
                req.setAttribute("alert", alert);
                session.removeAttribute("alert");
            }
        }
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    session = req.getSession(true);
                    session.setAttribute("username", cookie.getValue());
                    resp.sendRedirect(req.getContextPath() + "/waiting");
                    return;
                }
            }
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean isRememberMe = false;
        String remember = req.getParameter("remember");

        if ("on".equals(remember)) {
            isRememberMe = true;
        }
        String alertMsg = "";
        req.setAttribute("username", username);

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            alertMsg = "Tài khoản và mật khẩu không được để trống!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        UserService service = new UserServiceImpl();
        User user = service.login(username.trim(), password.trim());
        if (user != null) {
            // Kiểm tra trạng thái kích hoạt tài khoản
            if (user.getStatus() == 0) {
                HttpSession session = req.getSession(true);
                session.setAttribute("verify_email", user.getEmail());
                session.setAttribute("alert", "Tài khoản chưa được kích hoạt qua OTP. Vui lòng nhập mã OTP để kích hoạt!");
                String emailParam = (user.getEmail() != null) ? java.net.URLEncoder.encode(user.getEmail(), java.nio.charset.StandardCharsets.UTF_8) : "";
                resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + emailParam);
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("account", user);
            if (isRememberMe) {
                saveRemeberMe(resp, username.trim());
            }
            resp.sendRedirect(req.getContextPath() + "/waiting");
        } else {
            alertMsg = "Tài khoản hoặc mật khẩu không chính xác!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    private void saveRemeberMe(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);
    }
}

