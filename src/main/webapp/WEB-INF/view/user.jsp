<%--
  Created by IntelliJ IDEA.
  User: Comarch
  Date: 2016-07-07
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/partial/taglibs.jsp" %>
<html>
<head>
    <title>User details</title>
    <page:header />
</head>
<body style="background-color: darkgoldenrod">
<div class="container">
    <page:title text="User details" />
    <page:navbar />
    <div id="header" class="row">
        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 h4" style="color: navajowhite">
            Field
        </div>
        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 h4" style="color: navajowhite">
            Data
        </div>
    </div>
    <div id="username" class="row">
        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
            <span>Username</span>
        </div>
        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
            <span>${user.userName}</span>
        </div>
        <hr style="margin-bottom: 0"/>
    </div>
    <div id="password" class="row">
        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
            <span>Password</span>
        </div>
        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
            <a href="${spring:mvcUrl('UC#changePassword').arg(0, user.userName).build()}">
                <span>${user.userPass}</span>
            </a>
        </div>
        <hr style="margin-bottom: 0"/>
    </div>
    <div id="roles" class="row">
        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
            <span>Roles</span>
        </div>
        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
            <%--<c:forEach items="${user.userRolesEntities}" var="role">
                <a href="${spring:mvcUrl('UC#changeRoles').arg(0, user.userName).build()}">
                    <span id="role_${role.userRolesEntityPK.roleName}">
                            ${role.userRolesEntityPK.roleName}
                    </span>
                </a>
                <br/>
            </c:forEach>--%>
            <c:forEach items="${user.rolesEntitySet}" var="role">
                <a href="${spring:mvcUrl('UC#changeRoles').arg(0, user.userName).build()}">
                    <span id="role_${role}">
                            ${role}
                    </span>
                </a>
                <br/>
            </c:forEach>
        </div>
        <hr style="margin-bottom: 0"/>
    </div>
    <form:form action="${spring:mvcUrl('UC#deleteUser').build()}" cssClass="form-horizontal" modelAttribute="user">
        <form:hidden path="userName" />
        <form:hidden path="userPass" />
        <form:hidden path="rolesEntitySet" />
        <page:submitButton text="Delete user" />
        <spring:hasBindErrors name="user">
            <div class="panel panel-danger">
                <div class="text-danger bg-danger panel-body">
                    <c:forEach items="${errors.fieldErrors}" var="error">
                        <span>${error}</span><br />
                    </c:forEach>
                    <c:forEach items="${errors.globalErrors}" var="error">
                        <span>${error}</span><br />
                    </c:forEach>
                </div>
            </div>
        </spring:hasBindErrors>
    </form:form>
</div>
</body>
</html>
