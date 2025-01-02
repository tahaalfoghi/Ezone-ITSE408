<%@ page language="java" contentType="text/html; charset=UTF-8" import="models.User" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Cart" %>
<%@ page import="services.IProductService" %>
<%@ page import="services.ProductService" %>
<%
    User auth = (User) request.getSession().getAttribute("auth");

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    ArrayList<Cart> cartProducts  = null;
    if(cart_list != null){
        IProductService productService = new ProductService();
        cartProducts = productService.getCartProducts(cart_list);
        request.setAttribute("cart_list",cart_list);

    }
    double cartTotal = 0;
    int cartItemCount = 0;
    if (cartProducts != null) {
        for(Cart item : cartProducts){
            cartTotal += item.getPrice() * item.getQuantity();
        }
        cartItemCount = cartProducts.size();
    }

    request.setAttribute("cartTotal",cartTotal);
%>

<!DOCTYPE html>
<html dir="rtl">
<head>
    <title>سلة التسوق</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>


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
                <%
                    }
                %>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="cart.jsp">
                        سلة الشراء
                        <i class="fa-solid fa-cart-shopping"></i>
                        <span class="badge bg-danger ms-1">
                            <%= (cart_list != null) ? cart_list.size() : 0 %>
                        </span>
                    </a>
                </li>
                <%
                    if (auth != null) {
                %>
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

<body>
<div class="container mt-5">
    <h2 class="mb-4">سلة التسوق</h2>
    <% if (cart_list == null || cart_list.isEmpty()) { %>
    <div class="alert alert-info" role="alert">
        <h2 class="text-center">سلة التسوق فارغة</h2>
        <div class="container text-center">
            <div class="justify-content-center">
                <img src="uploads/cartPage.png" style="height: 250px; width: 250px" class="text-center" alt="">
            </div>
            <a class="btn btn-primary" href="index.jsp">تصفح المنتجات</a>
        </div>
    </div>
    <% } else { %>
    <div class="row">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    المنتجات في السلة
                </div>
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>الصورة</th>
                            <th>اسم المنتج</th>
                            <th>الفئة</th>
                            <th>السعر</th>
                            <th>الكمية</th>
                            <th>الإجمالي</th>
                            <th>الإجراءات</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% if (cartProducts != null) {
                            for (Cart item : cartProducts) { %>
                        <tr>
                            <td>
                                <img src="uploads/<%= item.getImage() %>" class="img-fluid" style="max-width: 100px;" alt="Product Image">
                            </td>
                            <td><%= item.getName() %></td>
                            <td><%= item.getCategory() %></td>
                            <td><%= item.getPrice() %> دينار</td>
                            <td>
                                <div class="input-group">
                                    <a href="cart-quantity?action=dec&id=<%=item.getId()%>" class="btn btn-sm btn-outline-secondary decrease-qty">-</a>
                                    <input type="text" name="quantity_<%=item.getId()%>" class="form-control form-control-sm text-center qty-input" value="<%= item.getQuantity() %>" style="max-width: 70px;">
                                    <a href="cart-quantity?action=inc&id=<%=item.getId()%>" class="btn btn-sm btn-outline-secondary increase-qty">+</a>
                                </div>
                            </td>
                            <td><%= String.format("%.2f", item.getPrice() * item.getQuantity()) %> دينار</td>
                            <td>
                                <a href="remove-cart?id=<%=item.getId()%>" class="btn btn-danger btn-sm remove-item">
                                    <i class="fas fa-trash"></i>
                                </a>
                            </td>
                        </tr>
                        <% }
                        } %>
                        </tbody>
                    </table>
                    <% if (cartProducts != null && !cartProducts.isEmpty()) { %>
                    <div class="text-center mt-3">
                        <form action="create-order" method="post">
                            <button type="submit" class="btn btn-success btn-lg">
                                <i class="fas fa-shopping-cart me-2"></i>إتمام عملية الشراء
                            </button>
                        </form>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">ملخص السلة</h5>
                </div>
                <div class="card-body">
                    <div class="d-flex justify-content-between mb-2">
                        <span>عدد المنتجات:</span>
                        <strong><%= cartItemCount %></strong>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <span>إجمالي السعر:</span>
                        <strong><%= String.format("%.2f", cartTotal) %> دينار</strong>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <% } %>
</div>


<script src="assets/js/jquery.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
</body>
</html>
<style>
    .table tbody td{
        vertical-align: middle;
    }
    .btn-increment, .btn-decrease{
        box-shadow: none;
        font-size: 25px;
    }
</style>