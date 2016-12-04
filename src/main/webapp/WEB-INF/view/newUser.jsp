<%--
  Created by IntelliJ IDEA.
  User: Comarch
  Date: 2016-07-08
  Time: 07:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/partial/taglibs.jsp" %>
<html>
<head>
    <title>New user</title>
    <page:header/>
</head>
<body style="background-color: darkgoldenrod; color: navajowhite;">
<div class="container">
    <page:title text="New user" />
    <page:navbar/>
    <form:form action="${spring:mvcUrl('UC#createUser').build()}" cssClass="form-horizontal" modelAttribute="user">
        <spring:hasBindErrors name="user">
            <c:set value="${errors}" var="bindingErrors" />
        </spring:hasBindErrors>
        <page:input text="Name:" path="userName" errors="${bindingErrors}" />
        <page:input text="Password:" path="userPass" errors="${bindingErrors}" type="password" />
        <page:input text="Repeat password:" path="repeatUserPass" errors="${bindingErrors}" type="password" />
        <page:checkboxes text="Roles:" path="roleNames" errors="${bindingErrors}" items="${roles}" />
        <page:submitButton text="Create user" />
    </form:form>
</body>
</html>
