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
    <title>Change roles</title>
    <page:header/>
</head>
<body style="background-color: darkgoldenrod; color: navajowhite;">
<div class="container">
    <page:title text="Change roles" />
    <page:navbar/>
    <form:form action="${spring:mvcUrl('UC#setRoles').arg(0, user.userName).build()}" cssClass="form-horizontal" modelAttribute="user">
        <spring:hasBindErrors name="user">
            <c:set value="${errors}" var="bindingErrors" />
        </spring:hasBindErrors>
        <form:hidden path="userName" />
        <page:checkboxes text="Roles:" path="roleNames" errors="${bindingErrors}" items="${roles}" />
        <page:submitButton text="Change roles" />
    </form:form>
</body>
</html>
