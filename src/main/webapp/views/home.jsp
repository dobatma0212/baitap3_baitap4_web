<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - Cửa Hàng & Sản Phẩm Mới</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .product-card {
            transition: transform 0.25s ease, box-shadow 0.25s ease;
            border-radius: 10px;
            overflow: hidden;
            border: 1px solid #e9ecef;
        }
        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.12);
        }
        .product-img-wrapper {
            position: relative;
            width: 100%;
            height: 210px;
            background-color: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }
        .product-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.3s ease;
        }
        .product-card:hover .product-img {
            transform: scale(1.05);
        }
        .badge-new {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: #e74c3c;
            color: #fff;
            padding: 5px 10px;
            border-radius: 15px;
            font-size: 11px;
            font-weight: bold;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
            z-index: 2;
        }
    </style>
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="topbar.jsp"></jsp:include>

    <div class="container my-4">
        <!-- Banner chào mừng -->
        <div class="jumbotron text-center bg-white shadow-sm rounded py-4 mb-4 border">
            <h2 class="text-primary font-weight-bold"><i class="fas fa-store-alt mr-2"></i>Chào Mừng Đến Với Cửa Hàng Trực Tuyến</h2>
            <p class="lead text-muted mb-2">Trải nghiệm mua sắm tiện lợi với các bộ sưu tập thời trang và sản phẩm mới nhất.</p>
            <c:if test="${sessionScope.account != null}">
                <div class="alert alert-info d-inline-block py-1 px-3 mb-0">
                    <i class="fas fa-user mr-1"></i> Xin chào, <strong>${sessionScope.account.fullName}</strong>!
                </div>
            </c:if>
        </div>

        <!-- Section: 10 Sản Phẩm Mới Nhất -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <div>
                <h3 class="font-weight-bold text-dark mb-0">
                    <i class="fas fa-fire text-danger mr-2"></i>10 Sản Phẩm Mới Nhất
                </h3>
                <small class="text-muted">Các mặt hàng vừa mới cập bến và có sẵn tại cửa hàng</small>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/product" class="btn btn-outline-danger btn-sm font-weight-bold mr-2">
                    <i class="fas fa-th mr-1"></i> Xem tất cả sản phẩm
                </a>
                <c:if test="${sessionScope.account != null and sessionScope.account.roleid == 1}">
                    <a href="${pageContext.request.contextPath}/admin/product/list" class="btn btn-outline-primary btn-sm">
                        <i class="fas fa-cog mr-1"></i> Quản lý sản phẩm
                    </a>
                </c:if>
            </div>
        </div>

        <!-- Danh sách sản phẩm dạng Grid -->
        <div class="row">
            <c:choose>
                <c:when test="${not empty top10Products}">
                    <c:forEach items="${top10Products}" var="prod">
                        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 mb-4">
                            <div class="card h-100 product-card shadow-sm bg-white">
                                <!-- Khối ảnh sản phẩm -->
                                <a href="${pageContext.request.contextPath}/product/detail?id=${prod.id}" class="text-decoration-none">
                                    <div class="product-img-wrapper">
                                        <span class="badge-new"><i class="fas fa-bolt"></i> MỚI</span>
                                        <c:choose>
                                            <c:when test="${not empty prod.images}">
                                                <c:url value="/image?fname=${prod.images}" var="prodImgUrl" />
                                                <img src="${prodImgUrl}" alt="${prod.name}" class="product-img" />
                                            </c:when>
                                            <c:otherwise>
                                                <div class="text-center text-muted">
                                                    <i class="fas fa-image fa-3x mb-1 text-secondary"></i>
                                                    <div class="small">Chưa có ảnh</div>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </a>

                                <!-- Nội dung thông tin sản phẩm -->
                                <div class="card-body d-flex flex-column p-3">
                                    <div class="mb-1">
                                        <span class="badge badge-light border text-primary">
                                            <i class="fas fa-tag mr-1"></i>${prod.category != null ? prod.category.name : 'Danh mục chung'}
                                        </span>
                                    </div>

                                    <h5 class="card-title font-weight-bold text-dark text-truncate mb-1" title="${prod.name}">
                                        <a href="${pageContext.request.contextPath}/product/detail?id=${prod.id}" class="text-dark text-decoration-none">
                                            ${prod.name}
                                        </a>
                                    </h5>

                                    <c:if test="${not empty prod.description}">
                                        <p class="card-text text-muted small text-truncate mb-2" title="${prod.description}">
                                            ${prod.description}
                                        </p>
                                    </c:if>

                                    <div class="mt-auto pt-2 border-top d-flex justify-content-between align-items-center">
                                        <div class="text-danger font-weight-bold" style="font-size: 1.15rem;">
                                            <fmt:formatNumber value="${prod.price}" pattern="#,###" /> đ
                                        </div>
                                        <div>
                                            <c:choose>
                                                <c:when test="${prod.quantity > 0}">
                                                    <span class="badge badge-success small">Còn hàng</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-secondary small">Hết hàng</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <!-- Nút thao tác -->
                                <div class="card-footer bg-white border-0 pt-0 pb-3 px-3">
                                    <a href="${pageContext.request.contextPath}/product/detail?id=${prod.id}" class="btn btn-outline-primary btn-block btn-sm font-weight-bold">
                                        <i class="fas fa-eye mr-1"></i> Xem chi tiết
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-12 text-center py-5">
                        <div class="card shadow-sm p-4 mx-auto" style="max-width: 500px;">
                            <i class="fas fa-box-open fa-3x text-muted mb-3"></i>
                            <h5 class="text-muted">Chưa có sản phẩm nào được hiển thị</h5>
                            <p class="text-muted small">Hãy thêm sản phẩm mới trong trang quản trị để hiển thị tại đây.</p>
                            <c:if test="${sessionScope.account != null and sessionScope.account.roleid == 1}">
                                <a href="${pageContext.request.contextPath}/admin/product/add" class="btn btn-primary btn-sm mx-auto">
                                    <i class="fas fa-plus-circle mr-1"></i> Thêm sản phẩm ngay
                                </a>
                            </c:if>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>
