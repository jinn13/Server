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

import com.kh.mvc.common.wrapper.EncryptPasswordWrapper;

@WebFilter(filterName="encryptFilter", servletNames = {"login", "enroll", "updatePwd"}) // 로그인과 회원가입시 암호화해줌
public class EncryptFilter implements Filter {

    public EncryptFilter() {
    }

    public void init(FilterConfig fConfig) throws ServletException {	
    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// System.out.println("EncryptFilter 호출");
		//String message = EncryptUtil.oneWayEnc(request.getParameter("password"), "SHA-256");
		//System.out.println(request.getParameter("password")+" : "+message);
		
		// request 의 파라미터 값을 직접 변경할 수 없기 때문에 requestWrapper를 생성한다. 
		EncryptPasswordWrapper wrapper = new EncryptPasswordWrapper((HttpServletRequest) request);
		
		// request 대신에 생성한 wrapper 객체를 넘겨준다. 
		
		chain.doFilter(wrapper, response);
	}

	public void destroy() {
	}
}
