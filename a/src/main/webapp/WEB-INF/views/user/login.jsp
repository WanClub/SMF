<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%

request.getSession(true).setAttribute("name", "lxj");

%>


<%=request.getSession().getAttribute("name") %>

<form action="submitLogin.shtml" method="post">

<input name="email">

<input name="pswd">

<input name="rememberMe" value='false' type="hidden" />

<input type="submit" value="submit" />

</form>




</body>
</html>