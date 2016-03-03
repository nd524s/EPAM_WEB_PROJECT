<%--
  Created by IntelliJ IDEA.
  User: Никита
  Date: 04.02.2016
  Time: 18:46
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
  <title><fmt:message key="title.resorts"/></title>
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/sticky-footer-navbar.css">
</head>
<body>
<c:import url="header.jsp"></c:import>
<div class="col-md-2 col-md-offset-5" style="margin-top: 15% ">
  ${warn}
  <c:forEach var="temp" items="${resorts}">
    <div class="list-group">
      <a href="/epam?command=resort&id=${temp.resortId}" class="list-group-item">${temp.resortName}</a>
    </div>
  </c:forEach>
</div>

<c:import url="footer.jsp"></c:import>

<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>

