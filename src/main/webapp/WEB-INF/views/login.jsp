<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理员登录</title>
    <link rel="icon" type="image/png" href="<c:url value='/resources/images/products/logo.png'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/styles.css?v=1.2'/>" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
    <div class="container flex-grow-1 d-flex align-items-center justify-content-center">
        <div class="login-container">
            <h2>管理员登录</h2>
            <form action="${pageContext.request.contextPath}/admin/login" method="post">
                <div class="mb-3">
                    <input type="text" name="userName" placeholder="用户名" required>
                </div>
                <div class="mb-3">
                    <input type="password" name="password" placeholder="密码" required>
                </div>
                <button type="submit">登录</button>
            </form>
        </div>
    </div>
    
    <footer class="footer py-4 bg-dark text-white">
        <div class="container text-center">
            <span>&copy; 2025 天津靓白装饰材料. 保留所有权利。</span>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
