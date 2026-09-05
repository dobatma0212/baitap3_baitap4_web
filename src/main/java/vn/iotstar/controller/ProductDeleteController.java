package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.impl.ProductServiceImpl;

@WebServlet(urlPatterns = { "/admin/product/delete" })
public class ProductDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                productService.delete(id);
            } catch (NumberFormatException ignored) {}
        }
        resp.sendRedirect(req.getContextPath() + "/admin/product/list");
    }
}

