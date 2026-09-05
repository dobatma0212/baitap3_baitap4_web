<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác Thực OTP - Kích Hoạt Tài Khoản</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .otp-box {
            max-width: 460px;
            margin: 50px auto;
            padding: 35px 30px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }
        .otp-icon {
            font-size: 48px;
            color: #007bff;
            margin-bottom: 15px;
            text-align: center;
        }
        .otp-title {
            text-align: center;
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
        }
        .otp-subtitle {
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
            height: 50px;
        }
        .btn-verify {
            background-color: #28a745;
            border-color: #28a745;
            font-size: 16px;
            font-weight: 600;
            padding: 10px;
        }
        .btn-verify:hover {
            background-color: #218838;
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
        <div class="otp-box">
            <div class="otp-icon">
                <i class="fas fa-shield-alt"></i>
            </div>
            <h3 class="otp-title">Kích Hoạt Tài Khoản</h3>
            <p class="otp-subtitle">Nhập mã xác thực 6 chữ số được gửi tới hộp thư của bạn.</p>

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

            <form action="${pageContext.request.contextPath}/verify-otp" method="post">
                <div class="form-group">
                    <label for="email" class="font-weight-bold" style="font-size: 14px;">Địa chỉ Email</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-envelope"></i></span>
                        </div>
                        <input type="email" id="email" name="email" class="form-control" 
                               value="${email != null ? email : ''}" placeholder="Nhập địa chỉ email" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="otp" class="font-weight-bold" style="font-size: 14px;">Mã OTP (6 chữ số)</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-key"></i></span>
                        </div>
                        <input type="text" id="otp" name="otp" class="form-control otp-input" 
                               maxlength="6" placeholder="------" pattern="[0-9]{6}" required autofocus autocomplete="one-time-code">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary btn-block btn-verify mt-4">
                    <i class="fas fa-check-circle mr-1"></i> Xác Nhận Kích Hoạt
                </button>
            </form>

            <div class="resend-box">
                <span class="text-muted">Chưa nhận được mã OTP? </span>
                <form action="${pageContext.request.contextPath}/resend-otp" method="post" style="display: inline;">
                    <input type="hidden" name="email" value="${email != null ? email : ''}">
                    <button type="submit" class="btn btn-link p-0 font-weight-bold" style="vertical-align: baseline;">
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

