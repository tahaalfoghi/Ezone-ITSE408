package controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Cart;
import models.Order;
import models.User;
import services.IOrderService;
import services.IProductService;
import services.OrderService;
import services.ProductService;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(value = "/create-order", name = "create-order")
public class CreateOrderServlet extends HttpServlet {

    @Inject private IOrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        orderService = new OrderService();
        resp.setContentType("text/html;charset=UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();
        try(PrintWriter out = resp.getWriter()) {
            User user = (User) req.getSession().getAttribute("auth");
            ArrayList<Cart> cart_list = (ArrayList<Cart>) req.getSession().getAttribute("cart-list");

            if(user != null && cart_list != null && !cart_list.isEmpty()) {
                IProductService productService = new ProductService();
                ArrayList<Cart> cartProducts = productService.getCartProducts(cart_list);
                
                boolean allOrdersSuccessful = true;
                for (Cart item : cartProducts) {
                    Order order = new Order();
                    order.setProduct_id(item.getId());
                    order.setQuantity(item.getQuantity());
                    order.setUser_id(user.getId());
                    order.setCreated_at(java.sql.Date.valueOf(sqlDateFormat.format(date)));

                    boolean result = orderService.addOrder(order);
                    if (!result) {
                        allOrdersSuccessful = false;
                        break;
                    }
                }

                if (allOrdersSuccessful) {
                    cart_list.clear();
                    req.getSession().setAttribute("cart-list", cart_list);
                    resp.sendRedirect("order-confirm.jsp");
                } else {
                    out.print("Some orders failed");
                }
            } else {
                resp.sendRedirect("login.jsp");
            }
        }
    }
}
