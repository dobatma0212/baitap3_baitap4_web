<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-danger shadow-sm">
        <div class="container-fluid">
            <a class="navbar-brand font-weight-bold" href="${pageContext.request.contextPath}/admin/home">
                <i class="fas fa-user-shield mr-1"></i> ADMIN PORTAL
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#adminNavbar" 
                    aria-controls="adminNavbar" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="adminNavbar">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/home">
                            <i class="fas fa-tachometer-alt"></i> Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/category/list">
                            <i class="fas fa-boxes"></i> Quản Lý Danh Mục
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/product/list">
                            <i class="fas fa-box-open"></i> Quản Lý Sản Phẩm
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">
                            <i class="fas fa-globe"></i> Xem Trang Khách
                        </a>
                    </li>
                </ul>

                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white font-weight-bold" href="#" id="adminUserDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-user-circle"></i> ${sessionScope.account.fullName} (Admin)
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="adminUserDropdown">
                            <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                <i class="fas fa-sign-out-alt"></i> Đăng Xuất
                            </a>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
