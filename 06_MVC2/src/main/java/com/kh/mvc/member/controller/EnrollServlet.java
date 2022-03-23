package com.kh.mvc.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.member.model.service.MemberService;
import com.kh.mvc.member.model.vo.Member;


@WebServlet(name="enroll", urlPatterns = "/member/enroll")
public class EnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 얘도 얘가 비즈니스 로직을 처리하는게 아님. 멤버서비스 객체에 전달예정
	private MemberService service = new MemberService();
       
    public EnrollServlet() {
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원가입 페이지로 전환해주는 기능
    	request.getRequestDispatcher("/views/member/enroll.jsp").forward(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// System.out.println(request.getParameter("userId"));
		Member member = new Member();
		
		// 회원번호와 가입날짜등은 왜 안가져오는가? 시퀀스로 생성할거라서!
		member.setId(request.getParameter("userId"));
		member.setPassword(request.getParameter("userPwd"));
		member.setName(request.getParameter("userName"));
		member.setPhone(request.getParameter("phone"));
		member.setEmail(request.getParameter("email"));
		member.setAddress(request.getParameter("address"));
//		member.setHobby(request.getParameterValues("hobby"));
		
		System.out.println(member);
		System.out.println(String.join(",", request.getParameterValues("hobby")));
        // 배열을 ,로 구분해서 하나의 문자열로 출력
		
		// 앤 지금 무조건 0 리턴됨 그래서 실패뜰것임
		int result = service.save(member);
		
		// 0보다 크다면 행이 존재한다는 의미
		if(result>0) {
			// 회원 가입 완료
    		request.setAttribute("msg", "회원 가입 성공!");
    		request.setAttribute("location", "/");
		}else {
			// 회원 가입 실패
    		request.setAttribute("msg", "회원 가입 실패!");
    		request.setAttribute("location", "/member/enroll");
    		// 실패하면 다시 회원가입 진행하게끔 url설정
		}
		
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
	}
}
