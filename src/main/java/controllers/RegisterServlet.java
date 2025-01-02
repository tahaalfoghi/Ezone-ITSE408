package controllers;


import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import services.UserService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user-register")
public class RegisterServlet extends HttpServlet {


    @Inject private  UserService userService ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService  = new UserService();
        resp.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {

            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String email = req.getParameter("email");

            System.out.println("Registration attempt - Username: " + username + ", Email: " + email);

            User user = new User();
            user.setName(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setRole("Customer");

            boolean success = userService.register(user);
            System.out.println("Registration result: " + success);
            
            if (success) {
                resp.sendRedirect("login.jsp");
                req.getSession().setAttribute("auth", user);
                System.out.println("Redirecting to login.jsp");
            } else {
                System.out.println("Registration failed");
                out.print("حدث خطأ يرجى اعادة المحاولة");
            }
        } catch (Exception e) {
            System.out.println("Exception during registration: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
