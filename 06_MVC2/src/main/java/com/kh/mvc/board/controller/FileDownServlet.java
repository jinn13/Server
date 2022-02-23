package com.kh.mvc.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/board/fileDown")
public class FileDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FileDownServlet() {
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String oname = request.getParameter("oname");
    	String rname = request.getParameter("rname");
    	
    	System.out.println("oname : "+oname);
    	System.out.println("rname : "+rname);
    	
    	// 1. 전송할 파일에 대한 경로와 파일명을 가져온다. 
		String path = getServletContext().getRealPath("/resources/upload/board");
		String filePath = path + "/" + rname;
    	
    	// 2. 물리적인 저장소에 저장되어있는 파일을 메모리로 가져온다. 
    	File downFile = new File(filePath); // 실제로 저장된 파일네임
    	
    	// 3. 메모리로 가져온 파일에 입력 스트림을 연결
    	// 읽거나 쓰려면 인풋스트림 필요하고 성능향상을 위해 버퍼도 같이 연결해준다. 
    	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downFile));
    	
    	// 4. 클라이언트로 내보낼 출력 스트림을 연결(우리가 아웃풋 스트림 따로 안만들어도 된다.)
    	// 기존엔 writer로 출력했었다면 이번엔 outputstream으로 가져와서 출력...
    	BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
    	
    	// 5. 브라우저별 인코딩 처리
    	// 아까 view에서 퍼센트 인코딩 했던 것처럼, 서버에서 내려줄 때도 거기에 맞게 인코딩 해줘야 함(안하면 파일명 깨짐)
		String downName = null; 
		
		// IE를 위한 처리부분
		String userAgent = request.getHeader("user-agent"); //IE를 위해서 얻어와야함
		boolean isMSIE = userAgent.indexOf("MSIE") != -1 || userAgent.indexOf("Trident") != -1;
		
		if(isMSIE) { 
			// 내일 다시 이어서 설명예정
		} else {
			// 아까 view에서와 달리 반대로 인코딩해준다. 
			downName = new String(oname.getBytes("UTF-8"), "ISO-8859-1");			
		}
				
		System.out.println(downName);
		System.out.println(userAgent);
    	
    	// 6. 응답 메세지 작성(html이 아닌 file을 보내니 아래처럼 작성)
    	// octet-stream 모든 종류의 2진 데이터를 뜻한다. 
    	response.setContentType("application/octet-stream");
    	// 파일 링크를 클릭했을 때 다운로드 화면이 출력되게 처리하는 부분
    	response.setHeader("Content-Disposition", "attachment;filename="+downName);
    	
    	// 7. 파일 출력(전송)하기
    	int read=-1;
    	while((read=bis.read())!=-1) {
    		bos.write(read);
    	}
    	
    	// 8. 역순으로 닫아준다. 
    	bos.close();
    	bis.close();
    	
	}
}
