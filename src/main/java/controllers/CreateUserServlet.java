package controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import services.IUserService;
import services.UserService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/create-user")
public class CreateUserServlet extends HttpServlet {


    @Inject private IUserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        userService = new UserService();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String role = req.getParameter("role");

        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);

        System.out.println("Creating user: " + user);

        boolean result = userService.addUser(user);
        System.out.println("User creation result: " + result);

        if (result) {
            resp.sendRedirect("users.jsp");
        } else {
            resp.sendRedirect("create-user.jsp?error=true");
        }
    }
}
