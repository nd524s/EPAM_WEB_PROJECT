<%--
  Created by IntelliJ IDEA.
  User: Никита
  Date: 29.02.2016
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:choose>
  <c:when test="${sessionScope.language == null}">
    <fmt:setLocale value="ru_Ru"/>
  </c:when>
  <c:when test="${sessionScope.language eq 'ru_Ru'}">
    <fmt:setLocale value="ru_Ru"/>
  </c:when>
  <c:when test="${sessionScope.language eq 'en_Us'}">
    <fmt:setLocale value="en_Us"/>
  </c:when>
</c:choose>
<fmt:setBundle basename="resources.caption"/>
<!DOCTYPE html>
<html>
<head>
  <title><fmt:message key="title.message"/></title>
  <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>

<body>
<div class="container">
  <div class="row">
    <div class="col-md-12">
      <h1><fmt:message key="message.delete"/></h1>
      <div class="error-actions">
        <a href="${url}" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>
          <fmt:message key="label.back"/>
        </a>
      </div>
    </div>
  </div>
</div>
</body>
</html>
