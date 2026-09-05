<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${product.name} - Chi Tiết Sản Phẩm</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .product-detail-img-wrapper {
            position: relative;
            width: 100%;
            height: 420px;
            background-color: #f8f9fa;
            border-radius: 12px;
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #e9ecef;
        }
        .product-detail-img {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
            transition: transform 0.3s ease;
        }
        .product-detail-img:hover {
            transform: scale(1.03);
        }
        .related-card {
            transition: transform 0.2s ease, box-shadow 0.2s ease;
            border-radius: 8px;
            overflow: hidden;
            border: 1px solid #e9ecef;
        }
        .related-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 6px 15px rgba(0,0,0,0.1);
        }
        .related-img-wrapper {
            height: 180px;
            background-color: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }
        .related-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
    </style>
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="topbar.jsp"></jsp:include>

    <div class="container my-4">
        <!-- Breadcrumb định hướng -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb bg-white shadow-sm">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home"><i class="fas fa-home"></i> Trang Chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/product">Sản Phẩm</a></li>
                <c:if test="${product.category != null}">
                    <li class="breadcrumb-item">
                        <a href="${pageContext.request.contextPath}/product?cateId=${product.category.id}">${product.category.name}</a>
                    </li>
                </c:if>
                <li class="breadcrumb-item active text-truncate" aria-current="page" style="max-width: 300px;">
                    ${product.name}
                </li>
            </ol>
        </nav>

        <!-- Khối chi tiết sản phẩm chính -->
        <div class="card shadow-sm border-0 mb-4">
            <div class="card-body p-4">
                <div class="row">
                    <!-- Cột trái: Ảnh sản phẩm -->
                    <div class="col-lg-5 col-md-6 mb-4 mb-md-0">
                        <div class="product-detail-img-wrapper shadow-sm">
                            <c:choose>
                                <c:when test="${not empty product.images}">
                                    <c:url value="/image?fname=${product.images}" var="prodImgUrl" />
                                    <img src="${prodImgUrl}" alt="${product.name}" class="product-detail-img" />
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center text-muted">
                                        <i class="fas fa-image fa-5x mb-2 text-secondary"></i>
                                        <div>Chưa có hình ảnh</div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <!-- Cột phải: Thông tin sản phẩm & Đặt mua -->
                    <div class="col-lg-7 col-md-6">
                        <div class="mb-2">
                            <span class="badge badge-primary px-3 py-2" style="font-size: 0.85rem;">
                                <i class="fas fa-tag mr-1"></i>${product.category != null ? product.category.name : 'Danh mục chung'}
                            </span>
                            <span class="badge badge-light border ml-2 px-3 py-2 text-muted" style="font-size: 0.85rem;">
                                Mã SP: #${product.id}
                            </span>
                        </div>

                        <h2 class="font-weight-bold text-dark mb-3">${product.name}</h2>

                        <!-- Khung giá sản phẩm -->
                        <div class="bg-light p-3 rounded mb-3 border">
                            <div class="d-flex align-items-baseline">
                                <span class="text-danger font-weight-bold" style="font-size: 2.2rem;">
                                    <fmt:formatNumber value="${product.price}" pattern="#,###" />
                                </span>
                                <span class="text-danger font-weight-bold ml-1" style="font-size: 1.3rem;">VNĐ</span>
                            </div>
                        </div>

                        <!-- Thông tin tình trạng hàng -->
                        <div class="mb-3">
                            <div class="row mb-2">
                                <div class="col-4 font-weight-bold text-muted">Tình trạng:</div>
                                <div class="col-8">
                                    <c:choose>
                                        <c:when test="${product.quantity > 0}">
                                            <span class="badge badge-success px-2 py-1"><i class="fas fa-check-circle mr-1"></i>Còn hàng (còn ${product.quantity} sản phẩm)</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-secondary px-2 py-1"><i class="fas fa-times-circle mr-1"></i>Hết hàng</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <div class="row mb-2">
                                <div class="col-4 font-weight-bold text-muted">Trạng thái:</div>
                                <div class="col-8">
                                    <c:choose>
                                        <c:when test="${product.status == 1}">
                                            <span class="text-success"><i class="fas fa-circle mr-1 small"></i>Đang kinh doanh</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted"><i class="fas fa-pause mr-1 small"></i>Tạm ngừng kinh doanh</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>

                        <!-- Mô tả chi tiết -->
                        <div class="mb-4">
                            <h5 class="font-weight-bold text-dark mb-2"><i class="fas fa-info-circle mr-1 text-primary"></i> Mô tả sản phẩm:</h5>
                            <div class="p-3 bg-white rounded border" style="min-height: 90px; line-height: 1.6;">
                                <c:choose>
                                    <c:when test="${not empty product.description}">
                                        ${product.description}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted font-italic">Chưa có thông tin mô tả chi tiết cho sản phẩm này.</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Thao tác mua hàng -->
                        <div class="border-top pt-3">
                            <div class="form-row align-items-center mb-3">
                                <div class="col-auto">
                                    <label class="font-weight-bold mr-2 mb-0">Số lượng:</label>
                                </div>
                                <div class="col-auto">
                                    <input type="number" class="form-control text-center" value="1" min="1" max="${product.quantity > 0 ? product.quantity : 1}" style="width: 80px;" />
                                </div>
                            </div>

                            <div class="d-flex flex-wrap gap-2">
                                <button type="button" class="btn btn-outline-primary btn-lg font-weight-bold mr-2 mb-2">
                                    <i class="fas fa-cart-plus mr-2"></i>Thêm Vào Giỏ Hàng
                                </button>
                                <button type="button" class="btn btn-danger btn-lg font-weight-bold mr-2 mb-2">
                                    <i class="fas fa-bolt mr-2"></i>Mua Ngay
                                </button>
                                <a href="${pageContext.request.contextPath}/product" class="btn btn-outline-secondary btn-lg mb-2">
                                    <i class="fas fa-arrow-left mr-1"></i> Quay lại
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Khối sản phẩm cùng danh mục -->
        <c:if test="${not empty relatedProducts}">
            <div class="mt-5">
                <h4 class="font-weight-bold text-dark mb-3">
                    <i class="fas fa-layer-group text-primary mr-2"></i>Sản Phẩm Cùng Danh Mục
                </h4>
                <div class="row">
                    <c:forEach items="${relatedProducts}" var="rel">
                        <div class="col-lg-3 col-md-6 col-sm-6 mb-3">
                            <div class="card h-100 related-card shadow-sm bg-white">
                                <a href="${pageContext.request.contextPath}/product/detail?id=${rel.id}" class="text-decoration-none">
                                    <div class="related-img-wrapper">
                                        <c:choose>
                                            <c:when test="${not empty rel.images}">
                                                <c:url value="/image?fname=${rel.images}" var="relImgUrl" />
                                                <img src="${relImgUrl}" alt="${rel.name}" class="related-img" />
                                            </c:when>
                                            <c:otherwise>
                                                <div class="text-center text-muted">
                                                    <i class="fas fa-image fa-2x text-secondary"></i>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </a>
                                <div class="card-body p-3 d-flex flex-column">
                                    <h6 class="card-title font-weight-bold text-dark text-truncate mb-1">
                                        <a href="${pageContext.request.contextPath}/product/detail?id=${rel.id}" class="text-dark text-decoration-none" title="${rel.name}">
                                            ${rel.name}
                                        </a>
                                    </h6>
                                    <div class="mt-auto pt-2 d-flex justify-content-between align-items-center">
                                        <span class="text-danger font-weight-bold">
                                            <fmt:formatNumber value="${rel.price}" pattern="#,###" /> đ
                                        </span>
                                        <a href="${pageContext.request.contextPath}/product/detail?id=${rel.id}" class="btn btn-outline-primary btn-sm">
                                            Xem
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </div>
</body>
</html>

