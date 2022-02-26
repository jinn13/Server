package com.kh.mvc.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Board;

@WebServlet("/board/view")
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BoardService service = new BoardService();
    public ViewServlet() {
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 2. 게시글 정보 no로 받아 조회하기(int로 형변환해줘야함)
    	// 참고 : url로 직접 입력시 에러페이지 뜰때 처리하는건 앞에 내용 참고하기
    	//       해당 파라미터가 없으면 null이니까, 혹은 숫자형태가 아니면 500 에러가 뜬다. 
    	int no= Integer.parseInt(request.getParameter("no"));
    	System.out.println("사용자한테 받은 NO값 : "+no);
    	
    	// 3. 서비스 객체 ↑에 만들었음, 그리고 서비스에서 받은 결과를 보드객체에 넣어줄것임
    	Board board = service.findBoardByNo(no);
    	
    	
    	// 서비스한테 받은 값 한번 확인차 출력하기
    	System.out.println("조회된 board객체 "+board);
    	// dao에서 받아온 자료를 이제 뷰.jsp로 가서 각자 원하는 영역에 출력하도록 한다. 
    	
    	// 4. 영역객체에다 attribute로 담아주어야 한다. 
    	request.setAttribute("board", board);
    	
    	// 1. view.jsp로 포워딩하기
    	// 리다이렉트랑 포워딩을 안하면 안녕하세요 누르면 하얀화면만 나옴. 헷갈리면 jsp복습하기
    	// 사이에 적어준 페이지로 요청정보랑 응답정보를 같이 줄것임
    	request.getRequestDispatcher("/views/board/view.jsp").forward(request, response);
    	
	}

}
