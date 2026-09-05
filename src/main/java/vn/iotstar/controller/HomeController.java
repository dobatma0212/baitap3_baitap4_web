package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Product;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.impl.ProductServiceImpl;

@WebServlet(urlPatterns = {"/home", "/admin/home", "/manager/home"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if ("/admin/home".equals(servletPath)) {
            req.getRequestDispatcher("/views/admin/home.jsp").forward(req, resp);
        } else if ("/manager/home".equals(servletPath)) {
            req.getRequestDispatcher("/views/manager/home.jsp").forward(req, resp);
        } else {
            List<Product> top10Products = productService.getTop10Newest();
            req.setAttribute("top10Products", top10Products);
            req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
        }
    }
}

