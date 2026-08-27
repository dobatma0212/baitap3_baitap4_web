<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh Sửa Danh Mục</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="../topbar.jsp"></jsp:include>

    <div class="container mt-4">
        <div class="card shadow-sm mx-auto" style="max-width: 600px;">
            <div class="card-header bg-warning text-dark font-weight-bold">
                <h4 class="mb-0"><i class="fas fa-edit"></i> Chỉnh Sửa Danh Mục</h4>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/admin/category/edit" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${category.id}" />

                    <div class="form-group">
                        <label for="name" class="font-weight-bold">Tên danh mục:</label>
                        <input type="text" class="form-control" id="name" name="name" value="${category.name}" required />
                    </div>

                    <div class="form-group">
                        <label class="font-weight-bold">Ảnh hiện tại:</label>
                        <div class="mb-2">
                            <c:choose>
                                <c:when test="${not empty category.icon}">
                                    <c:url value="/image?fname=${category.icon}" var="imgUrl" />
                                    <img src="${imgUrl}" alt="${category.name}" class="img-thumbnail" style="max-height: 120px; max-width: 180px; object-fit: cover;" />
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted"><i class="fas fa-image fa-2x"></i> Chưa có ảnh đại diện</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <label for="icon" class="font-weight-bold">Chọn ảnh mới (nếu muốn thay đổi):</label>
                        <input type="file" class="form-control-file" id="icon" name="icon" accept="image/*" />
                    </div>

                    <div class="mt-4 text-right">
                        <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-secondary mr-2">
                            <i class="fas fa-arrow-left"></i> Quay lại
                        </a>
                        <button type="reset" class="btn btn-outline-warning mr-2"><i class="fas fa-undo"></i> Reset</button>
                        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Cập nhật</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

