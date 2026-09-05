<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt Lại Mật Khẩu - Xác Thực OTP</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .reset-box {
            max-width: 480px;
            margin: 40px auto;
            padding: 35px 30px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }
        .reset-icon {
            font-size: 46px;
            color: #28a745;
            margin-bottom: 15px;
            text-align: center;
        }
        .reset-title {
            text-align: center;
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
        }
        .reset-subtitle {
            text-align: center;
            color: #6c757d;
            font-size: 14px;
            margin-bottom: 25px;
        }
        .input-group-text {
            background-color: #e9ecef;
            width: 42px;
            justify-content: center;
        }
        .otp-input {
            font-size: 22px;
            font-weight: bold;
            letter-spacing: 6px;
            text-align: center;
            height: 48px;
        }
        .btn-reset {
            background-color: #28a745;
            border-color: #28a745;
            color: #fff;
            font-size: 16px;
            font-weight: 600;
            padding: 10px;
            transition: all 0.3s ease;
        }
        .btn-reset:hover {
            background-color: #218838;
            border-color: #1e7e34;
            color: #fff;
        }
        .resend-box {
            background-color: #f8f9fa;
            border-radius: 6px;
            padding: 12px;
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <jsp:include page="topbar.jsp"></jsp:include>

    <div class="container">
        <div class="reset-box">
            <div class="reset-icon">
                <i class="fas fa-shield-alt"></i>
            </div>
            <h3 class="reset-title">Đặt Lại Mật Khẩu</h3>
            <p class="reset-subtitle">Nhập mã OTP đã nhận qua email và thiết lập mật khẩu mới.</p>

            <c:if test="${alert != null}">
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-circle mr-2"></i>${alert}
                </div>
            </c:if>

            <c:if test="${success_msg != null}">
                <div class="alert alert-success" role="alert">
                    <i class="fas fa-check-circle mr-2"></i>${success_msg}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/reset-password" method="post" id="resetPasswordForm">
                <div class="form-group">
                    <label for="email" class="font-weight-bold" style="font-size: 14px;">Địa chỉ Email</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-envelope"></i></span>
                        </div>
                        <input type="email" id="email" name="email" class="form-control" 
                               value="${email != null ? email : ''}" placeholder="Địa chỉ email" required readonly style="background-color: #e9ecef;">
                    </div>
                </div>

                <div class="form-group">
                    <label for="otp" class="font-weight-bold" style="font-size: 14px;">Mã OTP (6 chữ số)</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-key"></i></span>
                        </div>
                        <input type="text" id="otp" name="otp" class="form-control otp-input" 
                               maxlength="6" placeholder="------" pattern="[0-9]{6}" inputmode="numeric" 
                               title="Mã OTP gồm 6 chữ số" required autofocus autocomplete="one-time-code">
                    </div>
                </div>

                <div class="form-group">
                    <label for="newPassword" class="font-weight-bold" style="font-size: 14px;">Mật khẩu mới</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-lock"></i></span>
                        </div>
                        <input type="password" id="newPassword" name="newPassword" class="form-control" 
                               placeholder="Nhập ít nhất 6 ký tự" minlength="6" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="confirmPassword" class="font-weight-bold" style="font-size: 14px;">Xác nhận mật khẩu mới</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-check-double"></i></span>
                        </div>
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" 
                               placeholder="Nhập lại mật khẩu mới" minlength="6" required>
                    </div>
                    <small id="resetMatchText" class="form-text"></small>
                </div>

                <button type="submit" class="btn btn-block btn-reset mt-4">
                    <i class="fas fa-check-circle mr-1"></i> Đổi Mật Khẩu
                </button>
            </form>

            <script>
                const newPwd = document.getElementById('newPassword');
                const confirmPwd = document.getElementById('confirmPassword');
                const matchMsg = document.getElementById('resetMatchText');
                const resetFrm = document.getElementById('resetPasswordForm');

                function checkResetPwdMatch() {
                    if (!confirmPwd.value) {
                        matchMsg.textContent = '';
                        return true;
                    }
                    if (newPwd.value === confirmPwd.value) {
                        matchMsg.textContent = 'Mật khẩu khớp!';
                        matchMsg.className = 'form-text text-success font-weight-bold';
                        confirmPwd.setCustomValidity('');
                        return true;
                    } else {
                        matchMsg.textContent = 'Mật khẩu xác nhận không khớp!';
                        matchMsg.className = 'form-text text-danger font-weight-bold';
                        confirmPwd.setCustomValidity('Mật khẩu không khớp!');
                        return false;
                    }
                }

                newPwd.addEventListener('input', checkResetPwdMatch);
                confirmPwd.addEventListener('input', checkResetPwdMatch);

                resetFrm.addEventListener('submit', function(e) {
                    if (!checkResetPwdMatch()) {
                        e.preventDefault();
                        confirmPwd.focus();
                    }
                });
            </script>

            <div class="resend-box">
                <span class="text-muted">Chưa nhận được mã OTP? </span>
                <form action="${pageContext.request.contextPath}/resend-forgot-otp" method="post" style="display: inline;">
                    <input type="hidden" name="email" value="${email != null ? email : ''}">
                    <button type="submit" class="btn btn-link p-0 font-weight-bold text-success" style="vertical-align: baseline;">
                        Gửi lại mã OTP
                    </button>
                </form>
            </div>

            <div class="text-center mt-3" style="font-size: 14px;">
                <a href="${pageContext.request.contextPath}/login" class="text-secondary">
                    <i class="fas fa-arrow-left mr-1"></i> Quay lại trang Đăng nhập
                </a>
            </div>
        </div>
    </div>
</body>
</html>

