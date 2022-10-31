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
</head>
<body>
	<div style="display: flex;">
		<div>
		<h1>Lyrics Lab                            </h1>
		</div>
		<div>
	
			<h2>Register</h2>
			<form:form action="/register" method="POST" modelAttribute="newUser">
				<p>
					Name:<br> <form:input type="text" path="userName"/><br>
					<form:errors path="userName"/>
				</p>
				<p>
					Email:<br> <form:input type="email" path="email"/><br>
						<form:errors path="email"/>
				</p>
				<p>
					Password:<br> <form:input type="password" path="password"/>
					<form:errors path="password"/>
				</p>
				<p>
					Confirm Password:<br> 
					<form:input type="password" path="confirm"/>
					<form:errors path="confirm"/>
				</p>
				<input type="submit" value="Register" />
			</form:form>
		</div>
		<div>
			<h2>Login</h2>
			<form:form action="/login" method="POST" modelAttribute="newLogin">
				<p>
					Email:<br>
					 <form:input type="email" path="email"/><br>
					 <form:errors path="email"/>
				</p>
				<p>
					Password:<br> 
					<form:input type="password" path="password"/>
					<form:errors path="password"/>
				</p>
				<input type="submit" value="Login">
			</form:form>
		</div>
	</div>
</body>
</html>