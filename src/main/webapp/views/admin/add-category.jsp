<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Danh Mục Mới</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="../topbar.jsp"></jsp:include>

    <div class="container mt-4">
        <div class="card shadow-sm mx-auto" style="max-width: 600px;">
            <div class="card-header bg-success text-white">
                <h4 class="mb-0"><i class="fas fa-folder-plus"></i> Thêm Danh Mục Mới</h4>
            </div>
            <div class="card-body">
                <c:if test="${not empty alert}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-triangle mr-2"></i>${alert}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/admin/category/add" method="post" enctype="multipart/form-data" class="needs-validation">
                    <div class="form-group">
                        <label for="name" class="font-weight-bold">Tên danh mục: <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Nhập tên danh mục..." 
                               value="${category.name}" maxlength="255" required />
                        <small class="form-text text-muted">Tối đa 255 ký tự.</small>
                    </div>
                    <div class="form-group">
                        <label for="icon" class="font-weight-bold">Ảnh đại diện:</label>
                        <input type="file" class="form-control-file" id="icon" name="icon" accept="image/png,image/jpeg,image/webp,image/gif" />
                        <small class="form-text text-muted">Hỗ trợ các định dạng: .jpg, .jpeg, .png, .webp, .gif</small>
                    </div>
                    <div class="mt-4 text-right">
                        <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-secondary mr-2">
                            <i class="fas fa-arrow-left"></i> Quay lại
                        </a>
                        <button type="reset" class="btn btn-warning mr-2"><i class="fas fa-undo"></i> Hủy</button>
                        <button type="submit" class="btn btn-success"><i class="fas fa-save"></i> Thêm mới</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

