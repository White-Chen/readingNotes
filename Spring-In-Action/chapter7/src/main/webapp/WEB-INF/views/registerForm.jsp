<%--
  Created by IntelliJ IDEA.
  User: ChenZhePC
  Date: 2017/1/16
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<h1>Register</h1>

<sf:form method="post" commandName="spitterForm" enctype="multipart/form-data">
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

    <input type="file" name="profilePicture" accept="image/jpeg, image/png, image/gif"/>
    <input type="submit" value="Register"/>
</sf:form>
