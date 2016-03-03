<%--
  Created by IntelliJ IDEA.
  User: Никита
  Date: 02.02.2016
  Time: 21:49
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
    <title><fmt:message key="title.tours"/></title>
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/sticky-footer-navbar.css">
</head>
<body>
<c:import url="header.jsp"></c:import>
<div class="container">
  <h1 align="center"><fmt:message key="label.tours"/></h1>
  <h3 align="center">${warn}</h3>
  <h3 align="center">${message}</h3>
  <div class="row">
    <button type="button" class="btn btn-primary" name="back" onclick="history.back()">back</button>
  </div>
  <c:if test="${warn eq null}">
<table class="table">
  <thead>
  <tr>
    <th class="col-md-1"><fmt:message key="label.begDate"/></th>
    <th class="col-md-1"><fmt:message key="label.endDate"/></th>
    <th class="col-md-1"><fmt:message key="label.tourType"/></th>
    <th class="col-md-1"><fmt:message key="label.resort"/></th>
    <th class="col-md-1"><fmt:message key="label.country"/></th>
    <th class="col-md-1"><fmt:message key="label.cost"/></th>
    <th class="col-md-2"><fmt:message key="label.discription"/></th>
    <th class="col-md-1"><fmt:message key="label.operator"/></th>
    <th class="col-md-1"><fmt:message key="label.seats"/></th>
    <th class="col-md-1"><fmt:message key="label.tourStatus"/></th>
  </tr>
  </thead>
  <c:forEach var="temp" items="${tours}" varStatus="number">
    <tbody>
    <tr>
      <td><c:out value="${temp.begDate}" /></td>
      <td><c:out value="${temp.endDate}" /></td>
      <td><c:out value="${temp.tourType.typeName}" /></td>
      <td><c:out value="${temp.resort.resortName}" /></td>
      <td><c:out value="${temp.resort.country.countryName}" /></td>
      <td><c:out value="${temp.cost}" /></td>
      <td><c:out value="${temp.discription}" /></td>
      <td><c:out value="${temp.tourOperator.operatorName}" /></td>
      <td><c:out value="${temp.numberOfSeats}" /></td>
      <td>
        <c:if test="${temp.tourStatus eq '0'}">
          <fmt:message key="label.nBerning"/>
        </c:if>
        <c:if test="${temp.tourStatus eq '1'}">
          <fmt:message key="label.berning"/>
        </c:if>
      </td>

      <form name="order" action="epam" method="post">
        <input type="hidden" name="command" value="orderTour">
        <input type="hidden" name="tourId" value="${temp.tourId}">
        <input type="hidden" name="userId" value="${sessionScope.user.userId}">
        <input type="hidden" name="totalNum" value="${temp.numberOfSeats}">
        <input type="hidden" name="previousCommand" value="${param.command}">
        <input type="hidden" name="id" value="${param.id}">
        <c:if test="${sessionScope.role eq 'user'}">
          <td class="col-md-1">
            <button class="btn btn-group-sm btn-primary" type="submit"><fmt:message key="label.order"/></button>
          </td>
          <td class="col-md-1">
            <input type="number" name="num" required min="1" max="${temp.numberOfSeats}" style="width: 50px;">
          </td>
        </c:if>
      </form>
      <form name="update" action="epam" method="post">
        <input type="hidden" name="command" value="getUpdateData">
        <input type="hidden" name="tourId" value="${temp.tourId}">
        <input type="hidden" name="begDate" value="${temp.begDate}">
        <input type="hidden" name="endDate" value="${temp.endDate}">
        <input type="hidden" name="typeId" value="${temp.tourType.typeId}">
        <input type="hidden" name="resortId" value="${temp.resort.resortId}">
        <input type="hidden" name="cost" value="${temp.cost}">
        <input type="hidden" name="discription" value="${temp.discription}">
        <input type="hidden" name="operatorId" value="${temp.tourOperator.operatorId}">
        <input type="hidden" name="seats" value="${temp.numberOfSeats}">
        <input type="hidden" name="status" value="${temp.tourStatus}">
        <c:if test="${sessionScope.role eq 'admin'}">
          <td>
            <button class="btn btn-group-sm btn-primary" type="submit"><fmt:message key="label.editTour"/></button>
          </td>
        </c:if>
      </form>
      <form name="delete" action="epam" method="post">
        <input type="hidden" name="command" value="deleteTour">
        <input type="hidden" name="tourId" value="${temp.tourId}">
        <input type="hidden" name="previousCommand" value="${param.command}">
        <input type="hidden" name="id" value="${param.id}">
        <c:if test="${sessionScope.role eq 'admin'}">
          <td>
            <button class="btn btn-group-sm btn-primary" type="submit"><fmt:message key="label.deleteTour"/></button>
          </td>
        </c:if>
      </form>
    </tr>
    </tbody>
  </c:forEach>
</table>
  </c:if>
</div>
<c:import url="footer.jsp"></c:import>
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
