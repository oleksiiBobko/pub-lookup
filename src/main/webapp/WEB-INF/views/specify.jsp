<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<c:set var="ctxt" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}" />
<div>
<c:choose>
<c:when test="${empty result}">Nothing found</c:when>
<c:otherwise>
<ul class="list-group">
<c:forEach items="${result}" var="item">
<li class="list-group-item">
<a class="link" href="${ctxt}/specify?search=${item.id}">${item.formatted}</a>
</li>
</c:forEach>
</ul>
</c:otherwise>
</c:choose>
</div>