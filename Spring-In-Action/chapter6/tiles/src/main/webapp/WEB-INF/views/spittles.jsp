<%--
  Created by IntelliJ IDEA.
  User: ChenZhePC
  Date: 2017/1/16
  Time: 13:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
</head>
<body>

    <div class="spittleForm">
        <h1><s:message code="spittles.formHeader" text="Spit"/></h1>
        <form method="post" name="spittleForm">
            <input type="hidden" name="latitude">
            <input type="hidden" name="longitude">
            <textarea name="message" cols="80" rows="5"></textarea> <br/>
            <input type="submit" value="Add">
        </form>
    </div>
    <div class="listTitle">
        <h1>Recent Spittles</h1>
        <ul class="spittleList">
            <c:forEach items="${spittleList}" var="spittle">
                <li id="spittle_<c:out value="spittle.id"/>">
                    <div class="spittleMessage">
                        <c:out value="${spittle.message}"/>
                    </div>
                    <div>
                        <span class="spittleTime"><c:out value="${spittle.time}"/></span>
                        <span>(<c:out value="${spittle.latitude}" />,<c:out value="${spittle.longitude}" />)</span>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <c:if test="${size gt 20}">
            <hr/>
            <s:url value="/spittles?max=${maxCount}&count=${count}" var="more_url"/>
            <a href="${more_url}">Show More</a>
        </c:if>
    </div>

</body>
</html>
