<%--
  Created by IntelliJ IDEA.
  User: Никита
  Date: 02.02.2016
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  <title></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<footer class="footer">
  <div class="container">
    <p class="text-muted"><fmt:message key="label.footer"/></p>
  </div>
</footer>
</body>
</html>
