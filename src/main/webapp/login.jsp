
<%@ page contentType="text/html;charset=UTF-8" language="java" import="models.User" %>
<%
    User auth = (User) request.getSession().getAttribute("auth");
    if(auth != null){
        response.sendRedirect("index.jsp");
    }
%>
<html dir="rtl">
<head>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <title>تسجيل الدخول</title>
</head>
<body>
<div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed">
    <div
            class="position-relative overflow-hidden radial-gradient min-vh-100 d-flex align-items-center justify-content-center">
        <div class="d-flex align-items-center justify-content-center w-100">
            <div class="row justify-content-center w-100">
                <div class="col-md-8 col-lg-6 col-xxl-3">
                    <div class="card mb-0">
                        <div class="card-body">
                            <a href="index.jsp" class="text-nowrap logo-img text-center d-block py-3 w-100">
                                <img src="uploads/cart.png" width="80" height="80" alt="">
                            </a>
                            <p class="text-center h5">اهلا بك في ايزون</p>
                            <p class="text-center h5">تسجيل الدخول</p>
                            <form action="user-login" method="post">
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">البريد الالكتروني</label>
                                    <input type="email" name="email" class="form-control" id="exampleInputEmail1" required>
                                </div>
                                <div class="mb-4">
                                    <label for="exampleInputPassword1" class="form-label">كلمة المرور</label>
                                    <input type="password" name="password" class="form-control" id="exampleInputPassword1" required>
                                </div>
                                <div class="d-flex align-items-center justify-content-between mb-4">
                                    <div class="form-check">
                                        <input class="form-check-input primary" type="checkbox" value="" id="flexCheckChecked" checked>
                                        <label class="form-check-label text-dark" for="flexCheckChecked">
                                            تذكرني
                                        </label>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary w-100 py-8 fs-4 mb-4 rounded-2">تسجيل الدخول</button>
                                <div class="d-flex align-items-center justify-content-center">
                                    <p class="fs-4 mb-0 fw-bold">هل انت جديد؟</p>
                                    <a class="text-primary fw-bold ms-2" href="register.jsp">إنشاء حساب</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="assets/js/bootstrap.js"></script>
<script src="assets/js/jquery.js"></script>
</html>