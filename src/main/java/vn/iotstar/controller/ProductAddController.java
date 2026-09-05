package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.model.Category;
import vn.iotstar.model.Product;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.util.Constant;

@WebServlet(urlPatterns = { "/admin/product/add" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class ProductAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService = new ProductServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        List<Category> cateList = categoryService.getAll();
        req.setAttribute("cateList", cateList);
        RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.Path.ADMIN_PRODUCT_ADD);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");
        String quantityStr = req.getParameter("quantity");
        String statusStr = req.getParameter("status");
        String cateIdStr = req.getParameter("cateId");

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);

        if (name == null || name.trim().isEmpty()) {
            forwardWithError(req, resp, product, "Tên sản phẩm không được để trống!");
            return;
        }

        name = name.trim();
        if (name.length() > 255) {
            forwardWithError(req, resp, product, "Tên sản phẩm không được vượt quá 255 ký tự!");
            return;
        }
        product.setName(name);

        int cateId = 0;
        try {
            cateId = Integer.parseInt(cateIdStr);
        } catch (Exception e) {
            forwardWithError(req, resp, product, "Vui lòng chọn danh mục hợp lệ!");
            return;
        }

        Category category = categoryService.get(cateId);
        if (category == null) {
            forwardWithError(req, resp, product, "Danh mục đã chọn không tồn tại!");
            return;
        }
        product.setCategory(category);

        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
            if (price < 0) {
                forwardWithError(req, resp, product, "Giá sản phẩm phải lớn hơn hoặc bằng 0!");
                return;
            }
        } catch (Exception e) {
            forwardWithError(req, resp, product, "Giá sản phẩm không hợp lệ! Vui lòng nhập số.");
            return;
        }
        product.setPrice(price);

        int quantity = 0;
        try {
            if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                quantity = Integer.parseInt(quantityStr.trim());
                if (quantity < 0) {
                    forwardWithError(req, resp, product, "Số lượng trong kho không được âm!");
                    return;
                }
            }
        } catch (Exception e) {
            forwardWithError(req, resp, product, "Số lượng sản phẩm không hợp lệ! Vui lòng nhập số nguyên.");
            return;
        }
        product.setQuantity(quantity);

        int status = 1;
        try {
            if (statusStr != null && !statusStr.trim().isEmpty()) {
                status = Integer.parseInt(statusStr.trim());
            }
        } catch (Exception ignored) {}
        product.setStatus(status);

        Part part = req.getPart("image");
        String images = null;

        if (part != null && part.getSize() > 0) {
            String originalFilename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!vn.iotstar.util.ValidationUtil.isValidImageFile(originalFilename)) {
                forwardWithError(req, resp, product, "Hình ảnh sản phẩm phải là file ảnh (.jpg, .jpeg, .png, .webp, .gif)!");
                return;
            }

            int index = originalFilename.lastIndexOf(".");
            String ext = (index != -1) ? originalFilename.substring(index) : "";
            String fileName = System.currentTimeMillis() + ext;

            File uploadDir = new File(Constant.DIR + "/product");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            part.write(uploadDir.getAbsolutePath() + File.separator + fileName);
            images = "product/" + fileName;
            product.setImages(images);
        }

        String violationMsg = vn.iotstar.util.ValidationUtil.validateEntity(product);
        if (violationMsg != null) {
            forwardWithError(req, resp, product, violationMsg);
            return;
        }

        productService.insert(product);
        resp.sendRedirect(req.getContextPath() + "/admin/product/list");
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, Product product, String errorMsg)
            throws ServletException, IOException {
        req.setAttribute("alert", errorMsg);
        req.setAttribute("product", product);
        req.setAttribute("cateList", categoryService.getAll());
        req.getRequestDispatcher(Constant.Path.ADMIN_PRODUCT_ADD).forward(req, resp);
    }
}

