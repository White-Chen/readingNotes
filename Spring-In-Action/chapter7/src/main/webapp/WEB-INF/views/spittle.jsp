<%--
  Created by IntelliJ IDEA.
  User: ChenZhePC
  Date: 2017/1/16
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="spittleView">
    <div class="spittleMessage"><c:out value="${spittle.message}"/></div>
    <div class="spittleTime"><c:out value="${spittle.time}"/></div>
</div>
