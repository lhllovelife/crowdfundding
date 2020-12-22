<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <title>ERROR</title>
    <base href="<%=basePath%>">
</head>
<body>
<h3>出了点小差错！</h3>
错误信息：${exception.message}
</body>
</html>
