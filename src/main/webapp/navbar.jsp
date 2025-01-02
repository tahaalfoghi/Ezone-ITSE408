<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.User" %>
<%@ page import="services.IProductService" %>
<%@ page import="services.ProductService" %>
<%@ page import="models.Product" %>
<%@ page import="models.Cart" %>
<%@ page import="java.util.ArrayList" %>

<%
    User auth = (User) request.getSession().getAttribute("auth");
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    int cartSize = (cart_list != null) ? cart_list.size() : 0;
%>
<%--
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand me-3" href="index.jsp">الرئيسية</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-flex align-items-center" id="navbarSupportedContent">
            <ul class="navbar-nav mb-2 mb-lg-0">
                <%
                    if (auth != null) {
                %>
                <li class="nav-item">
                    <a class="nav-link active text-danger" href="user-logout">تسجيل الخروج
                        <i class="fa-solid fa-right-from-bracket"></i>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="cart.jsp">سلة الشراء
                        <i class="fa-solid fa-cart-shopping"></i>
                        <span class="badge bg-danger ms-1">${cart_list.size()}</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="order.jsp">الطلبات
                        <i class="fa-solid fa-list"></i>
                    </a>
                </li>
                <%
                    if ("Admin".equals(auth.getRole())) {
                %>
                <li class="nav-item">
                    <a class="nav-link active" href="product-list.jsp">إدارة المنتجات
                        <i class="fa-solid fa-boxes-stacked"></i>
                    </a>
                </li>
                <%
                    }
                } else {
                %>
                <li class="nav-item">
                    <a class="nav-link active" href="login.jsp">تسجيل الدخول
                        <i class="fa-solid fa-right-to-bracket"></i>
                    </a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>
--%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand me-3" href="index.jsp">الرئيسية</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-flex align-items-center" id="navbarSupportedContent">
            <ul class="navbar-nav mb-2 mb-lg-0">
                <%-- Home link always displayed --%>
                <%-- Logout displayed after Home if authenticated --%>
                <%
                    if (auth != null) {
                %>
                <li class="nav-item">
                    <a class="nav-link active text-danger" href="user-logout">تسجيل الخروج
                        <i class="fa-solid fa-right-from-bracket"></i>
                    </a>
                </li>
                <%
                    }
                %>
                <%-- Cart always visible --%>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="cart.jsp">
                        سلة الشراء
                        <i class="fa-solid fa-cart-shopping"></i>
                        <%-- Display cart size if available --%>
                        <span class="badge bg-danger ms-1">
                            <%= (cart_list != null) ? cart_list.size() : 0 %>
                        </span>
                    </a>
                </li>
                <%-- Additional links if authenticated --%>
                <%
                    if (auth != null) {
                %>
                <li class="nav-item">
                    <a class="nav-link active" href="order.jsp">الطلبات
                        <i class="fa-solid fa-list"></i>
                    </a>
                </li>
                <%-- Admin-specific options --%>
                <%
                    if ("Admin".equals(auth.getRole())) {
                %>
                <li class="nav-item">
                    <a class="nav-link active" href="product-list.jsp">إدارة المنتجات
                        <i class="fa-solid fa-boxes-stacked"></i>
                    </a>
                </li>
                <li class="nav-item">
                        <a class="nav-link active" href="users.jsp">إدارة المستخدمين
                            <i class="fa-solid fa-users"></i>
                        </a>
                </li>
                <%
                    }
                } else {
                %>
                <li class="nav-item">
                    <a class="nav-link active text-success" href="login.jsp">تسجيل الدخول
                        <i class="fa-solid fa-right-to-bracket"></i>
                    </a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>
