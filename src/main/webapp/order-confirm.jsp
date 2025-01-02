<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html dir="rtl">
<head>
    <title>تأكيد الطلب</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .confirmation-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 80vh;
            text-align: center;
            color: #333;
        }
        .confirmation-image {
            width: 450px;
            height: 450px;
            border-radius: 10px;
            margin-bottom: 15px;
        }
        .confirmation-message {
            font-size: 1.3rem;
            font-weight: bold;
            margin-bottom: 8px;
            color: #28a745;
        }
        .confirmation-text {
            font-size: 0.9rem;
            color: #6c757d;
        }
        .btn-return {
            font-size: 0.85rem;
            padding: 8px 16px;
        }
    </style>
</head>
<%@include file="navbar.jsp"%>
<body>
<div class="container confirmation-container">
    <img src="uploads/confirm.jpg" alt="Order Confirmed" class="confirmation-image">
    <div class="confirmation-message">تم تأكيد طلبك بنجاح!</div>
    <p class="confirmation-text">شكراً لتسوقك معنا. سيتم معالجة طلبك قريباً.</p>
    <a href="index.jsp" class="btn btn-success btn-return mt-3">
        <i class="fas fa-home"></i> العودة إلى الصفحة الرئيسية
    </a>
    <a href="order.jsp" class="btn btn-success btn-return mt-3">
        <i class="fas fa-list"></i> الذهاب الى طليياتي
    </a>
</div>
</body>
</html>
