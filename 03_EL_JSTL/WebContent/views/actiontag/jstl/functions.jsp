<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>JSTL Functions Library</h2>
	<c:set var="str" value="java oracle HTML CSS jQuery Servlet JSP AJAX"></c:set>
	
	str : ${ str }
	
	<ul>
		<li>문자열의 길이 : ${ fn:length(str) }</li>
		<li>모두 대문자 : ${ fn:toUpperCase(str) }</li>
		<li>모두 소문자 : ${ fn:toLowerCase(str) }</li>
		<li>CSS의 시작 위치는 : ${ fn:indexOf(str, 'CSS') }</li> <!-- ""도 가능함 -->
		<li>java를 javascript로 변경 : ${ fn:replace(str, 'java', "javascript") }</li>
	</ul>
	
	원본 str에는 변화 없음 : ${ str }
	
</body>
</html>