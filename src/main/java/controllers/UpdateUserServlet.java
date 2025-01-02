package controllers;

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

@WebServlet("/update-user")
public class UpdateUserServlet extends HttpServlet {
    private IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        int id = Integer.parseInt(userId);
        User user = userService.getUserById(id);
        
        req.setAttribute("user", user);
        req.getRequestDispatcher("update-user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String role = req.getParameter("role");
            String password = req.getParameter("password");

            User user = new User();
            user.setId(userId);
            user.setName(username);
            user.setEmail(email);
            user.setRole(role);
            user.setPassword(password);

            boolean result = userService.updateUser(user);
            
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            
            if (result) {
                out.println("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>");
                out.println("<script>");
                out.println("Swal.fire({");
                out.println("    title: 'تم تحديث المستخدم بنجاح',");
                out.println("    icon: 'success',");
                out.println("    confirmButtonText: 'حسناً'");
                out.println("}).then(() => {");
                out.println("    window.location.href = 'users.jsp';");
                out.println("});");
                out.println("</script>");
                resp.sendRedirect("users.jsp");
            } else {
                out.println("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>");
                out.println("<script>");
                out.println("Swal.fire({");
                out.println("    title: 'فشل تحديث المستخدم',");
                out.println("    icon: 'error',");
                out.println("    confirmButtonText: 'حسناً'");
                out.println("}).then(() => {");
                out.println("    window.location.href = 'users.jsp';");
                out.println("});");
                out.println("</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating user");
        }
    }
}
