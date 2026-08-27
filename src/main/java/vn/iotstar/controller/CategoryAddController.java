package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.model.Category;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.util.Constant;

@WebServlet(urlPatterns = { "/admin/category/add" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class CategoryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.Path.ADMIN_CATEGORY_ADD);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        Part part = req.getPart("icon");
        String icon = null;

        if (part != null && part.getSize() > 0) {
            String originalFilename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            int index = originalFilename.lastIndexOf(".");
            String ext = (index != -1) ? originalFilename.substring(index) : "";
            String fileName = System.currentTimeMillis() + ext;

            File uploadDir = new File(Constant.DIR + "/category");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            part.write(uploadDir.getAbsolutePath() + File.separator + fileName);
            icon = "category/" + fileName;
        }

        Category category = new Category();
        category.setName(name);
        category.setIcon(icon);

        cateService.insert(category);
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}

