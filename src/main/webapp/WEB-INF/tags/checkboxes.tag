<%@ tag description="Checkboxes tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="text" required="true" %>
<%@ attribute name="path" required="true" %>
<%@ attribute name="errors" required="true" type="org.springframework.validation.Errors" %>
<%@ attribute name="items" required="true" type="java.util.List" %>
<div class="form-group">
    <span style="display: inline-block; max-width: 100%; margin-bottom: 5px; font-weight: 700;">${text}</span>
    <div class="panel panel-danger" style="display: ${errors.hasFieldErrors(path) ? "block" : "none"}">
        <form:errors path="${path}" cssClass="text-danger bg-danger panel-body" element="div"/>
    </div>
    <c:forEach items="${items}" var="item" varStatus="i">
        <div class="col-xs-offset-2 col-sm-offset-2 col-md-offset-1 col-lg-offset-1 col-xs-10 col-sm-10 col-md-11 col-lg-11">
            <div class="checkbox">
                <label for="${path}${i.count}">
                    <form:checkbox path="${path}" value="${item}"/>
                    <span>${item}</span>
                </label>
            </div>
        </div>
    </c:forEach>
</div>