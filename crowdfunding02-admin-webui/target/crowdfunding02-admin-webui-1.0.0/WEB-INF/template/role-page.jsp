<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html lang="zh-CN">
<head>
    <%@include file="include-head.jsp" %>

    <%--引入分页组件--%>
    <link rel="stylesheet" href="static/css/pagination.css">
    <script type="text/javascript" src="static/jquery/jquery.pagination.js"></script>
    <script type="text/javascript">

        $(function () {

            //1. 准备初始化数据（作为全局变量）
            window.pageNum = 1;
            window.pageSize = 5;
            window.keyword = "";

            // 访问该页面时，自动执行该函数，生成页面效果
            generatePage();

            // 为查询按钮绑定事件
            $("#serachBtn").click(function () {
                // 取出查询参数
                var searchKeyword =  $.trim($("#searchKeyword").val());
                // layer.msg(searchKeyword);
                // 赋值给全局变量
                window.pageNum = 1;
                window.pageSize = 5;
                window.keyword = searchKeyword;
                // 执行查询
                generatePage();
            })

            // 为当前页面绑定键盘敲击事件
            $(window).keydown(function (event) {
                // layer.msg(event.keyCode)
                //回车敲击键盘keyCode是13
                if (event.keyCode == 13){
                    //执行带参数查询
                    // 取出查询参数
                    var searchKeyword =  $.trim($("#searchKeyword").val());
                    // 赋值给全局变量
                    window.pageNum = 1;
                    window.pageSize = 5;
                    window.keyword = searchKeyword;
                    // 执行查询
                    generatePage();

                    return false;
                }
            })

            // 给新增按钮绑定事件，点击新增按钮，打开模态窗口
            $("#creatBtn").click(function () {

                //手动打开模态窗口
                $("#addModal").modal("show");
            })

            // 为保存角色按钮绑定事件
            $("#saveRoleBtn").click(function () {
                var roleName = $.trim($("#roleName").val());
                if (roleName == ""){

                    layer.msg("角色名称不能为空");
                    return false;
                }

                // 发送ajax请求执行保存
                $.ajax({
                    url : "role/save.json",
                    data: {
                        "name" : roleName
                    },
                    type : "post",
                    dataType : "json",
                    success : function (response) {
                        if (response.result == "SUCCESS"){
                            // 显示响应结果
                            layer.msg("保存成功");

                            // 将搜索框中内容设置为空
                            $("#roleName").val("")

                            // 执行分页查询（跳转到第一页，查询关键词为空串儿，搜索框也设置为空串儿）
                            window.pageNum = 1;
                            window.pageSize = 5;
                            window.keyword = "";
                            $("#searchKeyword").val("");
                            generatePage();

                            // 关闭模态框
                            $("#addModal").modal("hide");
                        }
                        if (response.result == "FAILED"){
                            layer.msg("保存失败！" + response.message)
                        }
                    },
                    error : function (response) {
                        layer.msg("失败！响应状态码：" + response.status + " 说明信息：" + response.statusText);
                    }
                });

            })

            // 为更新按钮板顶事件
            $("#updateRoleBtn").click(function () {
                var id = $("#edit-id").val();
                var name = $.trim($("#edit-name").val());
                // ajax提交执行请求
                $.ajax({
                    url : "role/update.json",
                    data: {
                        "id" : id,
                        "name" : name
                    },
                    type : "post",
                    dataType : "json",
                    success : function (response) {
                        if (response.result == "SUCCESS"){
                            // 响应成功信息
                            layer.msg("更新成功")
                            // 关闭模态框
                            $("#editModal").modal("hide");
                            // 分页(到当前页 )
                            generatePage();
                        }

                        if (response.result == "FAILED"){
                            layer.msg(response.message)
                        }

                    },
                    error : function (response) {
                        layer.msg("失败！响应状态码：" + response.status + " 说明信息：" + response.statusText);
                    }
                });
            })

        })

        // 1. 执行分页，生成页面效果。一个总体函数。
        // 任何时候调用该函数就会重新加载分页数据
        function generatePage() {
            // 获取分页数据并填充表格
            var pageInfo = getPageInfoRemote();

        }

        // 2. 远程访问服务器，获取pageInfo数据
        function getPageInfoRemote() {

            $.ajax({
                url : "role/get/page/info.json",
                data: {
                    "pageNum" : window.pageNum,
                    "pageSize" : window.pageSize,
                    "keyword" : window.keyword
                },
                type : "post",
                dataType : "json",
                success : function (data) {
                    if (data.result == "SUCCESS"){
                        var pageInfo = data.data;
                        // ajax响应成功后填充表格
                        fillTableBody(pageInfo);
                    }
                },
                error : function (data) {
                    layer.msg("失败！响应状态码：" + data.status + " 说明信息：" + data.statusText);
                }
            });
        }
        // 3. 填充表格
        function fillTableBody(pageInfo) {
            // console.log("执行")
            // console.log(pageInfo.list.length);
            // 将数据铺到表格中
            var html = "";
            if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
                console.log("没有数据：" + pageInfo)
                html += '<tr><td colspan="6" align="center">抱歉！没有查询到您要的数据</td></tr>';
                // 将roleBod清空
                $("#roleBody").html(html);
                // 将导航条清空
                $("#Pagination").html("");
                return ;
            }
            // console.log("没有数据：" + pageInfo)
            $.each(pageInfo.list, function (i, n) {
                html += '<tr>';
                html += '<td>'+(i+1)+'</td>';
                html += '<td><input type="checkbox"></td>';
                html += '<td>'+n.name+'</td>';
                html += '<td>';
                html += '<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                html += '<button type="button" onclick="UpdateRole(\'' + n.id + '\',\''  +  n.name + '\')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                html += '<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
                html += '</td>';
                html += '</tr>';
            })

            $("#roleBody").html(html);
            // 生成分页导航条
            generateNavigator(pageInfo);
        }
        // 4. 生成分页导航条
        function generateNavigator(pageInfo) {
            // 获取总记录条数
            var totalRecord = pageInfo.total;
            // 声明一个json对象存储Pagination要设置的属性
            var properties = {
                num_edge_entries: 3,								// 边缘页数
                num_display_entries: 5,								// 主体页数
                callback: pageSelectCallback,						// 指定用户点击“翻页”的按钮时跳转页面的回调函数
                items_per_page: pageInfo.pageSize,	// 每页要显示的数据的数量
                current_page: pageInfo.pageNum-1,	// Pagination内部使用pageIndex来管理页码，pageIndex从0开始，pageNum从1开始，所以要减一
                prev_text: "上一页",									// 上一页按钮上显示的文本
                next_text: "下一页"									// 下一页按钮上显示的文本
            };
            // 生成页码导航条
            $("#Pagination").pagination(totalRecord, properties);
        }
        // 5. 翻页时回调函数
        function pageSelectCallback(pageIndex, jQuery){
            // 根据pageIndex计算pageNum
            var pageNum = pageIndex + 1;
            // 要跳转的页数
            window.pageNum = pageNum;
            // 将关键词从全局变量取出赋值到搜索框中
            $("#searchKeyword").val(window.keyword);
            //更新数据
            generatePage();

            // 由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
            return false;
        }

        // 为"铅笔"绑定函数
        function UpdateRole(roleId, roleName) {
            // layer.msg(roleId + " " + roleName);
            // 1. 将roleId 设置到隐藏域中, rolename设置到边框中
            $("#edit-id").val(roleId);
            $("#edit-name").val(roleName);
            // 2. 打开模态窗口
            $("#editModal").modal("show");
        }



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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="searchKeyword" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="serachBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" id="creatBtn" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="roleBody">

