<%@ tag description="Submit button tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="type" required="false"%>
<%@ attribute name="text" required="true" %>
<%@ attribute name="path" required="true" %>
<%@ attribute name="errors" required="true" type="org.springframework.validation.Errors" %>
<c:if test="${empty type}">
    <c:set var="type" value="text" />
</c:if>
<div class="form-group">
    <form:label path="${path}">${text}</form:label>
    <c:choose>
        <c:when test="${type eq 'text'}">
            <form:input path="${path}" cssClass="form-control" cssErrorClass="form-control"/>
        </c:when>
        <c:when test="${type eq 'password'}">
            <form:password path="${path}" cssClass="form-control" cssErrorClass="form-control"/>
        </c:when>
    </c:choose>
    <div class="panel panel-danger" style="display: ${errors.hasFieldErrors(path) ? "block" : "none"}">
        <form:errors path="${path}" cssClass="text-danger bg-danger panel-body" element="div"/>
    </div>
</div>