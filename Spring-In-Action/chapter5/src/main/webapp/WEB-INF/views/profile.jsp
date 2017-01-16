<%--
  Created by IntelliJ IDEA.
  User: ChenZhePC
  Date: 2017/1/16
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1><c:out value="${spitter.username}"/> Profile</h1>
    <c:out value="${spitter.username}"/> <br/>
    <c:out value="${spitter.firstName}"/>  <c:out value="${spitter.lastName}"/>
</body>
</html>
