<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${company.name} - 后台管理</title>
    <link rel="icon" type="image/png" href="<c:url value='/resources/images/products/logo.png'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/styles.css'/>" rel="stylesheet">
</head>
<body class="starry-sky d-flex flex-column min-vh-100">
    <nav class="navbar navbar-expand-lg navbar-light">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/admin' />">后台管理</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#company-management">公司管理</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#product-management">产品管理</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#add-product">新增产品</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/' />">返回前台</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/admin/logout' />">登出</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-3 mt-md-5 content">
        <div class="jumbotron bg-white p-4 rounded">
            <h1 class="display-4 text-center-sm">后台管理</h1>
            <p class="lead text-center-sm">在这里管理公司信息和产品。</p>
            <hr class="my-4">
        </div>

        <section id="company-management" class="admin-section fade-in">
            <h2 class="mb-4 text-center-sm">公司管理</h2>
            <form action="<c:url value='/admin/save' />" method="post">
                <input type="hidden" name="id" value="${company.id}">
                <div class="form-row">
                    <div class="form-group mb-3">
                        <label for="name" class="form-label">公司名称</label>
                        <input type="text" class="form-control" id="name" name="name" value="${company.name}" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="address" class="form-label">公司地址</label>
                        <input type="text" class="form-control" id="address" name="address" value="${company.address}" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group mb-3">
                        <label for="phone" class="form-label">联系电话</label>
                        <input type="tel" class="form-control" id="phone" name="phone" value="${company.phone}" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="email" class="form-label">联系邮箱</label>
                        <input type="email" class="form-control" id="email" name="email" value="${company.email}" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">公司简介</label>
                    <textarea class="form-control" id="description" name="description" rows="3" required>${company.description}</textarea>
                </div>
                <button type="submit" class="btn btn-primary">保存公司信息</button>
            </form>
        </section>

        <section id="product-management" class="admin-section fade-in">
            <h2 class="mb-4 text-center-sm">产品管理</h2>
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>描述</th>
                            <th>价格</th>
                            <th>图片</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td>${product.name}</td>
                                <td><pre class="product-description"><c:out value="${product.description}" escapeXml="true" /></pre></td>
                                <td>${product.price}</td>
                                <td>
                                    <img src="<c:url value='/resources/images/products/${product.imagePath}'/>" alt="${product.name}" style="max-width: 100px;">
                                </td>
                                <td>
                                    <a href="<c:url value='/admin/product/edit/${product.id}' />" class="btn btn-sm btn-primary">编辑</a>
                                    <a href="<c:url value='/admin/product/delete/${product.id}' />" class="btn btn-sm btn-danger">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>

        <section id="add-product" class="admin-section fade-in">
            <h2 class="mb-4 text-center-sm">新增产品</h2>
            <form action="<c:url value='/admin/product/save' />" method="post" enctype="multipart/form-data">
                <div class="form-row">
                    <div class="form-group mb-3">
                        <label for="productName" class="form-label">产品名称</label>
                        <input type="text" class="form-control" id="productName" name="name" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="productPrice" class="form-label">价格</label>
                        <input type="number" class="form-control" id="productPrice" name="price" step="0.01" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="productDescription" class="form-label">产品描述</label>
                    <textarea class="form-control" id="productDescription" name="description" rows="3" required></textarea>
                </div>
                <div class="mb-3">
                    <label for="productImage" class="form-label">产品图片</label>
                    <input type="file" class="form-control" id="productImage" name="image" accept="image/*" required>
                </div>
                <button type="submit" class="btn btn-success">添加产品</button>
            </form>
        </section>
    </div>

    <footer class="footer py-3 bg-dark text-white">
        <div class="container">
            <span class="text-center d-block">&copy; 2025 天津靓白装饰材料. 保留所有权利。</span>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
            navLinks.forEach(link => {
                link.addEventListener('click', function(e) {
                    if (this.getAttribute('href').startsWith('#')) {
                        e.preventDefault();
                        const targetId = this.getAttribute('href');
                        const targetElement = document.querySelector(targetId);
                        if (targetElement) {
                            window.scrollTo({
                                top: targetElement.offsetTop - 70,
                                behavior: 'smooth'
                            });
                        }
                    }
                });
            });
        });
    </script>
</body>
</html>
