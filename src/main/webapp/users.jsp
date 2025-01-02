<%@ page import="services.IUserService" %>
<%@ page import="services.UserService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    IUserService userService =  new UserService();
    ArrayList<User> users =  userService.getAllUsers();
%>
<!DOCTYPE html>
<html dir="rtl" lang="ar">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>إدارة المستخدمين</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
            direction: rtl;
        }
        .users-container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            padding: 20px;
            margin-top: 30px;
        }
        .table-users {
            width: 100%;
            margin-bottom: 0;
        }
        .table-users th {
            background-color: #007bff;
            color: white;
            text-align: center;
        }
        .table-users td {
            text-align: center;
            vertical-align: middle;
        }
        .table-users tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .table-users tr:hover {
            background-color: #e9ecef;
        }
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%@include file="navbar.jsp"%>
<div class="container users-container">
    <div class="header-container">
        <h1 class="mb-0">
            <i class="fas fa-users me-2"></i>قائمة المستخدمين
        </h1>
        <a href="create-user.jsp" class="btn btn-primary">
            <i class="fas fa-user-plus me-2"></i>إضافة مستخدم جديد
        </a>
    </div>

    <% if (users != null && !users.isEmpty()) { %>
    <div class="table-responsive">
        <table class="table table-striped table-hover table-users">
            <thead>
                <tr>
                    <th>رقم المستخدم</th>
                    <th>الاسم</th>
                    <th>البريد الإلكتروني</th>
                    <th>الإجراءات</th>
                </tr>
            </thead>
            <tbody>
                <% for (User user : users) { %>
                <tr>
                    <td><%= user.getId() %></td>
                    <td><%= user.getName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td>
                        <div class="btn-group" role="group">
                            <a href="update-user?id=<%= user.getId() %>" class="btn btn-sm btn-warning me-2">
                                <i class="fas fa-edit"></i>
                            </a>
                            <a href="delete-user?id=<%= user.getId() %>" class="btn btn-sm btn-danger delete-user">
                                <i class="fas fa-trash"></i>
                            </a>
                        </div>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <% } else { %>
    <div class="alert alert-info text-center" role="alert">
        لا توجد مستخدمين حالياً
    </div>
    <% } %>
</div>

<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const deleteButtons = document.querySelectorAll('.delete-user');
        deleteButtons.forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                const deleteUrl = this.getAttribute('href');
                
                Swal.fire({
                    title: 'هل أنت متأكد؟',
                    text: 'لن تتمكن من استعادة هذا المستخدم!',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'نعم، احذف!',
                    cancelButtonText: 'إلغاء'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = deleteUrl;
                    }
                });
            });
        });
    });
</script>
</body>
</html>
