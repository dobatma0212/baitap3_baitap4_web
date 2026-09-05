<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Sản Phẩm</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="../topbar.jsp"></jsp:include>

    <div class="container-fluid px-4 mt-4">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <h4 class="mb-0"><i class="fas fa-box-open"></i> Danh Sách Sản Phẩm</h4>
                <a href="${pageContext.request.contextPath}/admin/product/add" class="btn btn-light btn-sm font-weight-bold">
                    <i class="fas fa-plus-circle"></i> Thêm sản phẩm mới
                </a>
            </div>
            <div class="card-body">
                <!-- Thanh tìm kiếm & lọc danh mục -->
                <form action="${pageContext.request.contextPath}/admin/product/list" method="get" class="form-inline mb-3">
                    <div class="form-group mr-2">
                        <input type="text" name="keyword" class="form-control" placeholder="Tìm theo tên sản phẩm..." value="${param.keyword}">
                    </div>
                    <div class="form-group mr-2">
                        <select name="cateId" class="form-control">
                            <option value="">-- Tất cả danh mục --</option>
                            <c:forEach items="${cateList}" var="c">
                                <option value="${c.id}" ${param.cateId == c.id ? 'selected' : ''}>${c.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-outline-primary mr-2"><i class="fas fa-search"></i> Lọc / Tìm</button>
                    <c:if test="${not empty param.keyword || not empty param.cateId}">
                        <a href="${pageContext.request.contextPath}/admin/product/list" class="btn btn-link">Xem tất cả</a>
                    </c:if>
                </form>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover text-center align-middle">
                        <thead class="thead-dark">
                            <tr>
                                <th style="width: 60px;">STT</th>
                                <th style="width: 120px;">Hình ảnh</th>
                                <th>Tên sản phẩm</th>
                                <th style="width: 160px;">Danh mục</th>
                                <th style="width: 130px;">Giá bán</th>
                                <th style="width: 90px;">Số lượng</th>
                                <th style="width: 140px;">Trạng thái</th>
                                <th style="width: 160px;">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty productList}">
                                    <c:forEach items="${productList}" var="prod" varStatus="STT">
                                        <tr>
                                            <td class="align-middle">${STT.index + 1}</td>
                                            <td class="align-middle">
                                                <c:choose>
                                                    <c:when test="${not empty prod.images}">
                                                        <c:url value="/image?fname=${prod.images}" var="imgUrl" />
                                                        <img src="${imgUrl}" alt="${prod.name}" class="img-thumbnail" style="max-height: 70px; max-width: 100px; object-fit: cover;" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted"><i class="fas fa-image fa-2x"></i><br>Không ảnh</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="align-middle font-weight-bold text-left pl-3">
                                                <div>${prod.name}</div>
                                                <c:if test="${not empty prod.description}">
                                                    <small class="text-muted text-truncate d-inline-block" style="max-width: 300px;">${prod.description}</small>
                                                </c:if>
                                            </td>
                                            <td class="align-middle">
                                                <span class="badge badge-info p-2">${prod.category != null ? prod.category.name : 'Chưa phân loại'}</span>
                                            </td>
                                            <td class="align-middle text-danger font-weight-bold">
                                                <fmt:formatNumber value="${prod.price}" pattern="#,###" /> đ
                                            </td>
                                            <td class="align-middle">
                                                <span class="badge ${prod.quantity > 0 ? 'badge-light border' : 'badge-danger'} p-2">${prod.quantity}</span>
                                            </td>
                                            <td class="align-middle">
                                                <c:choose>
                                                    <c:when test="${prod.status == 1}">
                                                        <span class="badge badge-success p-2"><i class="fas fa-check-circle"></i> Đang bán</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-secondary p-2"><i class="fas fa-pause-circle"></i> Tạm ngừng</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="align-middle">
                                                <a href="<c:url value='/admin/product/edit?id=${prod.id}'/>" class="btn btn-warning btn-sm mr-1" title="Chỉnh sửa">
                                                    <i class="fas fa-edit"></i> Sửa
                                                </a>
                                                <a href="<c:url value='/admin/product/delete?id=${prod.id}'/>" 
                                                   class="btn btn-danger btn-sm"
                                                   title="Xóa"
                                                   onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm \'${prod.name}\' không?');">
                                                    <i class="fas fa-trash-alt"></i> Xóa
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="8" class="text-center text-muted py-4">Chưa có sản phẩm nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

