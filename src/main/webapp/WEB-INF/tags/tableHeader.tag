<%@ tag description="Table header tag" pageEncoding="UTF-8" %>
<%@ attribute name="firstColumnName" required="true" %>
<%@ attribute name="secondColumnName" required="true" %>
<div id="header" class="row">
    <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 h4" style="color: navajowhite">
        ${firstColumnName}
    </div>
    <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 h4" style="color: navajowhite">
        ${secondColumnName}
    </div>
</div>