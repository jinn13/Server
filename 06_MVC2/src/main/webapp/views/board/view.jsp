<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<jsp:include page="/views/common/header.jsp" />

<style>
    section>div#board-write-container{width:600px; margin:0 auto; text-align:center;}
    section>div#board-write-container h2{margin:10px 0;}
    table#tbl-board{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-board th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-board td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
    div#comment-container button#btn-insert{width:60px;height:50px; color:white; background-color:#3300FF;position:relative;top:-20px;}
    
    /*댓글테이블*/
    table#tbl-comment{width:580px; margin:0 auto; border-collapse:collapse; clear:both; } 
    table#tbl-comment tr td{border-bottom:1px solid; border-top:1px solid; padding:5px; text-align:left; line-height:120%;}
    table#tbl-comment tr td:first-of-type{padding: 5px 5px 5px 50px;}
    table#tbl-comment tr td:last-of-type {text-align:right; width: 100px;}
    table#tbl-comment button.btn-delete{display:none;}
    table#tbl-comment tr:hover {background:lightgray;}
    table#tbl-comment tr:hover button.btn-delete{display:inline;}
    table#tbl-comment sub.comment-writer {color:navy; font-size:14px}
    table#tbl-comment sub.comment-date {color:tomato; font-size:10px}
</style>
<section id="content">   
	<div id="board-write-container">
		<h2>게시판</h2>
		<table id="tbl-board">
			<tr>
			<!-- 서비스에서 받은 값들을 이제 el로 적어준다. -->
				<th>글번호</th>
				<td>${ board.no }</td>
			</tr>
			<tr>
				<th>제 목</th>
				<td>${ board.title }</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${ board.writerId }</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${ board.readCount }</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<c:if test="${ empty board.originalFileName }">
					<!-- OriginalFileName없으면 span으로 - 찍기 -->
						<span> - </span>
					</c:if>
					<c:if test="${ !empty board.originalFileName }">
					<!-- OriginalFileName있으면 img와 파일명 출력하기 -->
						<img src="${ pageContext.request.contextPath }/resources/images/file.png" width="20" height="20"/>
						<!-- 파일에 공백있을경우 이름 좀 잘림.. 이건 fn:replace를 써서 공백을 제거하면 된다. -->
						<%-- 1. 실제 파일이 루트 하위에 존재할경우 다운로드 받는 방법
						<a href="${ pageContext.request.contextPath }/resources/upload/board/${ board.renamedFileName }"
							download=${ board.originalFileName }>
							<c:out value="${ board.originalFileName }" />
						</a>
						--%>
						
						<!-- 클릭하면 js의 fileDonwload 실행하게 만들기(매개값은 oname, rname보내주기) -->
						<a href="javascript:fileDownload('${board.originalFileName}', '${board.renamedFileName}')">
							<c:out value="${ board.originalFileName }" />
						</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>내 용</th>
				<td>${ board.content }</td>
			</tr>
			<%--글작성자/관리자인경우 수정삭제 가능 --%>
			<tr>
				<th colspan="2">
				<!--  로그인안하면 삭제, 수정 안보이게 하기+내가 쓴글에만 보이게 하기 -->
					<c:if test="${ ! empty loginMember && loginMember.id == board.writerId }">
						<button type="button" onclick="location.href='${ pageContext.request.contextPath }/board/update?no=${ board.no }'">수정</button>
						<button type="button" id="btnDelete">삭제</button>
					</c:if>
					
					<button type="button" onclick="location.href='${ pageContext.request.contextPath }/board/list'">목록으로</button>
				</th>
			</tr>
		</table>
		<div id="comment-container">
	    	<div class="comment-editor">
	    		<form action="" method="">
	    			<input type="hidden" name="boardNo" value="">
	    			<input type="hidden" name="writer" value="">
					<textarea name="content" cols="55" rows="3"></textarea>
					<button type="submit" id="btn-insert">등록</button>	    			
	    		</form>
	    	</div>
	    </div>
	    <table id="tbl-comment">
    	   	<tr class="level1">
	    		<td>
	    			<sub class="comment-writer">aa</sub>
	    			<sub class="comment-date">2021.05.07</sub>
	    			<br>
	    			컨텐츠
	    		</td>
	    		<td>
    				<button class="btn-delete">삭제</button>
	    		</td>
	    	</tr>
	    </table>
    </div>
</section>
<script>
	$(document).ready(()=>{
		$("#btnDelete").on("click", ()=>{
			
			if(confirm("정말로 게시글을 삭제하겠습니까?")){
				location.replace("${ pageContext.request.contextPath }/board/delete?no=${ board.no }");
			}
		})
		
	});
	
	function fileDownload(oname, rname){
		
		// 브라우저마다 동작 안할경우 있으므로 인코딩해서 넘겨주기
		// encodeURIComponent()
		// 	- 아스키문자(a~z, A~Z, 1~8, ..)는 그대로 전달하고 기타문자(한글, 특수문자 등)만 %XX(16진수 2자리)와 같이 변환된다.
		location.assign("${ pageContext.request.contextPath }/board/fileDown?oname="+encodeURIComponent(oname)+"&rname="+encodeURIComponent(rname));		
	}
</script>
<jsp:include page="/views/common/footer.jsp" />