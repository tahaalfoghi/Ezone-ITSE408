<%@ page import="java.util.ArrayList" %>
<%@ page import="models.UserOrderDetails" %>
<%@ page import="models.User" %>
<%@ page import="services.OrderService" %>
<%@ page import="services.UserOrderDetailsService" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User user = (User) session.getAttribute("auth");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    ArrayList<UserOrderDetails> userOrderDetails = UserOrderDetailsService.getUserOrderDetails(user.getId());
    
    // Group orders by date
    Map<String, ArrayList<UserOrderDetails>> ordersByDate = new TreeMap<>((a, b) -> b.compareTo(a)); // Reverse order
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    // If userOrderDetails is not null, process the orders
    if (userOrderDetails != null && !userOrderDetails.isEmpty()) {
        // Modify the code to use a default date if not set
        for (UserOrderDetails order : userOrderDetails) {
            String formattedDate = "Unknown Date";
            
            // If you have a way to get the order date from another source, add it here
            // For now, we'll use the current date as a fallback
            if (order.getOrderDate() == null) {
                order.setOrderDate(new Date());
            }
            
            formattedDate = dateFormat.format(order.getOrderDate());
            ordersByDate.computeIfAbsent(formattedDate, k -> new ArrayList<>()).add(order);
        }
    }
    System.out.println(userOrderDetails);
%>

<!DOCTYPE html>
<html dir="rtl" lang="ar">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>طلباتي</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Cairo', sans-serif;
        }
        .order-section {
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            overflow: hidden;
        }
        .order-date-header {
            background-color: #007bff;
            color: white;
            padding: 15px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .order-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            margin-bottom: 15px;
            border: none;
            border-bottom: 1px solid #e9ecef;
        }
        .order-card:last-child {
            border-bottom: none;
        }
        .order-card:hover {
            transform: translateX(-10px);
            box-shadow: -5px 5px 15px rgba(0,0,0,0.1);
        }
        .product-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 10px;
        }
        .empty-orders {
            text-align: center;
            padding: 50px;
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        @media (max-width: 768px) {
            .order-card {
                flex-direction: column;
                align-items: flex-start !important;
            }
            .product-image {
                margin-bottom: 10px;
            }
        }
    </style>
</head>
<body>
<%@include file="navbar.jsp" %>

<div class="container mt-5">
    <div class="row">
        <div class="col-12">
            <h1 class="text-center mb-4">
                <i class="fas fa-shopping-bag me-2"></i>طلباتي
            </h1>

            <% if (userOrderDetails == null || userOrderDetails.isEmpty()) { %>
            <div class="empty-orders">
                <i class="fas fa-shopping-cart fa-4x text-muted mb-3"></i>
                <h3>لا توجد طلبات حتى الآن</h3>
                <p class="text-muted">يبدو أنك لم تقم بأي عمليات شراء بعد</p>
                <a href="index.jsp" class="btn btn-primary mt-3">تصفح المنتجات</a>
            </div>
            <% } else { %>
            <div class="orders-container">
                <% for (Map.Entry<String, ArrayList<UserOrderDetails>> dateEntry : ordersByDate.entrySet()) { %>
                <div class="order-section mb-4">
                    <div class="order-date-header">
                        <span>الطلبات في <%= dateEntry.getKey() %></span>
                        <span class="badge bg-light text-dark"><%= dateEntry.getValue().size() %> طلب</span>
                    </div>
                    <div class="p-3">
                        <% for (UserOrderDetails orderDetail : dateEntry.getValue()) { %>
                        <div class="card order-card d-flex flex-row align-items-center p-3">
                            <img src="uploads/<%= orderDetail.getImage() %>"
                                 alt="<%= orderDetail.getProductName() %>"
                                 class="product-image me-3">
                            <div class="flex-grow-1">
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <h5 class="mb-1"><%= orderDetail.getProductName() %></h5>
                                    <span class="badge bg-success">مكتمل</span>
                                </div>
                                <div class="text-muted small mb-2">
                                    التصنيف: <%= orderDetail.getCategory() %>
                                </div>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <strong>السعر:</strong>
                                        <%= String.format("%.2f", orderDetail.getUnitPrice()) %> ريال
                                    </div>
                                    <div class="text-muted small">
                                        رقم الطلب: <%= orderDetail.getOrderId() %>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>
                <% } %>
            </div>
            <% } %>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
