<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <title>目标页面</title>
    <base href="<%=basePath%>">
</head>
<body>
目标页面
</body>
</html>
