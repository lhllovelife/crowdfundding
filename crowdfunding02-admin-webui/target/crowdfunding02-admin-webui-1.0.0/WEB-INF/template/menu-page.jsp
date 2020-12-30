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
</body>
</html>