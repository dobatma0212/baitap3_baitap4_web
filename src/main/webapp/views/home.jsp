<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - Khách Hàng / Người Dùng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="topbar.jsp"></jsp:include>

    <div class="container mt-5">
        <div class="jumbotron text-center bg-white shadow-sm rounded">
            <h1 class="display-4 text-primary">Chào Mừng Đến Với Hệ Thống!</h1>
            <p class="lead">Giao diện dành cho người dùng / khách hàng.</p>
            <hr class="my-4">
            <c:if test="${sessionScope.account != null}">
                <div class="card mx-auto" style="max-width: 400px;">
                    <div class="card-body">
                        <h5 class="card-title">Thông tin tài khoản</h5>
                        <p class="card-text"><strong>Username:</strong> ${sessionScope.account.userName}</p>
                        <p class="card-text"><strong>Họ tên:</strong> ${sessionScope.account.fullName}</p>
                        <p class="card-text"><strong>Email:</strong> ${sessionScope.account.email}</p>
                        <p class="card-text"><strong>Số điện thoại:</strong> ${sessionScope.account.phone}</p>
                        <p class="card-text"><strong>Role ID:</strong> ${sessionScope.account.roleid}</p>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>

