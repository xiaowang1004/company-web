<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>编辑产品 - ${company.name}</title>
    <link rel="icon" type="image/png" href="<c:url value='/resources/images/products/logo.png'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/styles.css?v=1.1'/>" rel="stylesheet">
</head>
<body class="starry-sky d-flex flex-column min-vh-100">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/admin' />">后台管理</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/admin' />">返回管理面板</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/' />">返回前台</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-3 mt-md-5 flex-grow-1">
        <div class="jumbotron bg-white p-4 rounded mb-4">
            <h1 class="display-4 text-center-sm">编辑产品</h1>
            <p class="lead text-center-sm">在这里修改产品信息。</p>
            <hr class="my-4">
        </div>
        <form action="<c:url value='/admin/product/save' />" method="post" enctype="multipart/form-data" class="mb-5 bg-white p-4 rounded">
            <input type="hidden" name="id" value="${product.id}">
            <div class="mb-3">
                <label for="name" class="form-label">产品名称</label>
                <input type="text" class="form-control" id="name" name="name" value="${product.name}" required>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">产品描述</label>
                <textarea class="form-control product-description" id="description" name="description" rows="5" required><c:out value="${product.description}" escapeXml="true" /></textarea>
                <small class="form-text text-muted">支持换行和格式化文本。</small>
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">价格</label>
                <input type="number" class="form-control" id="price" name="price" step="0.01" value="${product.price}" required>
            </div>
            <div class="mb-3">
                <label for="image" class="form-label">产品图片</label>
                <input type="file" class="form-control" id="image" name="image" accept="image/*">
                <small class="form-text text-muted">如果不上传新图片，将保留原有图片。</small>
            </div>
            <div class="mb-3 text-center">
                <img src="<c:url value='/resources/images/products/${product.imagePath}'/>" alt="${product.name}" style="max-width: 100%; height: auto; max-height: 300px;">
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-primary">保存修改</button>
            </div>
        </form>
    </div>

    <footer class="footer py-3 bg-dark text-white">
        <div class="container">
            <span class="text-center d-block">&copy; 2025 天津靓白装饰材料. 保留所有权利。</span>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
