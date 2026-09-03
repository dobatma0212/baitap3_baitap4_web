package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;

@WebServlet(urlPatterns = { "/profile", "/member/myaccount" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || (session.getAttribute("account") == null && session.getAttribute("username") == null)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("account");
        if (user == null) {
            String username = (String) session.getAttribute("username");
            user = userService.get(username);
        } else {
            // Lấy thông tin mới nhất từ cơ sở dữ liệu
            User freshUser = userService.get(user.getUserName());
            if (freshUser != null) {
                user = freshUser;
                session.setAttribute("account", user);
            }
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || (session.getAttribute("account") == null && session.getAttribute("username") == null)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("account");
        if (user == null) {
            String username = (String) session.getAttribute("username");
            user = userService.get(username);
        } else {
            User freshUser = userService.get(user.getUserName());
            if (freshUser != null) {
                user = freshUser;
            }
        }

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Nhận dữ liệu từ các trường văn bản
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");

        user.setFullName(fullname);
        user.setPhone(phone);

        // Xử lý lấy file ảnh đại diện và lưu vào thư mục vật lý
        try {
            Part part = req.getPart("images");
            if (part == null || part.getSize() == 0) {
                part = req.getPart("image");
            }

            if (part != null && part.getSize() > 0) {
                String originalFilename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                int index = originalFilename.lastIndexOf(".");
                String ext = (index != -1) ? originalFilename.substring(index) : "";
                String fileName = System.currentTimeMillis() + ext;

                File uploadDir = new File(Constant.DIR + "/user");
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                part.write(uploadDir.getAbsolutePath() + File.separator + fileName);
                String imagePath = "user/" + fileName;
                user.setImages(imagePath);
                user.setAvatar(imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Gọi tầng Service để cập nhật dữ liệu vào CSDL
        userService.update(user);

        // Làm mới lại Session với thông tin tài khoản vừa cập nhật
        session.setAttribute("account", user);

        // Gửi thông báo thành công sang giao diện
        req.setAttribute("user", user);
        req.setAttribute("message", "Cập nhật hồ sơ cá nhân thành công!");
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }
}
