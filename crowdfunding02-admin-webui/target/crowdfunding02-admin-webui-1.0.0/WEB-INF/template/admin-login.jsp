<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <title>尚筹网-管理员登录</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <script src="static/jquery/jquery-2.1.1.min.js"></script>
    <script src="static/bootstrap/js/bootstrap.min.js"></script>
    <script src="static/layer/layer.js"></script>

    <link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/font-awesome.min.css">
    <link rel="stylesheet" href="static/css/login.css">
    <style>

    </style>

    <script>
        $(function () {

            //1. 给登录按钮绑定事件
            $("#loginBtn").click(function () {
                //调用登录方法
                login();
            })

            //2. 为当前窗口绑定键盘敲击事件
            $(window).keydown(function (event) {
                // layer.msg(event.keyCode)
                //回车敲击键盘keyCode是13
                if (event.keyCode == 13){
                    //登录
                    login();
                }
            })
        })

        //进行提交表单，进行登录
        function login() {
            var loginAct = $("#loginAcct").val();
            var userPswd = $("#userPswd").val();
            // 去除前后空格
            var loginActTrim = loginAct.trim();
            var userPswdTrim = userPswd.trim();
            if (loginActTrim == ""){
                layer.msg("账号不能为空");
                return;
            }
            if (userPswdTrim == ""){
                layer.msg("密码不能为空");
                return;
            }
            //账号密码均不为空，可以进行提交表单
            //将取出前后空格的值设置到对应的input标签中
            $("#loginAcct").val(loginActTrim);
            $("#userPswd").val(userPswdTrim);
            $("#AdminLoginForm").submit();
        }
    </script>

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <%--更改登录接口地址： "/security/do/login.html"，使用spring security提供的登录检查功能--%>
    <form class="form-signin" role="form" id="AdminLoginForm" method="post" action="security/do/login.html">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="loginAcct" name="loginAcct" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="userPswd" name="userPswd" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <p style="margin-left: auto; color: #e4393c;">${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
        <p style="margin-left: auto; color: #e4393c;">${requestScope.exception.message}</p>
        <div class="checkbox" style="text-align:right;"><a href="reg.html">我要注册</a></div>
        <div id="loginBtn" class="btn btn-lg btn-success btn-block" >登录</div>
    </form>
</div>
</body>
</html>


