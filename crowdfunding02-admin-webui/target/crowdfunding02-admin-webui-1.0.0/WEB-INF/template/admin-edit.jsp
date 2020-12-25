<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html lang="zh-CN">
<head>
    <%@include file="include-head.jsp" %>

    <script type="text/javascript">
        $(function () {
            // 为修改按钮绑定事件
            $("#editBtn").click(function () {
                if (checkForm()){
                    $("#editForm").submit();
                }
            })

            // 为重置按钮绑定事件
            $("#resetBtn").click(function () {
                $("#edit-loginAcct").val("");
                $("#edit-userName").val("");
                $("#edit-email").val("");
            })
        })

        function checkForm() {
            //取值，去除两边空串
            var loginAcct =  $.trim($("#edit-loginAcct").val());
            var userName =  $.trim($("#edit-userName").val());
            var email =  $.trim($("#edit-email").val());
            //将去除两边空串的值赋值到表单中
            $("#edit-loginAcct").val(loginAcct);
            $("#edit-userName").val(userName);
            $("#edit-email").val(email); //这里存在问题：email表单项中，将去除两边串的email字符重新复制到表单中该项，该项在页面未出现效果

            // alert("-->"+$("#creat-email").val()  +"<--")

            if (loginAcct == ""){
                layer.msg("登录账号不能为空");
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
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form" id="editForm" action="admin/update.html">
                        <%--隐藏域--%>
                        <input type="hidden" name="id" value="${param.adminId}">
                            <input type="hidden" name="pageNum" value="${param.pageNum}">
                            <input type="hidden" name="keyword" value="${param.keyword}">
                        <div class="form-group">
                            <label for="exampleInputPassword1">登录账号</label>
                            <input type="text" class="form-control" id="edit-loginAcct" name="loginAcct" value="${requestScope.admin.loginAcct}">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户昵称</label>
                            <input type="text" class="form-control" id="edit-userName" name="userName" value="${requestScope.admin.userName}">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input type="email" class="form-control" id="edit-email" name="email" value="${requestScope.admin.email}">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="button" id="editBtn" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> 修改</button>
                        <button type="button" id="resetBtn" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>