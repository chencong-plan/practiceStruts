<%--
  Created by IntelliJ IDEA.
  User: chencong
  Date: 2017/9/5
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <form action="LoginAction" method="post">
        用户名：<input name="user.username"><br>
        密码：<input name="user.password" type="password">
        <input type="submit" value="登录">
    </form>
</body>
</html>
