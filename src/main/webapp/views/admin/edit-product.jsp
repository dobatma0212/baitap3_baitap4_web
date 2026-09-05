<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh Sửa Sản Phẩm</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body style="background-color: #f8f9fa;">
    <jsp:include page="../topbar.jsp"></jsp:include>

    <div class="container mt-4 mb-5">
        <div class="card shadow-sm mx-auto" style="max-width: 700px;">
            <div class="card-header bg-warning text-dark font-weight-bold">
                <h4 class="mb-0"><i class="fas fa-edit"></i> Chỉnh Sửa Sản Phẩm</h4>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/admin/product/edit" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${product.id}" />

                    <div class="form-group">
                        <label for="name" class="font-weight-bold">Tên sản phẩm: <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="name" name="name" value="${product.name}" required />
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="cateId" class="font-weight-bold">Danh mục: <span class="text-danger">*</span></label>
                            <select class="form-control" id="cateId" name="cateId" required>
                                <option value="">-- Chọn danh mục --</option>
                                <c:forEach items="${cateList}" var="c">
                                    <option value="${c.id}" ${product.category != null && product.category.id == c.id ? 'selected' : ''}>${c.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="price" class="font-weight-bold">Giá bán (VNĐ): <span class="text-danger">*</span></label>
                            <input type="number" step="any" min="0" class="form-control" id="price" name="price" value="${product.price}" required />
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="quantity" class="font-weight-bold">Số lượng trong kho:</label>
                            <input type="number" min="0" class="form-control" id="quantity" name="quantity" value="${product.quantity}" />
                        </div>
                        <div class="form-group col-md-6">
                            <label for="status" class="font-weight-bold">Trạng thái:</label>
                            <select class="form-control" id="status" name="status">
                                <option value="1" ${product.status == 1 ? 'selected' : ''}>Đang kinh doanh</option>
                                <option value="0" ${product.status == 0 ? 'selected' : ''}>Tạm ngừng kinh doanh</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="font-weight-bold">Mô tả sản phẩm:</label>
                        <textarea class="form-control" id="description" name="description" rows="3">${product.description}</textarea>
                    </div>

                    <div class="form-group">
                        <label class="font-weight-bold">Hình ảnh hiện tại:</label>
                        <div class="mb-2">
                            <c:choose>
                                <c:when test="${not empty product.images}">
                                    <c:url value="/image?fname=${product.images}" var="imgUrl" />
                                    <img src="${imgUrl}" alt="${product.name}" class="img-thumbnail" style="max-height: 140px; max-width: 180px; object-fit: cover;" />
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted"><i class="fas fa-image fa-2x"></i> Chưa có ảnh sản phẩm</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <label for="image" class="font-weight-bold">Chọn ảnh mới (nếu muốn thay đổi):</label>
                        <input type="file" class="form-control-file" id="image" name="image" accept="image/*" />
                        <small class="form-text text-muted">Nếu không chọn ảnh mới, hệ thống sẽ giữ lại ảnh hiện tại.</small>
                    </div>

                    <div class="mt-4 text-right">
                        <a href="${pageContext.request.contextPath}/admin/product/list" class="btn btn-secondary mr-2">
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

