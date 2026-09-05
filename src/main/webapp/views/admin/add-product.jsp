<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Sản Phẩm Mới</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="../topbar.jsp"></jsp:include>

    <div class="container mt-4 mb-5">
        <div class="card shadow-sm mx-auto" style="max-width: 700px;">
            <div class="card-header bg-success text-white">
                <h4 class="mb-0"><i class="fas fa-plus-circle"></i> Thêm Sản Phẩm Mới</h4>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/admin/product/add" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="name" class="font-weight-bold">Tên sản phẩm: <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Nhập tên sản phẩm..." required />
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="cateId" class="font-weight-bold">Danh mục: <span class="text-danger">*</span></label>
                            <select class="form-control" id="cateId" name="cateId" required>
                                <option value="">-- Chọn danh mục --</option>
                                <c:forEach items="${cateList}" var="c">
                                    <option value="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="price" class="font-weight-bold">Giá bán (VNĐ): <span class="text-danger">*</span></label>
                            <input type="number" step="any" min="0" class="form-control" id="price" name="price" placeholder="Ví dụ: 250000" required />
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="quantity" class="font-weight-bold">Số lượng trong kho:</label>
                            <input type="number" min="0" class="form-control" id="quantity" name="quantity" value="0" />
                        </div>
                        <div class="form-group col-md-6">
                            <label for="status" class="font-weight-bold">Trạng thái:</label>
                            <select class="form-control" id="status" name="status">
                                <option value="1" selected>Đang kinh doanh</option>
                                <option value="0">Tạm ngừng kinh doanh</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="font-weight-bold">Mô tả sản phẩm:</label>
                        <textarea class="form-control" id="description" name="description" rows="3" placeholder="Nhập mô tả chi tiết sản phẩm..."></textarea>
                    </div>

                    <div class="form-group">
                        <label for="image" class="font-weight-bold">Hình ảnh sản phẩm:</label>
                        <input type="file" class="form-control-file" id="image" name="image" accept="image/*" />
                        <small class="form-text text-muted">Hỗ trợ các định dạng .jpg, .jpeg, .png, .webp.</small>
                    </div>

                    <div class="mt-4 text-right">
                        <a href="${pageContext.request.contextPath}/admin/product/list" class="btn btn-secondary mr-2">
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

