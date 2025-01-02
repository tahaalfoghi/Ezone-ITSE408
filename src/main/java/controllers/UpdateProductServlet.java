
package controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Product;
import services.ProductService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/update-product")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class UpdateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Inject private ProductService productService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("update-product.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        productService = new ProductService();
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            String productName = request.getParameter("product-name");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));

            Product product = new Product();
            product.setId(productId);
            product.setName(productName);
            product.setCategory(category);
            product.setPrice(price);

            Part filePart = request.getPart("image");
            String fileName = null;
            if (filePart != null && filePart.getSize() > 0) {
                fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String filePath = uploadPath + File.separator + fileName;

                filePart.write(filePath);

                product.setImage(fileName);
            } else {
                Product existingProduct = productService.getProductById(productId);
                if (existingProduct != null) {
                    product.setImage(existingProduct.getImage());
                }
            }

            boolean updateResult = productService.updateProduct(product);

            if (updateResult) {
                request.getSession().setAttribute("successMessage", "تم تحديث المنتج بنجاح");
                response.sendRedirect("product-list.jsp");
            } else {
                request.getSession().setAttribute("errorMessage", "فشل تحديث المنتج. يرجى المحاولة مرة أخرى");
                response.sendRedirect("update-product.jsp?id=" + productId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "حدث خطأ غير متوقع: " + e.getMessage());
            response.sendRedirect("update-product.jsp?id=" + request.getParameter("id"));
        }
    }
}

