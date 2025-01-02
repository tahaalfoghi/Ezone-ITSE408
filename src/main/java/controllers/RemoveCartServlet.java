package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(value = "/remove-cart", name = "remove-cart")
public class RemoveCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {

            String id = req.getParameter("id");
            if(id!=null){
                ArrayList<Cart> carts = (ArrayList<Cart>) req.getSession().getAttribute("cart-list");
                if(carts!=null){
                    for (Cart cart : carts) {
                        if(cart.getId()==Integer.parseInt(id)){
                            carts.remove(cart);
                            break;
                        }
                    }
                    resp.sendRedirect("cart.jsp");
                }
            }else{
                resp.sendRedirect("cart.jsp");
            }
        }
    }
}
