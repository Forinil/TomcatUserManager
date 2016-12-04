<%--
  Created by IntelliJ IDEA.
  User: Comarch
  Date: 2016-07-06
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/partial/taglibs.jsp" %>
<html>
<head>
    <title>user-manager</title>
    <page:header />
</head>
<body style="background-color: darkgoldenrod">
<style type="text/css">
    hr.index {
        /*border: 1px inset #eeeeee;*/
        border-image-outset: 0;
        border-image-repeat: stretch;
        border-image-slice: 100%;
        border-image-source: none;
        border-image-width: 1;
        border: 0 none rgb(51, 51, 51);
        border-top: 1px solid rgb(238, 238, 238);
    }
</style>
<div class="container">
    <page:title text="Users" />
    <page:navbar />
    <page:tableHeader firstColumnName="User" secondColumnName="Roles" />
    <c:forEach items="${users}" var="user">
        <div id="user_${user.userName}" class="row">
            <%--<spring:url value="/user/${user.userName}" var="url" htmlEscape="true" />--%>
            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
                    <a href="${spring:mvcUrl('UC#getUser').arg(0, user.userName).build()}">${user.userName}</a>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
                <%--<c:forEach items="${user.userRolesEntities}" var="role">
                    <span id="role_${role.userRolesEntityPK.roleName}">
                            ${role.userRolesEntityPK.roleName}
                    </span>
                    <br />
                </c:forEach>--%>
                <c:forEach items="${user.rolesEntitySet}" var="role">
                    <span id="role_${role.roleName}">
                            ${role.roleName}
                    </span>
                    <br />
                </c:forEach>
            </div>
            <%--<hr class="index"/>--%>
        </div>
    </c:forEach>
</div>
</body>
</html>
