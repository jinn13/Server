package com.kh.mvc.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.util.PageInfo;


@WebServlet("/board/list")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = new BoardService();
       
    public ListServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 게시글 리스트 조회
		// 2. 페이징 처리
		int page = 0;
		int listCount = 0;
		PageInfo pageInfo = null;
		List<Board> list = null;
		
		
		// 2. 500에러 삼항연산자로 해결하기
		// page=request.getParameter("page")!=null? Integer.parseInt(request.getParameter("page")) : 1;
		
		try {
			// 1. interger에 null을 넘겨서 500에러가 뜬다. 예외처리로 해결(삼항연산자로도 해결가능)
			page=Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			page=1;
		}
		
		listCount= service.getBoardCount();
		pageInfo = new PageInfo(page, 10, listCount, 10);
		list=service.getBoardList(pageInfo);
		
		System.out.println(listCount);
		System.out.println(list);
		
		// ﻿﻿request 객체를 가져와서 데이터를 setAttribute()에 담아서 foward로 list.jsp에 넘겨준다.
		request.setAttribute("pageInfo", pageInfo);		
		request.setAttribute("list", list);		
		request.getRequestDispatcher("/views/board/list.jsp").forward(request, response);
	}
}
