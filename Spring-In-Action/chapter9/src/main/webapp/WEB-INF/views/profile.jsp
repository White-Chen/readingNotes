<%--
  Created by IntelliJ IDEA.
  User: ChenZhePC
  Date: 2017/1/16
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Your Profile</h1>
<c:out value="${spitter.username}" /><br/>
<c:out value="${spitter.firstName}" /> <c:out value="${spitter.lastName}" /><br/>
<c:out value="${spitter.email}" />

