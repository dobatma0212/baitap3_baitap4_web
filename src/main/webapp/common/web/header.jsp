<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
        <div class="container">
            <a class="navbar-brand font-weight-bold" href="${pageContext.request.contextPath}/home">
                <i class="fas fa-cubes text-primary mr-1"></i> HỆ THỐNG QUẢN LÝ
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarMain" 
                    aria-controls="navbarMain" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarMain">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">
                            <i class="fas fa-home"></i> Trang Chủ
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/product">
                            <i class="fas fa-boxes"></i> Sản Phẩm
                        </a>
                    </li>
                    <c:if test="${sessionScope.account != null and sessionScope.account.roleid == 1}">
                        <li class="nav-item">
                            <a class="nav-link text-warning" href="${pageContext.request.contextPath}/admin/home">
                                <i class="fas fa-user-shield"></i> Trang Quản Trị
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.account != null and sessionScope.account.roleid == 2}">
                        <li class="nav-item">
                            <a class="nav-link text-info" href="${pageContext.request.contextPath}/manager/home">
                                <i class="fas fa-tasks"></i> Quản Lý Cửa Hàng
                            </a>
                        </li>
                    </c:if>
                </ul>

                <ul class="navbar-nav ml-auto">
                    <c:choose>
                        <c:when test="${sessionScope.account == null}">
                            <li class="nav-item">
                                <a class="nav-link btn btn-outline-light btn-sm mr-2 px-3 text-white" href="${pageContext.request.contextPath}/login">
                                    <i class="fas fa-sign-in-alt"></i> Đăng Nhập
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link btn btn-primary btn-sm px-3 text-white" href="${pageContext.request.contextPath}/register">
                                    <i class="fas fa-user-plus"></i> Đăng Ký
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle text-white" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fas fa-user-circle"></i> <strong>${sessionScope.account.fullName}</strong>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                                    <span class="dropdown-item-text text-muted small">
                                        Quyền: 
                                        <c:choose>
                                            <c:when test="${sessionScope.account.roleid == 1}">Quản trị viên (Admin)</c:when>
                                            <c:when test="${sessionScope.account.roleid == 2}">Quản lý (Manager)</c:when>
                                            <c:otherwise>Khách hàng (User)</c:otherwise>
                                        </c:choose>
                                    </span>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/profile">
                                        <i class="fas fa-id-badge mr-1"></i> Hồ Sơ Cá Nhân
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                        <i class="fas fa-sign-out-alt"></i> Đăng Xuất
                                    </a>
                                </div>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</header>
