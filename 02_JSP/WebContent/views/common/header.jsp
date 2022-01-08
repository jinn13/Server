<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.Period"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	LocalDateTime now=LocalDateTime.now();
	String today=now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh시 mm분 ss초"));
	// int days=Period.between(now.toLocalDate(), LocalDate.of(2022, 1, 1)).getDays();
	long days=ChronoUnit.DAYS.between(now.toLocalDate(), LocalDate.of(2022, 1, 1));
	//int result=1/0;
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<h1>지시자 테스트</h1>
		오늘은 <span style="color: tomato"><%=today %></span> 입니다.<br>
		2022년 새해까지..<%= days %>일 남았네요!
	</header>