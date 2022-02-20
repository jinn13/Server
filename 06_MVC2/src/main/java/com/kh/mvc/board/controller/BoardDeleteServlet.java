package com.kh.mvc.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.board.model.service.BoardService;


@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = new BoardService();
			
    public BoardDeleteServlet() {
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 로그인체크 & 본인 게시글 삭제 여부 확인(직접 적용시켜보세요.)
    	// 로그인체크는 문제없을텐데, 로그인되었으면 dB에서 no값 가지고 게시글 조회한 다음 게시글 작성자랑 로그인 체크할때 로그인 멤버에 있는 id랑 같은지 체크하면 되겠죠?
    	int result=0;    	
    	int no= Integer.parseInt(request.getParameter("no"));
    	System.out.println("NO : "+no);
    	
    	result=service.delete(no);
    	
    	// 0보다 크면 게시글 작성에 성공한거니.. 
		if(result>0) {
    		request.setAttribute("msg", "게시글 삭제 성공!");
    		request.setAttribute("location", "/board/list");
		}else {
			// 게시글 등록 실패
    		request.setAttribute("msg", "게시글 삭제 실패!");
    		request.setAttribute("location", "/board/list");
    	
        	request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
    }
}
