<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tất Cả Sản Phẩm - Cửa Hàng Trực Tuyến</title>
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
            height: 220px;
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
        .pagination .page-item.active .page-link {
            background-color: #007bff;
            border-color: #007bff;
        }
        .filter-pill {
            border-radius: 20px;
            padding: 6px 16px;
            font-weight: 500;
            margin-right: 8px;
            margin-bottom: 8px;
            display: inline-block;
            text-decoration: none;
            transition: all 0.2s;
        }
        .filter-pill:hover {
            text-decoration: none;
        }
    </style>
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="topbar.jsp"></jsp:include>

    <div class="container my-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb bg-white shadow-sm">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home"><i class="fas fa-home"></i> Trang Chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Tất Cả Sản Phẩm</li>
            </ol>
        </nav>

        <!-- Thanh tiêu đề & tìm kiếm -->
        <div class="card shadow-sm mb-4 border-0">
            <div class="card-body">
                <div class="row align-items-center">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <h3 class="font-weight-bold text-dark mb-1">
                            <i class="fas fa-boxes text-primary mr-2"></i>Tất Cả Sản Phẩm
                        </h3>
                        <p class="text-muted mb-0">
                            Hiển thị <strong>6 sản phẩm / trang</strong> (Tổng cộng <strong>${totalProducts}</strong> sản phẩm)
                        </p>
                    </div>
                    <div class="col-md-6">
                        <form action="${pageContext.request.contextPath}/product" method="get" class="form-inline justify-content-md-end">
                            <c:if test="${not empty cateId}">
                                <input type="hidden" name="cateId" value="${cateId}">
                            </c:if>
                            <div class="input-group w-100" style="max-width: 360px;">
                                <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm sản phẩm..." value="${keyword}">
                                <div class="input-group-append">
                                    <button class="btn btn-primary" type="submit">
                                        <i class="fas fa-search"></i>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <hr class="my-3">

                <!-- Bộ lọc theo danh mục -->
                <div class="d-flex flex-wrap align-items-center">
                    <span class="font-weight-bold text-muted mr-3 mb-2"><i class="fas fa-filter"></i> Danh mục:</span>
                    <a href="${pageContext.request.contextPath}/product<c:if test="${not empty keyword}">?keyword=${keyword}</c:if>" 
                       class="filter-pill ${empty cateId ? 'btn btn-primary text-white' : 'btn btn-outline-secondary'}">
                        Tất cả
                    </a>
                    <c:forEach items="${cateList}" var="c">
                        <a href="${pageContext.request.contextPath}/product?cateId=${c.id}<c:if test="${not empty keyword}">&keyword=${keyword}</c:if>" 
                           class="filter-pill ${cateId == c.id ? 'btn btn-primary text-white' : 'btn btn-outline-secondary'}">
                            ${c.name}
                        </a>
                    </c:forEach>
                    <c:if test="${not empty keyword || not empty cateId}">
                        <a href="${pageContext.request.contextPath}/product" class="btn btn-link text-danger mb-2 font-weight-bold">
                            <i class="fas fa-times-circle"></i> Xóa bộ lọc
                        </a>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- Lưới sản phẩm (6 sản phẩm / trang) -->
        <div class="row">
            <c:choose>
                <c:when test="${not empty productList}">
                    <c:forEach items="${productList}" var="prod">
                        <div class="col-lg-4 col-md-6 col-sm-6 mb-4">
                            <div class="card h-100 product-card shadow-sm bg-white">
                                <!-- Khung hình ảnh -->
                                <a href="${pageContext.request.contextPath}/product/detail?id=${prod.id}" class="text-decoration-none">
                                    <div class="product-img-wrapper">
                                        <c:choose>
                                            <c:when test="${not empty prod.images}">
                                                <c:url value="/image?fname=${prod.images}" var="prodImgUrl" />
                                                <img src="${prodImgUrl}" alt="${prod.name}" class="product-img" />
                                            </c:when>
                                            <c:otherwise>
                                                <div class="text-center text-muted">
                                                    <i class="fas fa-image fa-3x mb-1 text-secondary"></i>
                                                    <div class="small">Chưa có hình</div>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </a>

                                <!-- Thân thẻ thông tin -->
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
                                        <div class="text-danger font-weight-bold" style="font-size: 1.2rem;">
                                            <fmt:formatNumber value="${prod.price}" pattern="#,###" /> đ
                                        </div>
                                        <div>
                                            <c:choose>
                                                <c:when test="${prod.quantity > 0}">
                                                    <span class="badge badge-success">Còn ${prod.quantity}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-secondary">Hết hàng</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <!-- Nút mua / Xem chi tiết -->
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
                            <i class="fas fa-search fa-3x text-muted mb-3"></i>
                            <h5 class="text-muted">Không tìm thấy sản phẩm phù hợp</h5>
                            <p class="text-muted small">Hãy thử tìm với từ khóa khác hoặc bỏ chọn bộ lọc danh mục.</p>
                            <a href="${pageContext.request.contextPath}/product" class="btn btn-outline-primary btn-sm mx-auto">
                                <i class="fas fa-sync mr-1"></i> Xem tất cả sản phẩm
                            </a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Phân trang (Pagination) -->
        <c:if test="${totalPages > 1}">
            <!-- Xây dựng chuỗi tham số phụ khi chuyển trang -->
            <c:set var="queryParams" value="" />
            <c:if test="${not empty keyword}">
                <c:set var="queryParams" value="${queryParams}&keyword=${keyword}" />
            </c:if>
            <c:if test="${not empty cateId}">
                <c:set var="queryParams" value="${queryParams}&cateId=${cateId}" />
            </c:if>

            <nav aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">
                    <!-- Nút Trang Đầu -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/product?page=1${queryParams}" title="Trang đầu">
                            <i class="fas fa-angle-double-left"></i>
                        </a>
                    </li>

                    <!-- Nút Trang Trước -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/product?page=${currentPage - 1}${queryParams}">
                            Trước
                        </a>
                    </li>

                    <!-- Các số trang -->
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/product?page=${i}${queryParams}">
                                ${i}
                            </a>
                        </li>
                    </c:forEach>

                    <!-- Nút Trang Sau -->
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/product?page=${currentPage + 1}${queryParams}">
                            Sau
                        </a>
                    </li>

                    <!-- Nút Trang Cuối -->
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/product?page=${totalPages}${queryParams}" title="Trang cuối">
                            <i class="fas fa-angle-double-right"></i>
                        </a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>
</body>
</html>

