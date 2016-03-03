<%--
  Created by IntelliJ IDEA.
  User: Никита
  Date: 02.02.2016
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.language == null}">
    <fmt:setLocale value="ru_Ru"/>
</c:if>
<c:if test="${sessionScope.language != null}" >
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="resources.caption"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.countries"/></title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/sticky-footer-navbar.css">
</head>
<body>
<c:import url="header.jsp"></c:import>
<div class="container">
    <div class="row" style="margin-top: 5%">
        <c:forEach var="temp" items="${countries}">
            <div class="col-md-2 col-md-offset-1">
            ${warn}
                <a href="/epam?command=country&id=${temp.countryId}">
                    <img src="images/flags/${temp.countryName}.png" class="img-thumbnail center-block" width="100" height="100">
                </a>
                <h5 class="text-center">${temp.countryName}</h5>
            </div>
        </c:forEach>
    </div>
</div>

<c:import url="footer.jsp"></c:import>

<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
