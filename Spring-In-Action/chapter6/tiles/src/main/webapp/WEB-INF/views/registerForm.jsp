<%--
  Created by IntelliJ IDEA.
  User: ChenZhePC
  Date: 2017/1/16
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/style.css" />" >
</head>
<body>
    <h1>Register</h1>

    <sf:form method="post" commandName="spitter">
        <sf:errors path="*" element="div" cssClass="errors"/>
        <sf:label path="firstName" csserrorclass="error">First Name</sf:label>:
        <sf:input path="firstName" cssErrorClass="error"/><br/>

        <sf:label path="lastName" csserrorclass="error">Last Name</sf:label>:
        <sf:input path="lastName" csserrorclass="error"/><br/>

        <sf:label path="email" csserrorclass="error">Email</sf:label>:
        <sf:input path="email" csserrorclass="error"/><br/>

        <sf:label path="username" csserrorclass="error">Username</sf:label>:
        <sf:input path="username" cssErrorClass="error"/><br/>

        <sf:label path="password" csserrorclass="error">Password</sf:label>:
        <sf:password path="password" cssErrorClass="error"/><br/>

        <input type="submit" value="Register"/>
    </sf:form>
</body>
</html>
