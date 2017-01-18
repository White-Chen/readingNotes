<%--
  Created by IntelliJ IDEA.
  User: ChenZhe
  Date: 1/15/2017
  Time: 11:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spittr</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css"/>">
</head>
<body>
    <h1><s:message code="spitter.welcome" text="Welcome"/></h1>

    <s:url value="/spitter/register" var="registerUrl" />

    <a href="<s:url value="/spittles"/> ">Spittles</a>
    <a href="${registerUrl}">Register</a>
</body>
</html>
