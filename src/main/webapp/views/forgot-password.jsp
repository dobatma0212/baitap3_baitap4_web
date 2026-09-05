<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quên Mật Khẩu - Khôi Phục Tài Khoản</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .forgot-box {
            max-width: 460px;
            margin: 60px auto;
            padding: 35px 30px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }
        .forgot-icon {
            font-size: 46px;
            color: #ff9800;
            margin-bottom: 15px;
            text-align: center;
        }
        .forgot-title {
            text-align: center;
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
        }
        .forgot-subtitle {
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
        .btn-forgot {
            background-color: #ff9800;
            border-color: #ff9800;
            color: #fff;
            font-size: 16px;
            font-weight: 600;
            padding: 10px;
            transition: all 0.3s ease;
        }
        .btn-forgot:hover {
            background-color: #e68900;
            border-color: #e68900;
            color: #fff;
        }
    </style>
</head>
<body>
    <jsp:include page="topbar.jsp"></jsp:include>

    <div class="container">
        <div class="forgot-box">
            <div class="forgot-icon">
                <i class="fas fa-key"></i>
            </div>
            <h3 class="forgot-title">Quên Mật Khẩu</h3>
            <p class="forgot-subtitle">Nhập địa chỉ email liên kết với tài khoản của bạn để nhận mã OTP xác thực.</p>

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

            <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                <div class="form-group">
                    <label for="email" class="font-weight-bold" style="font-size: 14px;">Địa chỉ Email</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-envelope"></i></span>
                        </div>
                        <input type="email" id="email" name="email" class="form-control" 
                               value="${email != null ? email : ''}" placeholder="Nhập địa chỉ email của bạn" required autofocus>
                    </div>
                </div>

                <button type="submit" class="btn btn-block btn-forgot mt-4">
                    <i class="fas fa-paper-plane mr-1"></i> Gửi Mã OTP Qua Email
                </button>
            </form>

            <div class="text-center mt-4" style="font-size: 14px;">
                <a href="${pageContext.request.contextPath}/login" class="text-secondary font-weight-500">
                    <i class="fas fa-arrow-left mr-1"></i> Quay lại trang Đăng nhập
                </a>
            </div>
        </div>
    </div>
</body>
</html>

