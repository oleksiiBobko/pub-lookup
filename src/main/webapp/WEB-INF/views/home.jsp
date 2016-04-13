<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<title>PubLookup</title>
<link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="resources/css/default.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html;">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
    content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<body>
    <div class="container">

        <div class="page-header">
            <h1>Lookup nearest Pub either by address or post code</h1>
        </div>

        <div class="panel-body">
            <form method="post" action="/">
                <div class="form-group">
                    <input type="text" class="form-control input-sm"
                        name="search" placeholder="search" />
                </div>
                <div class="form-group">
                    <input type="submit" value="lookup"
                        class="btn btn-info btn-block" />
                </div>
            </form>
            <div class="row">
                <div class="col-sm-12">
                    <button type="button" class="btn btn-success">IG11 0SN</button>
                    <button type="button" class="btn btn-success">IG11 0SN</button>
                    <button type="button" class="btn btn-success">IG11 0SN</button>
                    <button type="button" class="btn btn-success">IG11 0SN</button>
                    <button type="button" class="btn btn-success">IG11 0SN</button>
                    <button type="button" class="btn btn-success">IG11 0SN</button>
                    <button type="button" class="btn btn-success">IG11 0SN</button>
                    <button type="button" class="btn btn-success">IG11 0SN</button>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-8">
                    <c:if test="${!empty distances}">
                        <h2>Pub list</h2>
                        <ul class="list-group">
                        <c:forEach items="${distances}" var="distance">
                        <li class="list-group-item">
                            <a href="#">
                                <p>${distance.pub.pubName}</p>
                                <p>${distance.pub.locality}</p>
                                <p>${distance.distance}</p>
                            </a>
                            </li>
                        </c:forEach>
                        </ul>
                    </c:if>
                </div>
                <div class="col-sm-4">
                    <h2>Most recent searches</h2>
                    <ul class="list-group">
                        <li class="list-group-item">First item</li>
                        <li class="list-group-item">Second item</li>
                        <li class="list-group-item">Third item</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
