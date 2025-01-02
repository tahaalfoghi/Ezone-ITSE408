<%@ page import="models.Product" %>
<%@ page import="services.IProductService" %>
<%@ page import="services.ProductService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    IProductService productService = new ProductService();
    int id = Integer.parseInt(request.getParameter("id"));
    Product product = productService.getProductById(id);
%>
<html dir="rtl">
<head>
    <title>تعديل منتج</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" >
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css" >
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>

<%@include file="navbar.jsp" %>
<body>
<div class="container mt-5">
    <h2>تعديل منتج</h2>
    <div class="card-body mt-3">
        <form action="update-product" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="<%= product.getId()%>">
            <div class="mb-3">
                <label class="form-label">إسم المنتج</label>
                <input type="text" class="form-control" name="product-name" value="<%= product.getName()%>">
            </div>
            <div class="mb-3">
                <label  class="form-label">التصنيف</label>
                <input type="text" name="category" class="form-control" value="<%=product.getCategory()%>">
            </div>
            <div class="mb-3">
                <label class="form-label">السعر</label>
                <input type="number" name="price" class="form-control" value="<%=product.getPrice()%>">
            </div>
            <div class="mb-3">
                <label class="form-label">صورة المنتج</label>
                <input type="file" name="image" class="form-control" >
            </div>
            <button type="submit" class="btn btn-primary">حفظ</button>
            <a href="product-list.jsp" class="btn btn-success">قلئمة المنتجات</a>
        </form>
    </div>
</div>
</body>
</html>
