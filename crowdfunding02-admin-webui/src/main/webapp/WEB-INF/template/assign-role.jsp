<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html lang="zh-CN">
<head>
    <%@include file="include-head.jsp" %>

    <script>
        $(function () {
            // 向右按钮
            // 将左边下拉框中选中的添加到右边的下拉框中
            // select 是标签选择器
            // :eq(0)表示选择页面上的第一个
            // :eq(1)表示选择页面上的第二个
            // “>”表示选择子元素
            $("#toRightBtn").click(function () {
                $("select:eq(0)>option:selected").appendTo("select:eq(1)");
            })

            // 向右按钮
            // 将右边下拉框中选中的添加到左边的下拉框中
            $("#toLeftBtn").click(function () {
                $("select:eq(1)>option:selected").appendTo("select:eq(0)");
            })

            // 为提交按钮绑定事件
            $("#submitBtn").click(function () {
                // 将右框中全部选中
                $("select:eq(1)>option").prop("selected", "selected");

                // 提交表单
                $("#assignForm").submit();
            })
        })
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
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" id="assignForm" class="form-inline" action="assign/do/role/assign.html" method="post">
                        <%--设置隐藏域--%>
                        <input type="hidden" name="adminId" value="${param.adminId}">
                            <input type="hidden" name="pageNum" value="${param.pageNum}">
                            <input type="hidden" name="keyword" value="${param.keyword}">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoleList}" var="role" varStatus="myStatus">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select class="form-control" name="roleIdList" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoleList}" var="role" varStatus="myStatus">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                    <button id="submitBtn" style="width: 80px; margin-left: 130px" class="btn btn-md btn-success btn-block">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>