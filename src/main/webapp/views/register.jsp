<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tạo Tài Khoản Mới</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .register-box {
            max-width: 480px;
            margin: 40px auto;
            padding: 30px;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .register-title {
            text-align: center;
            margin-bottom: 25px;
            font-weight: 600;
            color: #333;
        }
        .input-group-text {
            background-color: #e9ecef;
            width: 42px;
            justify-content: center;
        }
        .btn-register {
            background-color: #17a2b8;
            border-color: #17a2b8;
            font-size: 16px;
            font-weight: 500;
            color: #fff;
        }
        .btn-register:hover {
            background-color: #138496;
            color: #fff;
        }
    </style>
</head>
<body>
    <jsp:include page="topbar.jsp"></jsp:include>

    <div class="container">
        <div class="register-box">
            <h3 class="register-title">Tạo tài khoản mới</h3>

            <c:if test="${alert != null}">
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-circle mr-2"></i>${alert}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="post" id="registerForm">
                <div class="form-group">
                    <label for="username">Tên tài khoản: <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-user"></i></span>
                        </div>
                        <input type="text" id="username" name="username" class="form-control" placeholder="Tài khoản (3-50 ký tự)" 
                               value="${username}" pattern="^[a-zA-Z0-9_]{3,50}$" 
                               title="Tên tài khoản từ 3 đến 50 ký tự, chỉ gồm chữ cái, số và dấu gạch dưới" required autofocus>
                    </div>
                </div>

                <div class="form-group">
                    <label for="fullname">Họ và tên: <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-id-card"></i></span>
                        </div>
                        <input type="text" id="fullname" name="fullname" class="form-control" placeholder="Họ và tên" 
                               value="${fullname}" maxlength="150" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">Địa chỉ Email: <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-envelope"></i></span>
                        </div>
                        <input type="email" id="email" name="email" class="form-control" placeholder="name@example.com" 
                               value="${email}" maxlength="150" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="phone">Số điện thoại: <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-phone"></i></span>
                        </div>
                        <input type="tel" id="phone" name="phone" class="form-control" placeholder="Ví dụ: 0912345678" 
                               value="${phone}" pattern="^(0[3|5|7|8|9])[0-9]{8}$" 
                               title="Số điện thoại phải gồm 10 chữ số, bắt đầu bằng 03, 05, 07, 08, 09" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password">Mật khẩu: <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-lock"></i></span>
                        </div>
                        <input type="password" id="password" name="password" class="form-control" placeholder="Tối thiểu 6 ký tự" 
                               minlength="6" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Xác nhận mật khẩu: <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-check-double"></i></span>
                        </div>
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Nhập lại mật khẩu" 
                               minlength="6" required>
                    </div>
                    <small id="passwordMatchText" class="form-text"></small>
                </div>

                <button type="submit" class="btn btn-block btn-register mt-4">Tạo tài khoản</button>

                <div class="text-center mt-3" style="font-size: 14px;">
                    <span>Nếu bạn đã có tài khoản? </span>
                    <a href="${pageContext.request.contextPath}/login" class="font-weight-bold">Đăng nhập</a>
                </div>
            </form>
        </div>
    </div>

    <script>
        const pwdInput = document.getElementById('password');
        const confirmPwdInput = document.getElementById('confirmPassword');
        const matchText = document.getElementById('passwordMatchText');
        const form = document.getElementById('registerForm');

        function validatePasswordMatch() {
            if (!confirmPwdInput.value) {
                matchText.textContent = '';
                return true;
            }
            if (pwdInput.value === confirmPwdInput.value) {
                matchText.textContent = 'Mật khẩu khớp!';
                matchText.className = 'form-text text-success font-weight-bold';
                confirmPwdInput.setCustomValidity('');
                return true;
            } else {
                matchText.textContent = 'Mật khẩu xác nhận không khớp!';
                matchText.className = 'form-text text-danger font-weight-bold';
                confirmPwdInput.setCustomValidity('Mật khẩu xác nhận không khớp!');
                return false;
            }
        }

        pwdInput.addEventListener('input', validatePasswordMatch);
        confirmPwdInput.addEventListener('input', validatePasswordMatch);

        form.addEventListener('submit', function(e) {
            if (!validatePasswordMatch()) {
                e.preventDefault();
                confirmPwdInput.focus();
            }
        });
    </script>
</body>
</html>

