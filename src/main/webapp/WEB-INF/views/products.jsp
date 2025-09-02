<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${company.name} - 产品介绍</title>
    <link rel="icon" type="image/png" href="<c:url value='/resources/images/products/logo.png'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/styles.css?v=1.3'/>" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/' />">${company.name}</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/' />">首页</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/about' />">关于我们</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="<c:url value='/products' />">产品介绍</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/contact' />">联系我们</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5 flex-grow-1">
        <div class="content">
            <h1 class="text-center mb-4">${company.name}的产品</h1>
            <p class="lead text-center mb-5">探索我们的优质产品，为您的生活带来更多便利和乐趣。</p>
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                <c:forEach var="product" items="${products}">
                    <div class="col">
                        <div class="card product-card h-100">
                            <a href="<c:url value='/product/${product.id}' />">
                                <img src="<c:url value='/resources/images/products/${product.imagePath}'/>" class="card-img-top product-img-top" alt="${product.name}">
                            </a>
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">
                                    <a href="<c:url value='/product/${product.id}' />" class="text-decoration-none">${product.name}</a>
                                </h5>
                                <p class="card-text mt-auto"><strong>价格: ￥${product.price}</strong></p>
                                <!-- 添加调试信息 -->
                                <p class="text-muted small">
                                    图片路径: <c:url value='/resources/images/products/${product.imagePath}'/>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
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
