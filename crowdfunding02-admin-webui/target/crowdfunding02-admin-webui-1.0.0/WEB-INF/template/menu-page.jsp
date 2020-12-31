<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html lang="zh-CN">
<head>
    <%@include file="include-head.jsp" %>

    <%--引入zTree文件--%>
    <link rel="stylesheet" href="static/ztree/zTreeStyle.css">
    <script type="text/javascript" src="static/ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="static/crowd/my-menu.js"></script>

    <script type="text/javascript">

        $(function () {
            generateTree();

            // 为保存按钮绑定事件
            $("#menuSaveBtn").click(function () {
                // 1. 从隐藏域中取出当前节点id
                var currentNodeId = $("#currentNodeId").val();
                // 2. 准备ajax发送的数据
                var pid = currentNodeId;
                var name = $.trim($("#create-name").val());
                var url = $.trim($("#create-url").val());
                var icon = $("input[name=icon]:checked").val();

                // 2. ajax提交数据
                $.ajax({
                    url : "menu/save.json",
                    data: {
                        "pid" : pid,
                        "name" : name,
                        "url" : url,
                        "icon" : icon
                    },
                    type : "post",
                    dataType : "json",
                    success : function (response) {
                        if (response.result == "SUCCESS"){
                            // 响应成功信息
                            layer.msg("保存成功！")
                            // 清空表单内容
                            $("#menuResetBtn").click();
                            // 重新刷新显示树结构
                            generateTree();
                            // 关闭模态框
                            $("#menuAddModal").modal("hide");
                        }

                        if (response.result == "FAILED"){
                            layer.msg(response.message);
                        }
                    },
                    error : function (response) {
                        layer.msg("失败！响应状态码：" + response.status + " 说明信息：" + response.statusText);
                    }
                });

            })

            // 为更新按钮绑定事件
            $("#menuEditBtn").click(function () {
                // 获取要提交的数据
                var id = $("#edit-currentNodeId").val();
                var name = $.trim($("#edit-name").val());
                var url = $.trim($("#edit-url").val());
                var icon = $("#menuEditModal [name=icon]:checked").val();

                // 2. ajax提交数据
                $.ajax({
                    url : "menu/update.json",
                    data: {
                        "id" : id,
                        "name" : name,
                        "url" : url,
                        "icon" : icon
                    },
                    type : "post",
                    dataType : "json",
                    success : function (response) {
                        if (response.result == "SUCCESS"){
                            // 响应成功信息
                            layer.msg("更新成功！")

                            // 重新刷新显示树结构
                            generateTree();
                            // 关闭模态框
                            $("#menuEditModal").modal("hide");
                        }

                        if (response.result == "FAILED"){
                            layer.msg(response.message);
                        }
                    },
                    error : function (response) {
                        layer.msg("失败！响应状态码：" + response.status + " 说明信息：" + response.statusText);
                    }
                });

            })

            // 为确认删除按钮绑定事件
            $("#confirmBtn").click(function () {

                var id = $("#remove-id").val();

                // 2. ajax提交数据
                $.ajax({
                    url : "menu/remove.json",
                    data: {
                        "id" : id
                    },
                    type : "post",
                    dataType : "json",
                    success : function (response) {
                        if (response.result == "SUCCESS"){
                            // 响应成功信息
                            layer.msg("删除成功！")

                            // 重新刷新显示树结构
                            generateTree();
                            // 关闭模态框
                            $("#menuConfirmModal").modal("hide");
                        }

                        if (response.result == "FAILED"){
                            layer.msg(response.message);
                        }
                    },
                    error : function (response) {
                        layer.msg("失败！响应状态码：" + response.status + " 说明信息：" + response.statusText);
                    }
                });


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

            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float: right; cursor: pointer;" data-toggle="modal"
                         data-target="#myModal">
                        <i class="glyphicon glyphicon-question-sign"></i>
                    </div>
                </div>
                <div class="panel-body">
                    <!-- 这个ul标签是zTree动态生成的节点所依附的静态节点 -->
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>

        </div>
    </div>
</div>

<%@include file="modal-menu-add.jsp" %>
<%@include file="modal-menu-edit.jsp" %>
<%@include file="modal-menu-confirm.jsp" %>


</body>
</html>