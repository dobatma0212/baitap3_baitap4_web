package vn.iotstar.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.util.Constant;

@WebServlet(urlPatterns = "/image") // ?fname=category/abc.png
public class DownloadImageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String fileName = req.getParameter("fname");
        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        File file = new File(Constant.DIR + "/" + fileName);
        if (file.exists()) {
            // Tự động nhận diện mime-type dựa theo định dạng file
            String mimeType = getServletContext().getMimeType(file.getName());
            if (mimeType == null) {
                mimeType = "image/jpeg";
            }
            resp.setContentType(mimeType);

            try (FileInputStream in = new FileInputStream(file)) {
                in.transferTo(resp.getOutputStream());
            }
        }
    }
}

