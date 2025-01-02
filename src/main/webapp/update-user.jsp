<%@ page import="models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html dir="rtl" lang="ar">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>تحديث مستخدم</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
            direction: rtl;
        }
        .update-user-container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            padding: 30px;
            margin-top: 50px;
        }
    </style>
</head>
<body>
<%@include file="navbar.jsp"%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 update-user-container">
            <h2 class="text-center mb-4">
                <i class="fas fa-user-edit me-2"></i>تحديث المستخدم
            </h2>
            <%
                User user = (User) request.getAttribute("user");
                if (user == null) {
                    response.sendRedirect("users.jsp");
                    return;
                }
            %>
            <form action="update-user" method="post">
                <input type="hidden" name="id" value="<%= user.getId() %>">
                <div class="mb-3">
                    <label for="username" class="form-label">اسم المستخدم</label>
                    <input type="text" class="form-control" id="username" name="username" 
                           value="<%= user.getName() %>" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">البريد الإلكتروني</label>
                    <input type="email" class="form-control" id="email" name="email" 
                           value="<%= user.getEmail() %>" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label"> كلمة المرور</label>
                    <input type="password" class="form-control" id="pass" name="password"
                           value="<%= user.getPassword() %>" required>
                </div>
                <div class="mb-3">
                    <label for="role" class="form-label">الدور</label>
                    <select class="form-select" id="role" name="role" required>
                        <option value="user" <%= user.getRole().equals("user") ? "selected" : "" %>>مستخدم عادي</option>
                        <option value="admin" <%= user.getRole().equals("admin") ? "selected" : "" %>>مسؤول</option>
                    </select>
                </div>
                <div class="d-grid">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-2"></i>حفظ التحديثات
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>
