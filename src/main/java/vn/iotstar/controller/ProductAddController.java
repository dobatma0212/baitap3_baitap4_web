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
        double price = 0;
        try {
            price = Double.parseDouble(req.getParameter("price"));
        } catch (Exception ignored) {}

        int quantity = 0;
        try {
            quantity = Integer.parseInt(req.getParameter("quantity"));
        } catch (Exception ignored) {}

        int status = 1;
        try {
            status = Integer.parseInt(req.getParameter("status"));
        } catch (Exception ignored) {}

        int cateId = 0;
        try {
            cateId = Integer.parseInt(req.getParameter("cateId"));
        } catch (Exception ignored) {}

        Part part = req.getPart("image");
        String images = null;

        if (part != null && part.getSize() > 0) {
            String originalFilename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            int index = originalFilename.lastIndexOf(".");
            String ext = (index != -1) ? originalFilename.substring(index) : "";
            String fileName = System.currentTimeMillis() + ext;

            File uploadDir = new File(Constant.DIR + "/product");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            part.write(uploadDir.getAbsolutePath() + File.separator + fileName);
            images = "product/" + fileName;
        }

        Category category = categoryService.get(cateId);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setStatus(status);
        product.setImages(images);
        product.setCategory(category);

        productService.insert(product);
        resp.sendRedirect(req.getContextPath() + "/admin/product/list");
    }
}

