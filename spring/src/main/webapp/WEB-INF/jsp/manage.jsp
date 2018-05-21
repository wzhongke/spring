<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/9/30
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <spring:url value="/static/css/bootstrap.min.css" var="crunchifyCSS" />
    <title>Title</title>
    <link href="${crunchifyCSS}" rel="stylesheet" />
    <style>
        .blue{
            border-bottom: 1px solid red;
        }
        table>td {
            vertical-align:middle !important;
        }
        table>thead>tr>th{
            border-bottom: 2px solid cadetblue !important;
        }
    </style>
</head>
<body>
<div class="container" style="margin-top:120px;">
    <table class="table table-striped table-hover">
        <thead class="blue">
        <tr>
            <th>内容</th>
            <th>描述</th>
            <th>操作</th>
        </tr>
        </thead>
        <tr>
            <c:forEach var="pic" items="${person.imageList}" varStatus="status">
                <td>${pic.listName}</td>
                <td>${pic.description}</td>
                <td>
                    <a href="operate/view?order=${status.index}" class="btn btn-primary btn-sm"><i class="glyphicon glyphicon-zoom-in"></i>查看</a>
                    <a class="btn btn-primary btn-sm"><i class="glyphicon glyphicon-edit" ></i>修改</a>
                    <a class="btn btn-primary btn-sm"><i class="glyphicon glyphicon-trash" ></i>删除</a>
                </td>
            </c:forEach>
        </tr>
    </table>
</div>
<script></script>
<script src="/static/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/static/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
