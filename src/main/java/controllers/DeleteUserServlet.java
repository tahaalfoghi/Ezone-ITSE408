package controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.IUserService;
import services.UserService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
    @Inject private IUserService userService ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService = new UserService();
        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            boolean result = userService.deleteUser(userId);
            
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            
            if (result) {
                out.println("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>");
                out.println("<script>");
                out.println("Swal.fire({");
                out.println("    title: 'تم حذف المستخدم بنجاح',");
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
                out.println("    title: 'فشل حذف المستخدم',");
                out.println("    icon: 'error',");
                out.println("    confirmButtonText: 'حسناً'");
                out.println("}).then(() => {");
                out.println("    window.location.href = 'users.jsp';");
                out.println("});");
                out.println("</script>");
                resp.sendRedirect("users.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting user");
        }
    }
}
