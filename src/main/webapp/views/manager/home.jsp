<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Quản Lý - Manager</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="../topbar.jsp"></jsp:include>

    <div class="container mt-5">
        <div class="jumbotron text-center bg-white shadow-sm rounded border-warning">
            <h1 class="display-4 text-warning"><i class="fas fa-user-cog"></i> Trang Quản Lý (Manager)</h1>
            <p class="lead">Bạn đang đăng nhập với quyền Quản lý (Role ID = 2).</p>
            <hr class="my-4">
            <c:if test="${sessionScope.account != null}">
                <div class="card mx-auto" style="max-width: 450px;">
                    <div class="card-header bg-warning text-dark">
                        <strong>Hồ sơ Quản Lý</strong>
                    </div>
                    <div class="card-body text-left">
                        <p><strong>Username:</strong> ${sessionScope.account.userName}</p>
                        <p><strong>Họ tên:</strong> ${sessionScope.account.fullName}</p>
                        <p><strong>Email:</strong> ${sessionScope.account.email}</p>
                        <p><strong>Số điện thoại:</strong> ${sessionScope.account.phone}</p>
                        <p><strong>Role ID:</strong> ${sessionScope.account.roleid} (Manager)</p>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>

