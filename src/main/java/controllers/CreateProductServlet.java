
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
import services.IProductService;
import services.ProductService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@MultipartConfig
@WebServlet(value = "/create-product", name = "createProduct")
public class CreateProductServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "uploads";

    @Inject private IProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String appPath = req.getServletContext().getRealPath("");
        String uploadPath = appPath + File.separator + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }


        String productName = req.getParameter("product-name");
        String category = req.getParameter("category");
        double price = Double.parseDouble(req.getParameter("price"));


        Part filePart = req.getPart("image");
        String fileName = filePart.getSubmittedFileName();

        if (fileName != null && !fileName.isEmpty()) {
            String filePath = uploadPath + File.separator + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Product product = new Product();
        product.setName(productName);
        product.setCategory(category);
        product.setPrice(price);
        product.setImage(fileName);

        ProductService productService = new ProductService();
        boolean success = productService.addProduct(product);

        if (success) {
            req.getSession().setAttribute("successMessage", "تم إضافة المنتج بنجاح");
            resp.sendRedirect("product-list.jsp");
        } else {
            req.getSession().setAttribute("errorMessage", "فشل إضافة المنتج. يرجى المحاولة مرة أخرى");
            resp.sendRedirect("create-product.jsp");
        }
    }
}
