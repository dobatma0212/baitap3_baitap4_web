package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
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

@WebServlet(urlPatterns = { "/admin/product/list" })
public class ProductListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService = new ProductServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        String cateIdStr = req.getParameter("cateId");

        List<Product> productList;

        if (cateIdStr != null && !cateIdStr.trim().isEmpty()) {
            try {
                int cateId = Integer.parseInt(cateIdStr.trim());
                productList = productService.getByCategoryId(cateId);
            } catch (NumberFormatException e) {
                productList = productService.getAll();
            }
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            productList = productService.search(keyword.trim());
        } else {
            productList = productService.getAll();
        }

        List<Category> cateList = categoryService.getAll();
        req.setAttribute("productList", productList);
        req.setAttribute("cateList", cateList);

        RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.Path.ADMIN_PRODUCT_LIST);
        dispatcher.forward(req, resp);
    }
}

