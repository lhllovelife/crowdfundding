<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html lang="zh-CN">
<head>
    <%@include file="include-head.jsp" %>
    <script type="text/javascript">
        $(function () {
            // 为新增按钮绑定事件
            $("#saveBtn").click(function () {
                var t = checkForm();
                if (t == true){
                    $("#createAdminForm").submit();
                }
            })

            // 为重置按钮绑定事件
            $("#resetBtn").click(function () {
                $("#creat-loginAcct").val("");
                $("#creat-userPswd").val("");
                $("#creat-userName").val("");
                $("#creat-email").val("");
            })
        })

        function checkForm() {
            //取值，去除两边空串
            var loginAcct =  $.trim($("#creat-loginAcct").val());
            var userPswd =  $.trim($("#creat-userPswd").val());
            var userName =  $.trim($("#creat-userName").val());
            var email =  $.trim($("#creat-email").val());
            //将去除两边空串的值赋值到表单中
            $("#creat-loginAcct").val(loginAcct);
            $("#creat-userPswd").val(userPswd);
            $("#creat-userName").val(userName);
            $("#creat-email").val(email); //这里存在问题：email表单项中，将去除两边串的email字符重新复制到表单中该项，该项在页面未出现效果

            // alert("-->"+$("#creat-email").val()  +"<--")

            if (loginAcct == ""){
                layer.msg("登录账号不能为空");
                return false;
            }
            if (userPswd == ""){
                layer.msg("登录密码不能为空");
                return false;
            }
            if (userName == ""){
                layer.msg("用户昵称不能为空");
                return false;
            }
            if (email == ""){
                layer.msg("邮箱地址不能为空");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <p style="margin-left: auto; color: #e4393c;">${requestScope.exception.message}</p>
                    <form role="form" id="createAdminForm" method="post" action="admin/save.html">
                        <div class="form-group">
                            <label for="exampleInputPassword1">登录账号</label>
                            <input type="text" class="form-control" id="creat-loginAcct" name="loginAcct" placeholder="请输入登录账号">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">登录密码</label>
                            <input type="text" class="form-control" id="creat-userPswd" name="userPswd" placeholder="请输入登录密码">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户昵称</label>
                            <input type="text" class="form-control" id="creat-userName" name="userName" placeholder="请输入用户昵称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input type="email" class="form-control" id="creat-email" name="email" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button id="saveBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button id="resetBtn" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>