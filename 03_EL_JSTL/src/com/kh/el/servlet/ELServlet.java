package com.kh.el.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.el.model.vo.Person;


@WebServlet("/el.do")
public class ELServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ELServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 서블릿에서 request, session, application객체를 가져와서
		// 데이터를 setAttribute()에 담아서 el.jsp에 전달
		HttpSession session = request.getSession();
		ServletContext application = request.getServletContext();
		
		//request Scope에 데이터를 저장
		request.setAttribute("classRoom", "한양반");
		request.setAttribute("student", new Person("성춘향", 20, '여'));
		// person 부분은 어떤 자료값 넣어도 autoboxing되므로 가능
		request.setAttribute("scope", "request 영역");
		
		//session Scope에 데이터를 저장
		session.setAttribute("academy", "집현전");
		session.setAttribute("teacher", new Person("세종대왕", 32, '남'));
		session.setAttribute("scope", "Session 영역");
		
		// Application Scope에 데이터를 저장
		application.setAttribute("scope", "Application 영역");
		
		// jsp의 forward를 자바에서 쓰는거라고 보면 됨
		request.getRequestDispatcher("views/el/el.jsp").forward(request, response);
	}
}
