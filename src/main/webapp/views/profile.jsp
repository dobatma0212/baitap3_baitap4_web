<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hồ Sơ Cá Nhân - Thông Tin Tài Khoản</title>
    <style>
        .profile-card {
            max-width: 650px;
            margin: 30px auto;
            border-radius: 10px;
        }
        .avatar-preview-box {
            position: relative;
            display: inline-block;
        }
        .avatar-preview {
            width: 130px;
            height: 130px;
            object-fit: cover;
            border-radius: 50%;
            border: 4px solid #007bff;
            box-shadow: 0 4px 10px rgba(0,0,0,0.15);
        }
        .avatar-placeholder {
            width: 130px;
            height: 130px;
            border-radius: 50%;
            background-color: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #6c757d;
            font-size: 55px;
            border: 4px solid #dee2e6;
            margin: 0 auto;
        }
    </style>
</head>
<body>
    <div class="container py-4">
        <div class="card shadow profile-card">
            <div class="card-header bg-primary text-white text-center py-3">
                <h4 class="mb-0 font-weight-bold">
                    <i class="fas fa-user-edit mr-2"></i>CẬP NHẬT HỒ SƠ CÁ NHÂN
                </h4>
            </div>

            <div class="card-body p-4">
                <!-- Thông báo thành công nếu có -->
                <c:if test="${not empty message}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle mr-2"></i>${message}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

                <!-- Thông báo lỗi nếu có -->
                <c:if test="${not empty alert}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-triangle mr-2"></i>${alert}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
                    <!-- Khu vực hiển thị ảnh đại diện hiện tại & chọn ảnh mới -->
                    <div class="text-center mb-4">
                        <div class="avatar-preview-box mb-2">
                            <c:set var="userImg" value="${not empty user ? user.images : sessionScope.account.images}" />
                            <c:if test="${empty userImg}">
                                <c:set var="userImg" value="${not empty user ? user.avatar : sessionScope.account.avatar}" />
                            </c:if>

                            <c:choose>
                                <c:when test="${not empty userImg}">
                                    <c:choose>
                                        <c:when test="${userImg.startsWith('http')}">
                                            <img id="imagePreview" src="${userImg}" alt="Avatar" class="avatar-preview" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:url value="/image?fname=${userImg}" var="imgSrc" />
                                            <img id="imagePreview" src="${imgSrc}" alt="Avatar" class="avatar-preview" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <div id="placeholderBox" class="avatar-placeholder">
                                        <i class="fas fa-user"></i>
                                    </div>
                                    <img id="imagePreview" src="#" alt="Avatar Preview" class="avatar-preview d-none" />
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div>
                            <label for="imageFile" class="btn btn-outline-primary btn-sm mt-2">
                                <i class="fas fa-camera mr-1"></i> Chọn ảnh đại diện mới
                            </label>
                            <input type="file" class="d-none" id="imageFile" name="images" 
                                   accept="image/png,image/jpeg,image/webp,image/gif" onchange="previewSelectedImage(event)" />
                            <small class="form-text text-muted">Hỗ trợ định dạng: .jpg, .jpeg, .png, .webp, .gif (tối đa 5MB)</small>
                        </div>
                    </div>

                    <div class="row">
                        <!-- Tên đăng nhập (chỉ đọc) -->
                        <div class="col-md-6 form-group">
                            <label class="font-weight-bold text-muted">Tên đăng nhập:</label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="fas fa-user-lock"></i></span>
                                </div>
                                <input type="text" class="form-control bg-light" 
                                       value="${not empty user ? user.userName : sessionScope.account.userName}" readonly />
                            </div>
                        </div>

                        <!-- Email (chỉ đọc) -->
                        <div class="col-md-6 form-group">
                            <label class="font-weight-bold text-muted">Email:</label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                                </div>
                                <input type="email" class="form-control bg-light" 
                                       value="${not empty user ? user.email : sessionScope.account.email}" readonly />
                            </div>
                        </div>
                    </div>

                    <!-- Ô nhập Họ và tên -->
                    <div class="form-group">
                        <label for="fullname" class="font-weight-bold">
                            Họ và tên: <span class="text-danger">*</span>
                        </label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-id-card"></i></span>
                            </div>
                            <input type="text" class="form-control" id="fullname" name="fullname" 
                                   value="${not empty user ? user.fullName : sessionScope.account.fullName}" 
                                   maxlength="150" placeholder="Nhập họ và tên của bạn" required />
                        </div>
                    </div>

                    <!-- Ô nhập Số điện thoại -->
                    <div class="form-group">
                        <label for="phone" class="font-weight-bold">
                            Số điện thoại:
                        </label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-phone-alt"></i></span>
                            </div>
                            <input type="tel" class="form-control" id="phone" name="phone" 
                                   value="${not empty user ? user.phone : sessionScope.account.phone}" 
                                   pattern="^(0[3|5|7|8|9])[0-9]{8}$"
                                   title="Số điện thoại gồm 10 chữ số, bắt đầu bằng 03, 05, 07, 08, 09"
                                   placeholder="Nhập số điện thoại (ví dụ: 0901234567)" />
                        </div>
                    </div>

                    <hr class="my-4">

                    <!-- Các nút hành động -->
                    <div class="d-flex justify-content-between align-items-center">
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">
                            <i class="fas fa-arrow-left mr-1"></i> Trang chủ
                        </a>
                        <div>
                            <button type="reset" class="btn btn-outline-warning mr-2">
                                <i class="fas fa-undo mr-1"></i> Làm mới
                            </button>
                            <button type="submit" class="btn btn-primary px-4">
                                <i class="fas fa-save mr-1"></i> Lưu thay đổi
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Script xem trước ảnh khi chọn file & kiểm tra dung lượng -->
    <script>
        function previewSelectedImage(event) {
            const input = event.target;
            if (input.files && input.files[0]) {
                const file = input.files[0];
                const maxSize = 5 * 1024 * 1024; // 5MB
                if (file.size > maxSize) {
                    alert('Dung lượng ảnh vượt quá 5MB! Vui lòng chọn ảnh nhỏ hơn.');
                    input.value = '';
                    return;
                }

                const reader = new FileReader();
                reader.onload = function(e) {
                    const preview = document.getElementById('imagePreview');
                    const placeholder = document.getElementById('placeholderBox');
                    preview.src = e.target.result;
                    preview.classList.remove('d-none');
                    if (placeholder) {
                        placeholder.classList.add('d-none');
                    }
                };
                reader.readAsDataURL(file);
            }
        }
    </script>
</body>
</html>
