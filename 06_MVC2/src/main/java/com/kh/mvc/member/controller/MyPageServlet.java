package com.kh.mvc.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.vo.Member;

@WebServlet("/member/myPage")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyPageServlet() {
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 로그인 했는지 안했는지 확인하는 코드(아래 두줄)
    	// false 왜 넣어주는가?? 만약 false아니면 새로 만들어서 주기 때문이다. 
    	HttpSession session = request.getSession(false);
    	// 세션이 만들어지니 그것도확인해야함
    	Member loginMember = session != null ? (Member)session.getAttribute("loginMember") : null;
    	
    	if(loginMember != null) {
    		// 데이터베이스에서 페이지 요청할 시점에 멤버객체를 가져와서 request영역에 담아 포워딩 시킨다. 
    		request.getRequestDispatcher("/views/member/myPage.jsp").forward(request, response);
    		
    	}else {
    		request.setAttribute("msg", "잘못된 접근입니다.");
    		request.setAttribute("location", "/");
    		
    		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
    	}		
    }
}
