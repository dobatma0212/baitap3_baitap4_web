package vn.iotstar.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Product;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.util.Constant;

@WebServlet(urlPatterns = { "/product/detail", "/product-detail" })
public class ProductDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/product");
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            Product product = productService.get(id);

            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/product");
                return;
            }

            List<Product> relatedProducts = Collections.emptyList();
            if (product.getCategory() != null) {
                List<Product> sameCate = productService.getByCategoryId(product.getCategory().getId());
                if (sameCate != null) {
                    relatedProducts = sameCate.stream()
                            .filter(p -> p.getId() != product.getId())
                            .limit(4)
                            .collect(Collectors.toList());
                }
            }

            req.setAttribute("product", product);
            req.setAttribute("relatedProducts", relatedProducts);

            req.getRequestDispatcher(Constant.Path.WEB_PRODUCT_DETAIL).forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/product");
        }
    }
}

