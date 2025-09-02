<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${company.name} - 关于我们</title>
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
                        <a class="nav-link active" href="<c:url value='/about' />">关于我们</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/products' />">产品介绍</a>
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
            <div class="card mb-4">
                <div class="card-body">
                    <h1 class="card-title text-center mb-4">关于${company.name}</h1>
                    <p class="lead text-center">${company.description}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h2 class="card-title text-center mb-3">我们的使命</h2>
                            <p class="card-text text-center">通过技术创新改善人们的生活质量，为客户创造价值。</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 mb-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h2 class="card-title text-center mb-3">我们的愿景</h2>
                            <p class="card-text text-center">成为行业内最受尊敬和信赖的装饰材料公司。</p>
                        </div>
                    </div>
                </div>
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
