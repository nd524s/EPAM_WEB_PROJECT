<%--
  Created by IntelliJ IDEA.
  User: Никита
  Date: 31.01.2016
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
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
  <title></title>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="/epam?command=getBurningTours"><fmt:message key="label.epamTravel"/> </a>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
      <ul class="nav navbar-nav">
        <li ><a href="/epam?command=getCountries"><fmt:message key="label.countries"/></a></li>
        <li><a href="/epam?command=getResorts"><fmt:message key="label.resorts"/></a></li>
        <c:if test="${sessionScope.role eq 'user'}">
          <li><a href="/epam?command=getUserOrders"><fmt:message key="label.user"/></a></li>
        </c:if>
        <c:if test="${sessionScope.role eq 'admin'}">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message key="label.admin"/> <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="/epam?command=viewAllOrders"><fmt:message key="label.showOrders"/></a></li>
              <li><a href="/epam?command=getAddData"><fmt:message key="label.addTour"/></a></li>
            </ul>
          </li>
        </c:if>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <ctg:header/>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>
</body>
</html>
