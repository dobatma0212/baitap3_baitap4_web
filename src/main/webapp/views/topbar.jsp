<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="topbar-wrapper" style="background: #333; color: #fff; padding: 10px 20px; margin-bottom: 20px;">
    <div class="container-fluid" style="display: flex; justify-content: space-between; align-items: center;">
        <div>
            <a href="${pageContext.request.contextPath}/home" style="color: #fff; text-decoration: none; font-weight: bold; font-size: 18px;">HỆ THỐNG QUẢN LÝ</a>
        </div>
        <c:choose>
            <c:when test="${sessionScope.account == null}">
                <div class="col-sm-6 text-right">
                    <ul class="list-inline right-topbar pull-right" style="list-style: none; margin: 0; padding: 0; display: inline-flex; gap: 10px;">
                        <li><a href="${pageContext.request.contextPath}/login" style="color: #fff; text-decoration: none;">Đăng nhập</a> | 
                            <a href="${pageContext.request.contextPath}/register" style="color: #fff; text-decoration: none;">Đăng ký</a></li>
                        <li><i class="search fa fa-search search-button"></i></li>
                    </ul>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col-sm-6 text-right">
                    <ul class="list-inline right-topbar pull-right" style="list-style: none; margin: 0; padding: 0; display: inline-flex; gap: 10px;">
                        <li><a href="${pageContext.request.contextPath}/member/myaccount" style="color: #fff; text-decoration: none;">${sessionScope.account.fullName}</a> | 
                            <a href="${pageContext.request.contextPath}/logout" style="color: #ffc107; text-decoration: none; font-weight: bold;">Đăng Xuất</a></li>
                        <li><i class="search fa fa-search search-button"></i></li>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

