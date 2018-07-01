<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/10/21
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h2>${message}</h2>

<div>
    <form method="POST" enctype="multipart/form-data" action="/files/uploadFiles">
        <table>
            <tr><td>File to upload:</td><td><input name="files" type="file" caption="files" multiple /></td></tr>
            <tr><td></td><td><input type="submit" value="Upload" /></td></tr>
        </table>
    </form>
</div>

<div>
    <ul>
        <c:forEach items="${files}" var="file">
            <li>${file}</li>
        </c:forEach>
    </ul>
</div>

</body>
</html>
