<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<title>PubLookup</title>
</head>
<body>
<h1>
Lookup nearest Pub either by address or postal code
</h1>
<div class="panel-body">
<form method="post" action="/">
    <div class="form-group">
        <input type="text" class="form-control input-sm" name="address" placeholder="address" />
    </div>
    <div class="form-group">
        <input type="submit" value="lookup" class="btn btn-info btn-block" />
    </div>
</form>
<c:if test="${!empty pubs}">
<c:forEach items="${pubs}" var="pub">
<p>${pub.pubName}</p>
<p>${pub.area}</p><br>
</c:forEach>
</c:if>
</div>
</body>
</html>
