<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Base64" %>
<html dir="rtl">
<head>
    <title>إضافة منتج</title>
   <link rel="stylesheet" href="assets/css/bootstrap.min.css" >
   <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css" >
   <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>

<%@include file="navbar.jsp" %>
<body>
<div class="container mt-5">
  <h2>إضافة منتج</h2>
  <div class="card-body mt-3">
    <form  action="create-product" method="post" enctype="multipart/form-data">
      <div class="mb-3">
        <label class="form-label">إسم المنتج</label>
        <input type="text" class="form-control" name="product-name" required>
      </div>
      <div class="mb-3">
        <label  class="form-label">التصنيف</label>
        <input type="text" name="category" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">السعر</label>
        <input type="number" name="price" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">صورة المنتج</label>
        <input type="file" name="image" class="form-control" required>
      </div>
      <button type="submit" class="btn btn-primary">حفظ</button>
      <a href="product-list.jsp" class="btn btn-success">قلئمة المنتجات</a>
    </form>
  </div>
</div>

</body>
</html>
