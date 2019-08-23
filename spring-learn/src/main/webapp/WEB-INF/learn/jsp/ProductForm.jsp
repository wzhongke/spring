<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/1/6
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product Form</title>
</head>
<body>
    <div id="global">
        <form action="product_save.action" method="post">
            <fieldset>
                <legend>Add a product</legend>
                <p>
                    <label for="name">Product Name:</label>
                    <input  type="text" id="name" name="name" tabindx="1">
                </p>
                <p>
                    <label for="description">Description:</label>
                    <input type="text" id="description" name="description" tabindex="2">
                </p>
                <p>
                    <label for="price">Description:</label>
                    <input type="text" id="price" name="price" tabindex="3">
                </p>
                <p id="buttons">
                    <input type="reset" id="reset" name="price" tabindex="4">
                    <input type="submit" id="submit" tabindex="5" value="Add Product">
                </p>
            </fieldset>
        </form>
    </div>
</body>
</html>
