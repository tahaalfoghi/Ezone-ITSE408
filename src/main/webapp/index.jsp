<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="models.User" %>
<%@ page import="services.IProductService" %>
<%@ page import="services.ProductService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Product" %>
<%@ page import="models.Cart" %>
<%
    User auth = (User) request.getSession().getAttribute("auth");
    IProductService productService = new ProductService();
    ArrayList<Product> products = productService.getAllProducts();

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    ArrayList<Cart> cartProducts  = null;
    if(cart_list != null){
        cartProducts = productService.getCartProducts(cart_list);
        request.setAttribute("cart_list",cart_list);
    }
    double cartTotal = 0;
    int cartSize = 0;
    if (cartProducts != null) {
        for(Cart item : cartProducts){
            cartTotal += item.getPrice() * item.getQuantity();
        }
        cartSize = cartProducts.size();
    }
%>
<!DOCTYPE html>
<html dir="rtl" lang="ar">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> Ezone</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 100px 0;
            text-align: center;
        }
        .hero-content {
            max-width: 800px;
            margin: 0 auto;
        }
        .product-section {
            padding: 50px 0;
        }
        .card-hover:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
            transition: all 0.3s ease;
        }
    </style>
</head>
<body>

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
                            <i class="fa-solid fa-users"></i>                        </a>
                    </li>
                <%
                    }
                } else {
                %>

                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>

<section class="hero-section">
    <div class="container">
        <div class="hero-content">
            <h1 class="display-4 mb-4">مرحبًا بك في ايزون</h1>
            <p class="lead mb-5">
                اكتشف مجموعتنا المميزة من المنتجات عالية الجودة بأسعار تنافسية
            </p>
            <% if (auth == null) { %>
            <a href="login.jsp" class="btn btn-light btn-lg">
                تسجيل الدخول الآن
                <i class="fas fa-sign-in-alt me-2"></i>
            </a>
            <% } %>
        </div>
    </div>
</section>

<section class="product-section">
    <div class="container">
        <h2 class="text-center mb-5">منتجاتنا</h2>
        <div class="row">
            <%
                if(!products.isEmpty()){
                    for(Product p : products){%>
            <div class="col-md-3 my-3 d-flex">
                <div class="card w-100 text-center d-flex flex-column card-hover">
                    <div class="card-img-top-container" style="height: 250px; overflow: hidden;">
                        <img src="uploads/<%= p.getImage() %>" alt="img" class="card-img-top" style="width: 100%; height: 100%; object-fit: cover;">
                    </div>
                    <div class="card-body flex-grow-1 d-flex flex-column">
                        <h5 class="card-title mb-2"> <%= p.getName() %> </h5>
                        <h6 class="card-category text-muted mb-2"> <%= p.getCategory() %>  </h6>
                        <h5 class="price mb-3"><strong><%= p.getPrice() %> دينار </strong></h5>
                        <div class="mt-auto d-flex justify-content-between">
                            <a href="create-order?quantity=<%=1%>&id=<%=p.getId()%>" class="btn btn-warning flex-grow-1 me-1">إشتري الان
                                <i class="fa-solid fa-money-bill me-2"></i>
                            </a>
                            <a href="add-to-cart?id=<%= p.getId() %>" class="btn btn-primary flex-grow-1 ms-1">إضافة الى السلة
                                <i class="fa-solid fa-basket-shopping me-2"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <%}
            } else {
            %>
            <div class="col-12 text-center">
                <p class="alert alert-info">لا توجد منتجات متاحة حاليًا</p>
            </div>
            <% } %>
        </div>
    </div>
</section>

<footer class="bg-light py-4 mt-5">
    <div class="container text-center">
        <p class="mb-0">&copy; 2024 متجرنا. جميع الحقوق محفوظة.</p>
    </div>
</footer>

<script src="assets/js/jquery.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>
