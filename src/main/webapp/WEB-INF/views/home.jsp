<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<c:set var="ctxt" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}" />
<html>
<head>
<title>PubLookup</title>
<link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="resources/css/default.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html;">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<script>
$(window).load(function() {
    $('#loading').hide();
});
$(document).ready(function() {
$('.result').on('click', 'a', function() {
    $('.selectModal').hide();
    $('#loading').show();
});
$('a').click(function(){
    $('#loading').show();
});
$('.submit_btn').click(function() {
    if($('input[type=text]').val() == '') {
        return false;
    }
    $('input[type=submit]').attr('disabled', true);
    $('.submit_form').submit();
});

$(".submit_form").submit(function(event) {
    var postData = $(this).serializeArray();
    var formURL = $(this).attr('action');
    var method = $(this).attr('method');
    $.ajax({
    type: method,
    url: formURL,
    data: postData,
    success: function(data, textStatus, jqXHR) {
         if(jqXHR.status === 200) {
              $('.result').html(data);
              $(".selectModal").modal('show');
              $('input[type=submit]').attr('disabled', false);
         }
    },
    error: function(jqXHR, textStatus, errorThrown) {
         $('.result').html('<div class="alert alert-danger" role="alert">' + jqXHR.responseText + '</div>');
         window.setTimeout(function () {
              $(".alert").fadeTo(500, 0).slideUp(500, function () {
                  $(this).remove();
              });
         }, 5000);
         $('input[type=submit]').attr('disabled', false);
    }
    });
event.preventDefault();
return false;
});
$('.selectModal').on('hidden.bs.modal', function () {
    $('input[type=text]').val('');
})
});
</script>
</head>
<body>
    <div id="loading">
        <img id="loading-image" src="resources/images/wait.gif" alt="Loading..." />
    </div>
    <div class="modal fade selectModal" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true" id="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Select
                        location</h4>
                </div>

                <div class="modal-body">
                    <p class="result"></p>
                </div>
                <div class="modal-footer">
                    <button type="button"
                        class="btn btn-default modal_close"
                        data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <div class="container">

        <div class="page-header">
            <h1>Lookup nearest Pub either by address or post code</h1>
        </div>

        <div class="panel-body">
            <form method="post" action="/" class="submit_form">
                <div class="form-group">
                    <input type="text" class="form-control input-sm"
                        name="search" placeholder="e.g. place name, postcode, station..." />
                </div>
                <div class="form-group">
                    <input type="submit" value="lookup"
                        class="btn btn-info btn-block submit_btn"/>
                </div>
            </form>
            <div class="row">
                <div class="col-sm-12">
                    <c:forEach items="${cached_request}" var="cached_request" varStatus="loop">
                      <button type="button" class="btn btn-success search${loop.index}">${cached_request}</button>
                        <script>
                          $(document).ready(function() {
                              $('.search${loop.index}').click(function() {
                              $('input[type=text]').val('${cached_request}');
                              $('.submit_btn').trigger('click');
                          });
                          });
                        </script>
                    </c:forEach>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-8">
                    <c:if test="${!empty distances}">
                        <h2>search by <b>${search_view}</b></h2>
                        
                        
                        <ul class="list-group">
                        <c:forEach items="${distances}" var="distance"  varStatus="loop">
                        <li class="list-group-item">
                        <div class="row">
                        <div class="col-sm-6">
                            <p><b>Name:&nbsp</b><i>${distance.pub.pubName}</i></p>
                            <p><b>Address:&nbsp</b><i>${distance.pub.address}</i></p>
                            <p><b>Post code:&nbsp</b>
                            <button type="button" class="btn btn-default search-body${loop.index}"><i>${distance.pub.postCode}</i></button>
                            <script>
                            $(document).ready(function() {
                              $('.search-body${loop.index}').click(function() {
                              $('input[type=text]').val('${distance.pub.postCode}');
                              $('.submit_btn').trigger('click');
                              });
                              });
                            </script>
                            </p>
                            <p><b>Country:&nbsp</b><i>${distance.pub.country}</i></p>
                            <p><b>City:&nbsp</b><i>${distance.pub.city}</i></p>
                            <p><b>District:&nbsp</b><i>${distance.pub.district}</i></p>
                            <p><b>Distance:&nbsp</b><i>${distance.distance}</i></p>
                            </div>
                            <div class="col-sm-6">
                                <div style="align:\"right\";max-width: 200px; height: 150px;">
                                    <img style="max-width: 200px; height: 150px;" src="picture/${distance.pub.pubId}" alt="beer"/>
                                </div>
                            </div>
                        </div>
                        </li>
                        </c:forEach>
                        </ul>
                    </c:if>
                </div>
                <div class="col-sm-4">
                    <h2>Most recent searches</h2>
                    <ul class="list-group">
                        <c:forEach items="${cached_search}" var="cached_search">
                            <li class="list-group-item">
                                <a href="${ctxt}/specify?search=${cached_search.key}" >${cached_search.value}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
