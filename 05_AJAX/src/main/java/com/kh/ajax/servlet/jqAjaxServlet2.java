package com.kh.ajax.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.ajax.model.vo.User;

@WebServlet("/jqAjax2.do")
public class jqAjaxServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public jqAjaxServlet2() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int userNo = Integer.parseInt(request.getParameter("userNo"));
    	
    	// 사용자 정보가 저장되어있는 List 생성
    	List<User> list=new ArrayList<>();
    	
    	list.add(new User(1, "김철수", 30, '남'));
    	list.add(new User(2, "김영희", 30, '여'));
    	list.add(new User(3, "영심이", 16, '여'));
    	list.add(new User(4, "왕경태", 16, '남'));
    	list.add(new User(5, "감자킹", 20, '남'));
    	
    	User findUser = list.stream()
    						.filter(user->user.getNo() == userNo)
    						.findFirst()
    						.orElse(null);
    	
    	response.setContentType("application/json; charset=UTF-8");
    	
    	// 라이브러리를 쓰기 전에 JSON 만들어서 테스트
    	// String result="{\"no\":1, \"name\" : \"홍길동\", \"age\" : 30 \"gender\":\"남\"}";
    	// response.getWriter().print(result);
    	
    	// 1. 처음 만든 버젼
    	// response.getWriter().print(new Gson().toJson(findUser));
    	// - 변수에 담아 사용한 버젼
    	// String json=new Gson().toJson(findUser);
    	// System.out.println(json);
    	// response.getWriter().print(json);
    	
    	// 2. 읽기 편한 형태로 만들어주는 버젼
    	// String json2 = new GsonBuilder().setPrettyPrinting().create().toJson(findUser);
    	// System.out.println(json2);
    	// response.getWriter().print(json2);
    	
    	// 3. 한번에 버젼 : Gson만들때 객체와 스트림 라이터 넘겨주면 json만든 다음에 두번째 스트림을 통해 클라이언트로 바로 보냄
    	   new Gson().toJson(findUser, response.getWriter());
    	
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
