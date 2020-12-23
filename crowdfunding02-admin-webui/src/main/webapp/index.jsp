<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <title>首页</title>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="static/jquery/jquery-2.1.1.min.js"></script>

    <script>
        $(function () {
            //页面元素加载完成之后执行此处函数
            $("#btn1").click(function () {
                $.ajax({
                    url : "test/ajax1.do",
                    data: {
                        "id" : "1100",
                        "name" : "张三",
                    },
                    type : "get",
                    dataType : "json",
                    success : function (data) {

                    }
                });
            })


            $("#btn2").click(function () {
                $.ajax({
                    url : "test/ajax2.do",
                    data: JSON.stringify({
                        "id" : "1100",
                        "name" : "张三",
                        "classgrade" : {
                            "classno" : "71",
                            "classname" : "宏志班"
                        }
                    }),
                    type : "post",
                    contentType : "application/json;charset=UTF-8",
                    dataType : "json",
                    success : function (data) {
                    }
                });
            })
        })
    </script>

</head>
<body>
<h3>首页</h3>
<input type="button" id="btn1" value="ajax发送key=value格式"> <br/>
<input type="button" id="btn2" value="ajax发送json字符串">
<a href="test/target.html">访问target</a>
</body>
</html>