<%--                            <tr>--%>
<%--                                <td>1</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>PM - 项目经理</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"></div>
<%--                                    <ul class="pagination">--%>
<%--                                        <li class="disabled"><a href="#">上一页</a></li>--%>
<%--                                        <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>--%>
<%--                                        <li><a href="#">2</a></li>--%>
<%--                                        <li><a href="#">3</a></li>--%>
<%--                                        <li><a href="#">4</a></li>--%>
<%--                                        <li><a href="#">5</a></li>--%>
<%--                                        <li><a href="#">下一页</a></li>--%>
<%--                                    </ul>--%>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--新增用户模态框--%>
<div id="addModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增</h4>
            </div>
            <div class="modal-body">
                <form class="form-signin" role="form">
                    <div class="form-group has-success has-feedback">
                        <input
                                type="text" id="roleName"
                                class="form-control" placeholder="请输入角色名称"
                                autofocus>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="saveRoleBtn" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>
<%--更新角色信息模态框--%>
<div id="editModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">更新</h4>
            </div>
            <div class="modal-body">
                <form class="form-signin" role="form">
                    <div class="form-group has-success has-feedback">
                        <input type="hidden" id="edit-id">
                        <input
                                type="text" id="edit-name"
                                class="form-control" placeholder="请输入角色名称"
                                autofocus>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="updateRoleBtn" type="button" class="btn btn-success">更新</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>