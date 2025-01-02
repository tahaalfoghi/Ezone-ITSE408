<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Product" %>
<%@ page import="services.IProductService" %>
<%@ page import="services.ProductService" %>
<%@ page import="models.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    IProductService productService = new ProductService();
    ArrayList<Product> products = productService.getAllProducts();

    String successMessage = (String) session.getAttribute("successMessage");
    String errorMessage = (String) session.getAttribute("errorMessage");

    session.removeAttribute("successMessage");
    session.removeAttribute("errorMessage");
%>
<!DOCTYPE html>
<html dir="rtl" lang="ar">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>إدارة المنتجات</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
        }
        .product-management-container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            padding: 30px;
            margin-top: 30px;
        }
        .product-image {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 10px;
            transition: transform 0.3s ease;
        }
        .product-image:hover {
            transform: scale(1.1);
        }
        .table-hover tbody tr:hover {
            background-color: rgba(0,123,255,0.1);
            transition: background-color 0.3s ease;
        }
        .btn-action {
            margin: 0 5px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }
        .page-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 10px 10px 0 0;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%@include file="navbar.jsp" %>

<div class="container product-management-container">
    <div class="page-header">
        <div class="row align-items-center">
            <div class="col">
                <h2 class="mb-0">
                    <i class="fas fa-boxes-stacked me-2"></i>إدارة المنتجات
                </h2>
            </div>
            <div class="col-auto">
                <a href="create-product.jsp" class="btn btn-light">
                    <i class="fas fa-plus me-1"></i>إضافة منتج جديد
                </a>
            </div>
        </div>
    </div>

    <div class="table-responsive">
        <table id="productsTable" class="table table-hover table-striped">
            <thead class="table-light">
            <tr>
                <th>صورة المنتج</th>
                <th>اسم المنتج</th>
                <th>التصنيف</th>
                <th>السعر</th>
                <th class="text-center">العمليات</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (products != null && !products.isEmpty()) {
                    for (Product product : products) {
            %>
            <tr>
                <td>
                    <img src="uploads/<%= product.getImage() %>"
                         class="product-image"
                         alt="صورة المنتج">
                </td>
                <td><%= product.getName() %></td>
                <td><%= product.getCategory() %></td>
                <td><%= String.format("%.2f", product.getPrice()) %> دينار</td>
                <td class="text-center">
                    <div class="btn-group" role="group">
                        <a href="update-product.jsp?id=<%= product.getId() %>"
                           class="btn btn-sm btn-warning btn-action">
                            <i class="fas fa-edit me-1"></i>تعديل
                        </a>
                        <a href="delete-product?id=<%= product.getId() %>"
                           class="btn btn-sm btn-danger btn-action delete-product"
                           data-product-name="<%= product.getName() %>">
                            <i class="fas fa-trash me-1"></i>حذف
                        </a>
                    </div>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5" class="text-center text-muted">
                    <i class="fas fa-box-open fa-3x mb-3"></i>
                    <p>لا توجد منتجات لعرضها حاليًا</p>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    $(document).ready(function() {

        $('#productsTable').DataTable({
            language: {
                url: '//cdn.datatables.net/plug-ins/1.11.5/i18n/ar.json'
            },
            pageLength: 10,
            lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "الكل"]]
        });

        // Delete product confirmation
        $('.delete-product').on('click', function(e) {
            e.preventDefault();
            const productName = $(this).data('product-name');
            const deleteUrl = $(this).attr('href');

            Swal.fire({
                title: 'تأكيد الحذف',
                text: `هل أنت متأكد من حذف المنتج "${productName}"؟`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'نعم، احذف',
                cancelButtonText: 'إلغاء'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = deleteUrl;
                }
            });
        });
    });
</script>
</body>
</html>
