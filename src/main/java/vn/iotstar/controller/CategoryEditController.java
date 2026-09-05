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

@WebServlet(urlPatterns = { "/admin/category/edit" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class CategoryEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            Category category = cateService.get(id);
            req.setAttribute("category", category);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.Path.ADMIN_CATEGORY_EDIT);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            return;
        }

        String name = req.getParameter("name");
        Part part = req.getPart("icon");
        String icon = null;

        Category existingCategory = cateService.get(id);
        if (existingCategory == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("alert", "Tên danh mục không được để trống!");
            existingCategory.setName(name);
            req.setAttribute("category", existingCategory);
            req.getRequestDispatcher(Constant.Path.ADMIN_CATEGORY_EDIT).forward(req, resp);
            return;
        }

        name = name.trim();
        if (name.length() > 255) {
            req.setAttribute("alert", "Tên danh mục không được vượt quá 255 ký tự!");
            existingCategory.setName(name);
            req.setAttribute("category", existingCategory);
            req.getRequestDispatcher(Constant.Path.ADMIN_CATEGORY_EDIT).forward(req, resp);
            return;
        }

        if (part != null && part.getSize() > 0) {
            String originalFilename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!vn.iotstar.util.ValidationUtil.isValidImageFile(originalFilename)) {
                req.setAttribute("alert", "Ảnh đại diện phải là file định dạng ảnh (.jpg, .jpeg, .png, .webp, .gif)!");
                existingCategory.setName(name);
                req.setAttribute("category", existingCategory);
                req.getRequestDispatcher(Constant.Path.ADMIN_CATEGORY_EDIT).forward(req, resp);
                return;
            }

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
        category.setId(id);
        category.setName(name);
        category.setIcon(icon);

        String violationMsg = vn.iotstar.util.ValidationUtil.validateEntity(category);
        if (violationMsg != null) {
            req.setAttribute("alert", violationMsg);
            req.setAttribute("category", category);
            req.getRequestDispatcher(Constant.Path.ADMIN_CATEGORY_EDIT).forward(req, resp);
            return;
        }

        cateService.edit(category);
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}

