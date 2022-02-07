<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.kh.el.model.vo.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>EL 연산자</h2>
	<h3>산술 연산</h3>
	10 더하기 5 = ${ 10+5 }	<br>
	10 빼기 5 = ${ 10-5 }<br>
	10 곱하기 5 = ${ 10*5 }<br>
	10 나누기 5 = ${ 10/5 } 또는 ${ 10 div 5 }<br>
	10 나누기 7의 나머지 = ${ '10'%7 } 또는 ${ 10 mod 7 }<br>

	<h3>객체 비교 연산</h3>
<%
	String str1="하이";
	String str2= new String("하이");
	Person p1= new Person("홍길동", 20, '남');
	Person p2= new Person("임꺽정", 40, '남');
	
	// Page Scope에 데이터 담기
	// EL은 영역 객체에 attribute로 저장된 값에 접근해서 가져오기 때문에
	// 스크립트릿에 선언된 변슈는 attribute로 저장된 값이 아니라서 가져올 수 없다. 

	pageContext.setAttribute("str1", str1);
	pageContext.setAttribute("str2", str2);
	pageContext.setAttribute("p1", p1);
	pageContext.setAttribute("p2", p2);
%>
	<%-- 
	str1 = ${ str1 } <br>
	str2 = ${ str2 } <br>
	str1과 str2는 같은가? = ${ str1 eq str2 }
	--%>
	
	<table border="1">
		<tr>
			<th>비교식</th>
			<th>표현식</th>
			<th>EL</th>
		</tr>
		<tr>
			<td>str1 == str2</td>
			<td><%= str1 == str2 %></td>
			<!-- EL의 연산은 equals()와 같은 동작을 한다. -->
			<td>${ str1 == str2 } 또는 ${ str1 eq str2 }</td>
		</tr>
		
		<tr>
			<td>str1 != str2</td>
			<td><%= str1 != str2 %></td>
			<!-- 위는 주소값이 다르므로 true,  -->
			<td>${ str1 != str2 } 또는 ${ str1 ne str2 }</td>
		</tr>
		
		<tr>
			<td>p1==p2</td>
			<td><%= p1 == p2 %></td>
			<td>${ p1==p2 } 또는 ${ p1 eq p2 }</td>
		</tr>
		
		<tr>
			<td>p1!=p2</td>
			<td><%= p1 != p2 %></td>
			<td>${ p1!=p2 } 또는 ${ p1 ne p2 }</td>
		</tr>
	</table>
	<h3>숫자형 자동 형변환</h3>
<%
	pageContext.setAttribute("big", 10);
//	pageContext.setAttribute("big", "10"); // 숫자형으로 자동형변환된다.
//	pageContext.setAttribute("big", "a");  // 이건 문자열이라 에러
//	pageContext.setAttribute("big", 'a');  // 단일문자만 넘기면 아스키코드값으로 넘어감
	pageContext.setAttribute("small", 3);
%>
	<!-- Object 타입끼리는 연산 성립되지 않는다. (그래서 강제 형변환을 해야한다) 
	강제형변환으로 (Integer)로 오토박싱 하거나 (int)하면 된다. -->
	scriptlet : <%= (Integer)pageContext.getAttribute("big") + (Integer)pageContext.getAttribute("small") %> <br>

	<!-- El은 영역 객체에 속성으로 담긴 object타입 자동으로 인식하여 형변환 후 연산을 처리한다. -->
	EL : ${ big + small } <br>
	
	<!-- 자동 형변환으로 인해서 비교 연산도 가능하다.  -->
	big&gt;small : ${ big > small } 또는 ${ big gt small }<br>
	big&lt;small : ${ big < small } 또는 ${ big lt small }<br>
	big&gt;=small : ${ big >= small } 또는 ${ big ge small }<br>	
	big&lt;=small : ${ big <= small } 또는 ${ big le small }<br>	
	
	<h3>객체가 null 또는 비어있는 체크하는 연산자</h3>
	<%
//		String str3=null;
		String str3="a";
//		String str3="";
		List<String> list=new ArrayList<>();
		
		pageContext.setAttribute("str3", str3);
		pageContext.setAttribute("list", list);
	%>
	
	Scriptlet : <%= str3 == null %>, <%= list.isEmpty() %>
	<br><br>
	EL : ${ empty str3 }, ${ not empty str3 }, ${ empty list }
	
	
	<h3>논리 연산자 / 부정 연산자</h3>
	▶ 논리 연산자<br>
	and 연산 : ${ true && false } 또는 ${ true and false }<br>
	or 연산 : ${ true || false } 또는 ${ true or false }<br>
	
	▶ 부정연산자<br>
	${ !true } 또는 ${ not true }<br>
	${! (big>small) } 또는 ${ not(big > small) }<br>
	
</body>
</html>