<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập Vào Hệ Thống</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .login-box {
            max-width: 450px;
            margin: 60px auto;
            padding: 30px;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .login-title {
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
        .btn-login {
            background-color: #007bff;
            border-color: #007bff;
            font-size: 16px;
            font-weight: 500;
        }
        .btn-login:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <jsp:include page="topbar.jsp"></jsp:include>

    <div class="container">
        <div class="login-box">
            <h3 class="login-title">Đăng Nhập Vào Hệ Thống</h3>

            <c:if test="${alert != null}">
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-circle mr-2"></i>${alert}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="username">Tài khoản</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-user"></i></span>
                        </div>
                        <input type="text" id="username" name="username" class="form-control" placeholder="Tài khoản" required autofocus>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password">Mật khẩu</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-lock"></i></span>
                        </div>
                        <input type="password" id="password" name="password" class="form-control" placeholder="Mật khẩu" required>
                    </div>
                </div>

                <div class="form-group d-flex justify-content-between align-items-center">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="remember" name="remember">
                        <label class="custom-control-label" for="remember">Nhớ tôi</label>
                    </div>
                    <a href="#" class="text-muted" style="font-size: 14px;">Quên mật khẩu?</a>
                </div>

                <button type="submit" class="btn btn-primary btn-block btn-login mt-4">Đăng nhập</button>

                <div class="text-center mt-3" style="font-size: 14px;">
                    <span>Nếu bạn chưa có tài khoản trên hệ thống, thì hãy </span>
                    <a href="${pageContext.request.contextPath}/register" class="font-weight-bold">Đăng ký</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

