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
    <title>Roles</title>
    <page:header />
    <script type="application/javascript">
        submit = function (roleName) {
            console.log(roleName);
            $("#roleName").val(roleName);
            $("#role").submit();
        }
    </script>
</head>
<body style="background-color: darkgoldenrod">
<div class="container">
    <page:title text="Roles" />
    <page:navbar />
    <page:tableHeader firstColumnName="Role" secondColumnName="Users"/>
    <form:form action="${spring:mvcUrl('RC#deleteRole').build()}" modelAttribute="role" >
        <form:hidden path="roleName" />
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
        <c:forEach items="${roles}" var="role">
            <div id="user_${role.roleName}" class="row">
                <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
                <span>
                    <a href="#" onclick="submit('${role.roleName}');">
                        <img src="resources/icons/deletered.png" width="10" height="10" style="width: 10px; height: 10px;"/>
                    </a>
                    ${role.roleName}
                </span>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="color: navajowhite">
                    <%--<c:forEach items="${role.rolesEntities}" var="roleEntity">
                    <span id="role_${roleEntity.usersEntity.userName}">
                         <a href="${spring:mvcUrl('UC#getUser').arg(0, roleEntity.usersEntity.userName).build()}">${roleEntity.usersEntity.userName}</a>
                    </span>
                    <br />
                    </c:forEach>--%>
                    <c:forEach items="${role.usersEntitySet}" var="roleEntity">
                    <span id="role_${roleEntity.userName}">
                         <a href="${spring:mvcUrl('UC#getUser').arg(0, roleEntity.userName).build()}">${roleEntity.userName}</a>
                    </span>
                        <br />
                    </c:forEach>
                </div>
                <%--<hr style="margin-bottom: 0"/>--%>
            </div>
        </c:forEach>
    </form:form>
</div>
</body>
</html>
