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

@WebServlet(value = "/cart-quantity", name = "cart-quantity")
public class CartQuantityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        try(PrintWriter out = resp.getWriter()) {
            String action = req.getParameter("action");
            int id = Integer.parseInt(req.getParameter("id"));

            ArrayList<Cart> carts = (ArrayList<Cart>) req.getSession().getAttribute("cart-list");

            if(action != null && id>=1){
                if(action.equals("inc")){
                    for(Cart cart : carts){
                        if(cart.getId() == id && cart.getQuantity() >= 1){
                            cart.setQuantity(cart.getQuantity() + 1);
                            resp.sendRedirect("cart.jsp");
                        }
                    }
                }else if(action.equals("dec")){
                    for(Cart cart : carts){
                        if(cart.getId() == id && cart.getQuantity() > 1){
                            cart.setQuantity(cart.getQuantity() - 1);
                            break;
                        }
                    }
                    resp.sendRedirect("cart.jsp");
                }else{
                    resp.sendRedirect("cart.jsp");
                }
            }
        }
    }
}
