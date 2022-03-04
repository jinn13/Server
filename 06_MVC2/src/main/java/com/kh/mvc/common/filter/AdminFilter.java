package com.kh.mvc.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.vo.Member;

// 1. ↓ 루트(/)에서 admin으로 시작하는 모든 url에 대해서 필터를 태운다는 의미 
@WebFilter("/admin/*")
public class AdminFilter implements Filter {
	public AdminFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 관리자 페이지 권한 처리
		//  - 관리자가 아닌 경우에는 페이지를 볼 수 없도록 처리
		HttpSession session = ((HttpServletRequest)request).getSession(false); // 2. 세션 가져온다. 
		Member loginMember = (Member)(session.getAttribute("loginMember"));    // 3. loginMember 가져온다. 
		
		// 4. loginMember가 null이거나 관리자 계정이 아니라면 
		if(loginMember == null || !loginMember.getRole().equals("ROLE_ADMIN")) {
			request.setAttribute("msg","잘못된 경로로 접근하셨습니다."); // 5. 메세지 객체 만들고
			request.setAttribute("location","/");				// 6. 보내버릴 location 지정하고..
			// 7. msg.jsp로 포워딩 시킨다. 
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);

			// 8. 리턴시킨다. 
			return;
		}
		
		// 참고 : 원래는 chain메소드로 다음 필터, 또는 다음 서블릿으로 넘기는데 위에 return문으로 리턴시켰으니 아래 문구는 실행되지 않음
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
