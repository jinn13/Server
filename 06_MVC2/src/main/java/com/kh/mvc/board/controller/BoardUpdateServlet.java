package com.kh.mvc.board.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.util.FileRename;
import com.oreilly.servlet.MultipartRequest;


@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

    public BoardUpdateServlet() {
    }


    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 로그인 체크&본인게시글 수정 여부 확인(직접 적용해보기)
    	// 수업에선 다 체크되었다고 가정하고 할 예정

    	// 1. no값 받아올거니 no값 정수형으로 받기
    	int no = Integer.parseInt(request.getParameter("no"));
    	// 2. 게시글에 대한 정보를 가져와서 보여줘야 하니 board객체 가져오기
    	Board board=null;
    	
    	// 3. board에 서비스객체의 findBoardByNO메소드 실행시켜 결과값 받기
    	// BoardService의 경우 서블릿마다 동일한 객체니 호출될때만 만들어서 메소드 실행하고 끝나면 소멸되도록 하는게 좀 더 좋음
    	board = new BoardService().findBoardByNo(no);
    	
    	// 4. jsp에 데이터를 넘겨준다. 그리고 포워딩 시킨다. 
    	request.setAttribute("board", board);
    	request.getRequestDispatcher("/views/board/update.jsp").forward(request, response);
	}

    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 로그인 체크 & 본인게시글 수정여부 확인(직접 적용시켜보세요.)
    	// 이렇게 중복되는 경우 filter로 체크해도 좋겠죠??
    	
    	int result=0;
    	Board board=null;
    	// 새로 서버에 업로드될 파일 이름들
    	String originalFileName = null;
    	String renamedFileName = null;
    	String path = getServletContext().getRealPath("/resources/upload/board");
    	int maxSize = 10485760;	
    	String encoding="UTF-8";
    
    	// 매개값으로 리퀘스트객체, 저장될 위치, 최대사이즈, 인코딩방식, 리네임정책..
    	MultipartRequest mr = new MultipartRequest(request, path, maxSize, encoding, new FileRename());
    	
    	board=new Board();
    	
    	// 사용자가 보낸 정보를 통해 borad객체 생성
    	// no값만 받아서 기존값 받아오고 3개 title, writer, content만 바꿔도 되지만.. 여기선 이렇게!
    	board.setNo(Integer.parseInt(mr.getParameter("no")));
    	board.setTitle(mr.getParameter("title"));
    	board.setWriterId(mr.getParameter("writer"));
    	board.setContent(mr.getParameter("content"));
    	// 기존파일의 이름(hidden으로 숨겨둔거)
    	board.setOriginalFileName(mr.getParameter("originalFileName"));
    	board.setRenamedFileName(mr.getParameter("renamedFileName"));
    	
    	// 사용자가 보내준 변경할 파일명
    	originalFileName = mr.getOriginalFileName("upfile");
    	renamedFileName = mr.getFilesystemName("upfile");
    	
    	// 사용자가 새로운 파일을 업로드했다면?
    	if(originalFileName != null && !originalFileName.equals("")) {
//    		String deleteFilePath=path+"/"+board.getRenamedFileName();
    		File file=new File(path+"/"+board.getRenamedFileName());
    		
    		if(file.exists()) {
    			file.delete();
    		}
    		
    		board.setOriginalFileName(originalFileName);
        	board.setRenamedFileName(renamedFileName);
    	} 
    	
    	// 받는 값은 정수값
    	result = new BoardService().save(board);
    	
    	if(result > 0) {
    		request.setAttribute("msg", "게시글 수정 성공");
    	} else {
    		request.setAttribute("msg", "게시글 수정 실패");
    	}
    	
    	request.setAttribute("location", "/board/view?no=" + board.getNo());
    	request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);  	
	}

}
