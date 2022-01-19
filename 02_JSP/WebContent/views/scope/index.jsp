<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영역 객체</title>
</head>
<body>
	<h2>영역 객체</h2>
	
	<h3>session 영역과 application 영역의 비교</h3>
	<%
		// 키(userName)와 value(감자깡)로 저장되었고 value는 오브젝트임
		
		// application이 프로그램이 실행되는동안 scope유지, jsp에서 데이터를 공유할수 있다.  
		// session은 브라우저 다시 열어서 접근하면 기존거는 유지가 안되고 새로만들어졌기에 null
		// session은 브라우저 종료되기전까지 데이터를 공유할수있다. (브라우저 다시 열면 기존거는 유지가 안되서 null이 뜬다?)
		application.setAttribute("userName", "감자깡");
		session.setAttribute("address", "감자칩 왕국");
	%>
	
	<a href="scopeTest1.jsp">확인</a>
	
	<h3>page 영역과 request영역</h3>
	
	<a href="scopeTest2.jsp">vies Details</a>

</body>
</html>