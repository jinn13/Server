<%@page import="java.util.ArrayList"%>
<%@page import="com.kh.el.model.vo.Person"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>JSTL Core Library</h2>

	<h3>1. 변수</h3>
	<h4>1) 변수 선언(c:set)</h4>
	<p>
		변수를 선언하고 초기값을 대입하는 기능을 가진 태그이다. <br>
		변수 선언 시 scope를 지정할 수 있다. (지정하지 않으면 기본적으로 pageScope에 저장된다.)<br>
		▶ 사용방법<br>
		 - 변수 타입은 별도로 선언하지 않는다. <br>
		 - 초기값은 반드시 지정해야 한다. <br>
		 - c:set태그로 선언한 변수는 EL안에서 사용이 가능하고 스크립트릿 요소에서는 직접 사용할수 없다.<br>
	</p>	
	
	<%-- pageContext.setAttribute("num1", "10");	--%>
	<c:set var="num1" value="10" />
	<%-- request.setAttribute("num2", "20");	--%>
	<c:set var="num2" value="20" scope="request" />
	<c:set var="result" value="${num1+num2}" scope="session"/>
	<c:set var="array" scope="request">
		red, blue, yellow, pink, green
	</c:set>
	
	num1 변수값 : ${ pageScope.num1 } <br>
	num1 변수값(표현식) : <%= pageContext.getAttribute("num1") %> <br>
	num2 변수값 : ${ requestScope.num2 } <br>
	num2 변수값(표현식) : <%= request.getAttribute("num2") %><br>
	result 변수값 : ${ sessionScope.result } <br>
	result 변수값(표현식) : <%= session.getAttribute("result") %><br>
	array 배열 값 : ${ array }<br>
	
	<h4>2) 변수 삭제(c:remove)</h4>
	<p>
		지정한 변수를 모든 scope에서 검색해서 삭제한다. (scope 속성을 지정해서 특정 scope의 변수만 삭제 가능)
	</p>
	
	<!-- result 이름은 같아도 다른 영역객체에 저장되어있어서 OK -->
	<h5>※ 특정 scope에서 삭제</h5>
	<c:set var="result" value="99999" scope="request"/>
	삭제 전 result : ${result} <br>
	<c:remove var="result" scope="request"/>
	삭제 후 result : ${result} <br>
	
	<h5>※ 모든 scope에서 삭제</h5>
	<c:set var="result" value="99999" scope="request"/>
	삭제 전 result : ${result} <br>
	<c:remove var="result"/>
	삭제 후 result : ${result} <br>
	
	<h4>3) 변수출력(c:out)</h4>
	<p>데이터를 출력할 때 사용하는 태그이다.</p>	
	태그를 문자열로 출력 : <c:out value="<b>태그로 출력하기</b>"></c:out> <br>
	태그로 해석되어 출력 : <c:out value="<b>태그로 출력하기</b>" escapeXml="false"></c:out> <br>
	기본 값 출력 : <c:out value="${ result }" default="값이 없음"></c:out>
	
	<h2>2. 조건문</h2>
	<h4>1) IF(c:if)</h4>
	<p>
		Java의 if문과 비슷한 역할을 하는 태그이다. <br>
		태그 사용 시 조건은 test 속성에 지정해야 한다.<br> 
		이 때 조건식은 EL형식으로 기술해야 한다. 
	</p>
	
	<c:if test="${ num1>num2 }">
		num1은 num2보다 큽니다. 
	</c:if>
	<c:if test="${ num1<num2 }">
		num1은 num2보다 작습니다. 
	</c:if>	
	
	<h4>2) choose(c:choose, c:when, c:otherwise)</h4>
	<p>
		Java의 if else if문 또는 switch문과 비슷한 역할을 하는 태그이다.  <br>
		각 조건들은 c:choose 문의 하위 요소로 c:when을 통해서 작성한다. (default값으로는 c:otherwise)
	</p>

	<c:set var="count" value="0" />
	<c:choose>
		<c:when test="${ count eq 0 }">
			처음 뵙겠습니다.<br>
		</c:when>
		<c:when test="${ count eq 1 }">
			반갑습니다. 또 만났군요^^<br>
		</c:when>	
		<c:otherwise>
			안녕하세요. <br>
		</c:otherwise>
	</c:choose>


	<h3>3. 반복문</h3>
	<h4>1) forEach(c:forEach)</h4>
	<p>
		 Java의 for, for-in문에 해당하는 기능을 제공하는 태그이다.
	</p>
	<h5>▶ 기본 사용법</h5>
	<c:forEach var="i" begin="1" end="10" step="2">
		반복확인 : ${ i } <br>
	</c:forEach>
	
	<c:forEach var="i" begin="1" end="6">
		<h${ i }>EL로 반복문 사용</h${i}>
	</c:forEach>
	
	<c:forEach var="color" items="${ array }">
		<font color="${ color }">반복확인 : ${ color }</font><br>
	</c:forEach>
	<br><br>
	
	<%
		List<Person> list=new ArrayList<>();
		list.add(new Person("홍길동", 20, '남'));
		list.add(new Person("임꺽정", 21, '남'));
		list.add(new Person("성춘향", 17, '여'));
		list.add(new Person("이몽룡", 19, '남'));
		
		pageContext.setAttribute("list", list);
	%>
	
	<table border="1">
		<tr>
			<th>인덱스</th>
			<th>순번</th>
			<th>이름</th>				
			<th>나이</th>				
			<th>성별</th>				
			<th>isFirst</th>				
			<th>isLast</th>
		</tr>
		<c:forEach var="person" items="${ list }" varStatus="status">
				<tr>
					<td>${ status.index }</td>
					<td>${ status.count }</td>
					<td>${ person.name }</td>
					<td>${ person.age }</td>
					<td>${ person.gender }</td>
					<td>${ status.first }</td>
					<td>${ status.last }</td>
				</tr>
		</c:forEach>				
	</table>
	
	<h4>2) forTokens(c:forTokens)</h4>
	<p>
		문자열에 포함된 구분자를 통해 토큰을 분리해서 반복 처리하는 태그이다.<br>
		Java의 String.split또는 StingTokenizer와 비슷한 기능을 한다.
	</p>

	<c:set var="device">
		컴퓨터,노트북,핸드폰.TV/에어컨,냉장고.세탁기
	</c:set>
	
	<ul>
		<c:forTokens var="d" items="${device }" delims=",./">
		<li>${ d }</li>
		</c:forTokens>
	</ul>

	<ul>
		<c:forTokens var="color" items="pink aqua tomato yellow lime blue" delims=" ">
		<li style="background-color: ${ color }">${ color }</li>
		</c:forTokens>
	</ul>

	<h3>4. URL(c:url)</h3>
	<p>
		URL 경로를 생성하고, 해당 URL의 param속성을 선언하여 쿼리 스트링을 정의할 수 있는 태그이다. <br>
		해당 태그를 통해 URL 경로와 관련된 쿼리 스트링 값을 미리 결정하여 이를 제어할 수 있다.  
	</p>
	
	<c:url var="url" value="urlPage.jsp">
		<c:param name="pName" value="아이폰 12 미니"></c:param>
		<c:param name="pCount" value="1"></c:param>
		<c:param name="option" value="화이트"></c:param>
		<c:param name="option" value="64기가"></c:param>
	</c:url>

	<a href="${ url }">이동</a>





	<br><br><br><br><br><br><br><br>	
	<br><br><br><br><br><br><br><br>	
	<br><br><br><br><br><br><br><br>	
	<br><br><br><br><br><br><br><br>	
	<br><br><br><br><br><br><br><br>	
</body>
</html>