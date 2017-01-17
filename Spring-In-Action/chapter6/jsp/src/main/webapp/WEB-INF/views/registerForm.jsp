<%--
  Created by IntelliJ IDEA.
  User: ChenZhePC
  Date: 2017/1/16
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Register</h1>

    <form method="post">
        Fist Name : <input type="text" name="firstName"/> <br/>
        Second Name : <input type="text" name="secondName"/> <br/>
        Username : <input type="text" name="username"/> <br/>
        Password : <input type="password" name="password"/> <br/>

        <input type="submit" value="Register"/>
    </form>
</body>
</html>
