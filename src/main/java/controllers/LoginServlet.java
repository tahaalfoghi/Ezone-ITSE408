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

@WebServlet(value = "/user-login", name = "login")
public class LoginServlet extends HttpServlet {

    @Inject private IUserService userService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService = new UserService();
        resp.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            User userInDb = userService.login(user);
            if(userInDb == null){
                out.print("this user is not found in the database");
            }
            else {
                out.print(userInDb.toString());
                req.getSession().setAttribute("auth",userInDb);
                resp.sendRedirect("index.jsp");
                //out.print("Login successful");
            }
        }
    }
}
