<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="css/style.css" type="text/css"/>
</head>
<body>
<h1>Set Yo' Spot</h1>
		<a href="/dash">Back </a>
	<form:form action="/process/new/location" method="POST" modelAttribute="place">
	
	<form:label path = "name">Name: </form:label>
	<form:input type="text" path="name"/><form:errors path="name"/><br>
	
	<form:label path = "street_address">Street Address: </form:label>
	<form:input type="text" path="street_address"/><form:errors path="street_address"/><br>
	
	<form:label path = "city">City: </form:label>
	<form:input type="text" path="city"/><form:errors path="city"/>
	
	<form:label path = "state">State: </form:label>
	<form:input type="text" path="state"/><form:errors path="state"/>
	
	<form:label path="zip">Zip Code:</form:label>
	<form:input type="number" path="zip"/><form:errors path="zip"/><br>
	
	<form:input type="hidden" path="user" value="${userId}"/>
	<input type="submit" value="Submit">
	</form:form>
</body>
</html>