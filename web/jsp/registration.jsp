<%--
  Created by IntelliJ IDEA.
  User: Никита
  Date: 05.02.2016
  Time: 16:05
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
  <title><fmt:message key="title.registration"/></title>
  <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body>
<div class="container">
  <div class="col-md-4 col-md-offset-4" style="margin-top:100px">
    <a href="/index.jsp"><h3 class="text-center"><fmt:message key="label.epamTravel"/></h3></a>
    <form name="signin" action="/epam" method="post">
      <input type="hidden" name="command" value="registrate">

      <div class="form-group">
        <label for="inputFname"><fmt:message key="label.firstName"/></label>
        <input type="text" class="form-control" name="fname" value="${param.fname}" required pattern="^([A-Za-z]{1,40}|[а-яА-ЯЁё]{1,40})$" title="<fmt:message key="message.nameReg"/>" id="inputFname" placeholder=<fmt:message key="label.firstName"/>>
      </div>

      <div class="form-group">
        <label for="inputLname"><fmt:message key="label.lastName"/></label>
        <input type="text" class="form-control" name="lname" value="${param.lname}" required pattern="^([A-Za-z]{1,40}|[а-яА-ЯЁё]{1,40})$" title="<fmt:message key="message.lastNameReg"/>" id="inputLname" placeholder=<fmt:message key="label.lastName"/>>
      </div>

      <div class="form-group">
        <label for="inputTelNumber"><fmt:message key="label.telNumber"/></label>
        <input type="text" class="form-control" name="telNumber" value="${param.telNumber}" required pattern="^(\+375)(25|29|33|44)([1-9]){1}([0-9]){6}$" title="<fmt:message key="message.telNum"/>" id="inputTelNumber" placeholder=<fmt:message key="label.telNumber"/> >
      </div>

      <div class="form-group">
        <label for="inputEmail"><fmt:message key="label.username"/></label>
        <input type="text" class="form-control" name="username" value="${param.username}"  required pattern="^(([a-zA-Z]{1})([a-zA-Z0-9]){2,18}([a-zA-z]))$" title="<fmt:message key="message.login"/>" id="inputEmail" placeholder=<fmt:message key="label.username"/>>
      </div>

      <div class="form-group">
        <label for="inputPassword"><fmt:message key="label.password"/></label>
        <input type="password" class="form-control" name="password" required pattern="^([a-zA-Z0-9]{4,20})$" title="<fmt:message key="message.pass"/>" id="inputPassword" placeholder=<fmt:message key="label.password"/>>
      </div>

      <h6 style="color: red">${warn}</h6>
      <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="label.signUp"/></button>
    </form>
  </div>
</div>
</body>
</html>
