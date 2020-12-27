<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html lang="zh-CN">
<%--将include-head包含进来--%>
<head>
    <%@include file="include-head.jsp" %>

    <%--引入分页组件--%>
    <link rel="stylesheet" href="static/css/pagination.css">
    <script type="text/javascript" src="static/jquery/jquery.pagination.js"></script>
    <script type="text/javascript">
        //页面加载完毕后开始执行
        $(function () {
            // 初始化导航栏
            initPagination();

            //页面加载完毕之后，将keyword值设置到参数框中
            $("#search-keyword").val("${param.keyword}")

            //给查询按钮绑定事件
            $("#searchBtn").click(function () {
                var keyword = $("#search-keyword").val();
                //执行带参数分页查询查询
                window.location.href = "admin/get/page.html?pageNum=1&pageSize=5&keyword="+keyword;
            })

            // 键盘敲击事件
            $(window).keydown(function (event) {
                // layer.msg(event.keyCode)
                //回车敲击键盘keyCode是13
                if (event.keyCode == 13){
                    // 执行关键词查询
                    var keyword = $("#search-keyword").val();
                    //执行带参数分页查询查询
                    window.location.href = "admin/get/page.html?pageNum=1&pageSize=5&keyword="+keyword;
                    //这样在搜索框中敲击回车就不会重新加载页面了
                    return false;
                }
            })

        })

        // 初始化导航栏
        function initPagination() {
            // 1. 获取总记录条数, 在分页对象信息中
            var totalRecord = ${requestScope.pageInfo.total};
            // 声明一个json对象存储Pagination要设置的属性
            var properties = {
                num_edge_entries: 3,								// 边缘页数
                num_display_entries: 5,								// 主体页数
                callback: pageSelectCallback,						// 指定用户点击“翻页”的按钮时跳转页面的回调函数
                items_per_page: ${requestScope.pageInfo.pageSize},	// 每页要显示的数据的数量
                current_page: ${requestScope.pageInfo.pageNum - 1},	// Pagination内部使用pageIndex来管理页码，pageIndex从0开始，pageNum从1开始，所以要减一
                prev_text: "上一页",									// 上一页按钮上显示的文本
                next_text: "下一页"									// 下一页按钮上显示的文本
            };
            // 生成页码导航条
            $("#Pagination").pagination(totalRecord, properties);
        }
        // 回调函数的含义：声明出来以后不是自己调用，而是交给系统或框架调用
        // 用户点击“上一页、下一页、1、2、3……”这样的页码时调用这个函数实现页面跳转
        // pageIndex是Pagination传给我们的那个“从0开始”的页码
        function pageSelectCallback(pageIndex, jQueryObj) {
            // 根据pageIndex计算pageNum
            var pageNum = pageIndex + 1;
            // 进行页面跳转

            // 从参数中取keyword，设置到搜索框
            <%--alert('${param.keyword}');--%>
            var paramKeyword = "${param.keyword}";
            // 设置到搜索框搜索框中(显示到搜索框中)
            $("#search-keyword").val(paramKeyword);
            window.location.href = "admin/get/page.html?pageNum="+pageNum+"&keyword="+paramKeyword;
            // 由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
            return false;
        }

        //删除单条函数
        function removeAdminById(adminId) {
            /* 询问是否需要删除
            if (window.confirm("您确定要删除吗？")){
                alert("删除");
            }
             */
            layer.confirm("你确定要删除吗？",{
                btn: ['取消', '确定']
            }, function (index) {
                // 按钮1的事件(点击后，关闭弹窗)
                layer.close(index)
            }, function(){
                // 按钮2的事件
                var pageNum = "${param.pageNum}";
                var keyword = $.trim($("#search-keyword").val());
                //访问删除接口地址
                window.location.href = "admin/remove.html?adminId="+adminId+"&pageNum="+pageNum+"&keyword="+keyword;
            });
        }

        //修改单条
        function editAdminById(adminId) {
            // 查询Admin数据，跳转到修改数据页面
            var pageNum = "${param.pageNum}";
            var keyword = $.trim($("#search-keyword").val());
            window.location.href = "admin/to/edit/page.html?adminId="+adminId+"&pageNum="+pageNum+"&keyword="+keyword;
        }
    </script>
</head>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <%--数据列表展示--%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="search-keyword" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='admin/to/add/page.html'"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="userBody">
                                <c:if test="${empty requestScope.pageInfo.list}">
                                    <tr>
                                        <td colspan="6" align="center">抱歉！没有查询到您要的数据</td>
                                    </tr>
                                </c:if>

                                <%--动态展示数据--%>
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                                            <button type="button" onclick="editAdminById('${admin.id}')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
                                            <button type="button" onclick="removeAdminById('${admin.id}')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"></div>
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
</body>
</html>