package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        try(PrintWriter out = resp.getWriter()) {
            ArrayList<Cart> cartList = new ArrayList<>();

            int id = Integer.parseInt(req.getParameter("id"));
            Cart cart = new Cart();
            cart.setId(id);
            cart.setQuantity(1);

            HttpSession session = req.getSession();
            ArrayList<Cart> carts = (ArrayList<Cart>) session.getAttribute("cart-list");

            if(carts == null){
                cartList.add(cart);
                session.setAttribute("cart-list", cartList);
                out.println("<html><head>");
                out.println("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>");
                out.println("</head><body>");
                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({");
                out.println("    title: ' تم إضافة المنتج السلة',");
                out.println("    icon: 'success',");
                out.println("    confirmButtonText: 'حسناً'");
                out.println("}).then(() => {");
                out.println("    window.location.href = 'index.jsp';"); // Redirect after SweetAlert
                out.println("});");
                out.println("</script>");
                out.println("</body></html>");
            }else{
                cartList = carts;
                boolean isExists = false;

                for(Cart cartItem : carts){
                    if(cartItem.getId() == id){
                        isExists = true;
                        out.println("<html><head>");
                        out.println("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>");
                        out.println("</head><body>");
                        out.println("<script type='text/javascript'>");
                        out.println("Swal.fire({");
                        out.println("    title: 'المنتج موجود بالفعل في سلة الشراء',");
                        out.println("    icon: 'warning',");
                        out.println("    confirmButtonText: 'حسناً'");
                        out.println("}).then(() => {");
                        out.println("    window.location.href = 'index.jsp';"); // Redirect after SweetAlert
                        out.println("});");
                        out.println("</script>");
                        out.println("</body></html>");
                    }
                }
                if(!isExists){
                    cartList.add(cart);
                    out.println("<html><head>");
                    out.println("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>");
                    out.println("</head><body>");
                    out.println("<script type='text/javascript'>");
                    out.println("Swal.fire({");
                    out.println("    title: ' تم إضافة المنتج السلة',");
                    out.println("    icon: 'success',");
                    out.println("    confirmButtonText: 'حسناً'");
                    out.println("}).then(() => {");
                    out.println("    window.location.href = 'index.jsp';"); // Redirect after SweetAlert
                    out.println("});");
                    out.println("</script>");
                    out.println("</body></html>");
                }
            }
        }
    }
}
