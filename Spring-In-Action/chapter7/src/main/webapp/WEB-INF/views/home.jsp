<%--
  Created by IntelliJ IDEA.
  User: ChenZhe
  Date: 1/15/2017
  Time: 11:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<h1><s:message code="spitter.welcome" text="Welcome"/></h1>
<s:url value="/spitter/register" var="registerUrl" />
<a href="<s:url value="/spittles"/> ">Spittles</a>
<a href="${registerUrl}">Register</a>
