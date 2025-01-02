package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/user-logout", name = "logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try(PrintWriter out = resp.getWriter()) {
            if(req.getSession().getAttribute("auth") != null) {
                req.getSession().removeAttribute("auth");
                resp.sendRedirect("login.jsp");
            }else{
                resp.sendRedirect("index.jsp");
            }
        }
    }
}
