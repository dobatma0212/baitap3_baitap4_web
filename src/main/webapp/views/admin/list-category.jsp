<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Danh mục</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="../topbar.jsp"></jsp:include>

    <div class="container mt-4">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <h4 class="mb-0"><i class="fas fa-list"></i> Danh Sách Danh Mục</h4>
                <a href="${pageContext.request.contextPath}/admin/category/add" class="btn btn-light btn-sm font-weight-bold">
                    <i class="fas fa-plus-circle"></i> Thêm danh mục mới
                </a>
            </div>
            <div class="card-body">
                <!-- Thanh tìm kiếm -->
                <form action="${pageContext.request.contextPath}/admin/category/list" method="get" class="form-inline mb-3">
                    <input type="text" name="keyword" class="form-control mr-2" placeholder="Tìm theo tên danh mục..." value="${param.keyword}">
                    <button type="submit" class="btn btn-outline-primary"><i class="fas fa-search"></i> Tìm kiếm</button>
                    <c:if test="${not empty param.keyword}">
                        <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-link">Xem tất cả</a>
                    </c:if>
                </form>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover text-center align-middle">
                        <thead class="thead-dark">
                            <tr>
                                <th style="width: 80px;">STT</th>
                                <th style="width: 150px;">Hình ảnh</th>
                                <th>Tên danh mục</th>
                                <th style="width: 180px;">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty cateList}">
                                    <c:forEach items="${cateList}" var="cate" varStatus="STT">
                                        <tr>
                                            <td class="align-middle">${STT.index + 1}</td>
                                            <td class="align-middle">
                                                <c:choose>
                                                    <c:when test="${not empty cate.icon}">
                                                        <c:url value="/image?fname=${cate.icon}" var="imgUrl" />
                                                        <img src="${imgUrl}" alt="${cate.name}" class="img-thumbnail" style="max-height: 80px; max-width: 120px; object-fit: cover;" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted"><i class="fas fa-image fa-2x"></i><br>Không có ảnh</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="align-middle font-weight-bold text-left pl-4">${cate.name}</td>
                                            <td class="align-middle">
                                                <a href="<c:url value='/admin/category/edit?id=${cate.id}'/>" class="btn btn-warning btn-sm mr-1">
                                                    <i class="fas fa-edit"></i> Sửa
                                                </a>
                                                <a href="<c:url value='/admin/category/delete?id=${cate.id}'/>" 
                                                   class="btn btn-danger btn-sm"
                                                   onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này không?');">
                                                    <i class="fas fa-trash-alt"></i> Xóa
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="4" class="text-center text-muted py-4">Chưa có danh mục nào.</td>
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

