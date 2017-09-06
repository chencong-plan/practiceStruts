<%--
  Created by IntelliJ IDEA.
  User: chencong
  Date: 2017/9/5
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户信息页面</title>
    <script src="js/jquery-3.2.1.min.js"></script>
    <script>
        $(function () {
            var status;
            //判断用户名是否存在于数据库当中
            $("#username").blur(function () {
                $.ajax({
                    url: "checkUserAction",
                    data: {
                        "user.username": $("#username").val()
                    },
                    type: "POST",
                    success: function (data) {
                        if (data == "success") {
                            $("#showMsg").html("用户名可以使用");
                        }
                        if (data == "fail") {
                            $("#showMsg").html("用户名已经被占用");
                        }
                        status = data;
                    }
                });
            });

            $("#btn_add").click(function () {
                var username = $("#username").val();
                var password = $("#password").val();
                if (username == "" || password == "") {
                    $("#showMsg").html("输入框不能为空");
                } else {
                    if (status == "success") {
                        $.ajax({
                            url: "userRegisterAction",
                            data: {
                                "user.username": username,
                                "user.password": password
                            },
                            type: "POST",
                            success: function (data) {
                                var $tr = $("<tr id=\"tr"+data[data.length-1].id+"\">"+
                                    "<td>"+data[data.length-1].id+"</td>"+
                                    "<td>"+data[data.length-1].username+"</td>"+
                                    "<td>"+data[data.length-1].password+"</td>"+
                                    "<td><a href='javascript:void(0)' value="+data[data.length-1].id +">删除</a></td>"
                                    +"</tr>");
                                var $table = $("#userTable");
                                $table.append($tr);
                                var username = $("#username").val("");
                                var password = $("#password").val("");
                                bindListener(data[data.length-1].id);
                            }
                        });
                        if (status == "fail") {
                            $("#showMsg").html("用户名已经被占用");
                        }
                    }
                }
            });

            $("a").click(function () {
                var flag = window.confirm("确定删除编号 "+$(this).attr("value")+" 吗");
                var id = $(this).attr("value");
                if(flag){
                    $.ajax({
                        url: "deleteUserAction",
                        data: {
                            "user.id": id
                        },
                        type: "POST",
                        success:function (data) {
                            if(data == "success"){
                                $("#tr"+id).remove();
                            }
                        }
                    });
                }
            });
            
            function bindListener(id) {
                $("#tr"+id).unbind().click(function(){
                    $(this).remove();
                })
            }
        });
    </script>
</head>
<body>
<table id="userTable">
    <tr>
        <td>用户编号</td>
        <td>用户名</td>
        <td>密码</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${userList}" var="user">
        <tr id="tr${user.id}">
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td><a href="javascript:void(0)" value="${user.id}">删除</a></td>
        </tr>
    </c:forEach>
</table>

<form id="addUserForm" onsubmit="return false">
    <input name="username" id="username"><span id="showMsg"></span>
    <br>
    <input name="password" id="password">
    <br>
    <button id="btn_add">添加</button>
</form>
</body>
</html>
