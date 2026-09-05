package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Category;
import vn.iotstar.model.Product;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.util.Constant;

@WebServlet(urlPatterns = { "/product", "/products" })
public class ProductWebController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_PAGE_SIZE = 6; // Phân trang 6 sản phẩm / trang

    private ProductService productService = new ProductServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String pageStr = req.getParameter("page");
        String keyword = req.getParameter("keyword");
        String cateIdStr = req.getParameter("cateId");

        int page = 1;
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr.trim());
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException ignored) {}
        }

        Integer cateId = null;
        if (cateIdStr != null && !cateIdStr.trim().isEmpty()) {
            try {
                cateId = Integer.parseInt(cateIdStr.trim());
            } catch (NumberFormatException ignored) {}
        }

        int totalProducts = productService.countByFilter(keyword, cateId);
        int totalPages = (int) Math.ceil((double) totalProducts / DEFAULT_PAGE_SIZE);
        if (totalPages == 0) {
            totalPages = 1;
        }

        if (page > totalPages) {
            page = totalPages;
        }

        List<Product> productList = productService.getProductsByFilter(keyword, cateId, page, DEFAULT_PAGE_SIZE);
        List<Category> cateList = categoryService.getAll();

        req.setAttribute("productList", productList);
        req.setAttribute("cateList", cateList);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalProducts", totalProducts);
        req.setAttribute("pageSize", DEFAULT_PAGE_SIZE);
        req.setAttribute("keyword", keyword);
        req.setAttribute("cateId", cateId);

        req.getRequestDispatcher(Constant.Path.WEB_PRODUCT).forward(req, resp);
    }
}

