
package controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ProductService;

import java.io.IOException;

@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet {
    @Inject private ProductService productService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        productService = new ProductService();
        try {

            int productId = Integer.parseInt(request.getParameter("id"));

            boolean deleteResult = productService.deleteProduct(productId);

            if (deleteResult) {
                request.getSession().setAttribute("successMessage", "تم حذف المنتج بنجاح");
            } else {
                request.getSession().setAttribute("errorMessage", "فشل حذف المنتج. يرجى المحاولة مرة أخرى");
            }

            response.sendRedirect("product-list.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "حدث خطأ غير متوقع: " + e.getMessage());
            response.sendRedirect("product-list.jsp");
        }
    }
}
