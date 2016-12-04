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
    <title>New role</title>
    <page:header/>
</head>
<body style="background-color: darkgoldenrod; color: navajowhite;">
<div class="container">
    <page:title text="New role" />
    <page:navbar/>
    <form:form action="${spring:mvcUrl('RC#createRole').build()}" cssClass="form-horizontal" modelAttribute="role">
        <spring:hasBindErrors name="role">
            <c:set value="${errors}" var="bindingErrors" />
        </spring:hasBindErrors>
        <page:input text="Name:" path="roleName" errors="${bindingErrors}"/>
        <page:submitButton text="Create role" />
    </form:form>
</body>
</html>
