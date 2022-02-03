<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String year="2021";
	String pName=request.getParameter("pName"); // 이렇게 불러서 써도 되고...
	
	request.setCharacterEncoding("UTF-8"); // 한글깨져서 인코딩
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Include Page</h2>
	

	Include Page의 year 변수 값 : <%= year %><br><br>
	
	pName (EL로 출력) : ${ param.pName } <br><!-- 이렇게 써도 되고..아래처럼 해도 되고... -->
	pName (스트립트릿으로 출력) : <%= pName %> <br> 

</body>
</html>