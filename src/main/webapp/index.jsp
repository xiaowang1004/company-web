<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>重定向到产品页面</title>
    <meta http-equiv="refresh" content="0;url=<c:url value='/products' />">
</head>
<body>
    <p>如果您没有被自动重定向，请<a href="<c:url value='/products' />">点击这里</a>。</p>
</body>
</html>
