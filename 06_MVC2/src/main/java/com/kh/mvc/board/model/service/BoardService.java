package com.kh.mvc.board.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.mvc.board.model.dao.BoardDao;
import com.kh.mvc.board.model.vo.Board;
import static com.kh.mvc.common.jdbc.JDBCTemplate.*;
import com.kh.mvc.common.util.PageInfo;

public class BoardService {

	private BoardDao dao=new BoardDao();
	
	public int getBoardCount() {
		int count=0;
		// 1. connection 가져오기
		Connection connection=getConnection();
		// 2. dao객체한테 getBoardCount를 통해 게시글 갯수 받아오기
		count=dao.getBoardCount(connection);
		// 3. connection 클로즈하기
		close(connection);
		
		return count;
	}
	
	public Board findBoardByNo(int no) {
		// 1. 보드로 리턴해야하니 보드객체 만들기 커넥션도 만들기
		Board board = null;
		Connection connection=getConnection();
		// 2. DB에서 게시글을 조회해야함. 직접 접근안하니 DAO를 통해서 접근한다. 이 결과는 board객체에 넣는다. 
		board= dao.findBoardByNo(connection, no);
		
		// 3. 커넥션 닫아줌
		close(connection);
		// 4. board리턴해주기
		return board;
	}

	public List<Board> getBoardList(PageInfo pageInfo) {
		List<Board> list = null;
		Connection connection=getConnection();
		list = dao.findAll(connection, pageInfo);
		
		close(connection);
		
		return list;
	}

	public int save(Board board) {
		int result=0;
		Connection connection=getConnection();
		
		if(board.getNo()!=0) {
            // no이 있으므로 update 메소드 실행(없으면 새 글이니 아래의 insert문 실행)
			result = dao.updateBoard(connection, board);
		} else {
			result = dao.insertBoard(connection, board);
		}
		
		if(result>0) {
			commit(connection);
		}else {
			rollback(connection);
		}
		close(connection);
		
		return result;
	}

	public int delete(int no) {
		// Delete이니 결과값은 정수값
		int result=0;
		// 커넥션 필요(클로즈도 잊지말고 마지막에 적어주자)
		Connection connection=getConnection();
		
		// dao한테 상태값만 바꾸라고 시키고 결과값 받기
		result=dao.updateStatus(connection, no, "N");
		
		// 상태값 바꾸는데 성공 또는 실패하면 커밋or롤백하기 
		if(result>0) {
			commit(connection);
		}else {
			rollback(connection);
		}
		
		close(connection);
		
		return result;
	}

}
