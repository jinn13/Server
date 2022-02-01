<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>jsp:forward 액션 태그</h2>
	<p>
		현재 페이지의 요청과 응답에 대한 처리권을 다른 페이지로 넘기는 액션태그이다. 
	</p>
	
	<!-- 포워딩 전에 응답객체 쓴 내용은 forwarding 되면서 버퍼가 지워져서 아무런 효과가 없다. -->
	<script>alert('안녕하세요.')</script>
	
	<!-- 보통 이렇게 한번에 보내는데..쌓아둔다고 해도, 전에 쌓아둔 버퍼에 쌓아둔게 지워져서 효과가 없다..
	예전에 배운 보조스트림과 같은 느낌. 
	out.print("<script>alert('안녕하세요.')</script>");
	 -->
	
	<%--
	원래는 이렇게 자바코드방식으로 forward 시켰음
	<%
		pageContext.forward("forwardPage.jsp");
	%>
	 --%>
	
	<jsp:forward page="forwardPage.jsp" />
	<%--
	<jsp:forward page="forwardPage.jsp"></jsp:forward>
	파라미터가 있으면 닫는 태그 있는 이 방식으로 적는다.
	 --%>
</body>
</html>