package com.kh.mvc.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.util.PageInfo;

import static com.kh.mvc.common.jdbc.JDBCTemplate.*;

public class BoardDao {

	public int getBoardCount(Connection connection) {
		// 1. 기본적으로 필요한 애들 생성
		int count = 0;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String query="SELECT COUNT(*) FROM BOARD WHERE STATUS='Y'";
		
		try {
			// 2. connection에서 prepareStatment(query) 얻어옴
			// 3. 예외처리 try/catch로 감싸줌
			pstmt=connection.prepareStatement(query);
			// 4. resultset으로 반환해주는 execeteQuery실행, rs에 값 담기.
			rs=pstmt.executeQuery();
			// 5. next하면 첫번째 행 가리킴, 거기서 getXXX(컬럼명 or 컬럼순번)으로 값 가져옴
			if(rs.next()) {
				count=rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			// 6. 역순으로 close해줌. 
			close(rs);
			close(pstmt);			
		}
		return count;
	}
	
	
	
	public Board findBoardByNo(Connection connection, int no) {
		// 1. 보드값 리턴하게 만들어준다.(그 외 필요한 객체들 생성) 
		Board board = null;
		PreparedStatement pstmt = null; // 위치홀더 사용하기 위한 pstmt
		ResultSet rs = null;   			// select문은 결과가 resultset임
		// 2. 쿼리문 넣어주기
		String query= // 줄마다 끝에 꼭 공백들어갔는지 확인()
	            "SELECT "
	            +    "B.NO, "
	            +   "B.TITLE, "
	            +   "M.ID, "
	            +   "B.READCOUNT, "
	            +   "B.ORIGINAL_FILENAME, "
	            +   "B.RENAMED_FILENAME, "
	            +   "B.CONTENT, "
	            +   "B.CREATE_DATE, "
	            +   "B.MODIFY_DATE "
	            + "FROM BOARD B "
	            + "JOIN MEMBER M ON(B.WRITER_NO = M.NO) "
	            + "WHERE B.STATUS = 'Y' AND B.NO=?";
		
		try {
			// 3. connection객체에서 query를 통해 pstmt얻어오기
			pstmt=connection.prepareStatement(query);
			// 4. 쿼리문 마지막의 위치홀더 값 설정
			pstmt.setInt(1, no);
			
			// 5. 쿼리문 실행시켜서 rs에 담아준다. 
			rs=pstmt.executeQuery();
			
			// rs.next가 true면..(실제 데이터를 가리킨다면)
			if(rs.next()) {
				// 6. board객체 하나 만들어주고
				board = new Board();
				
				// 7. 조회된 하나의 행의 값을 자바에서 쓸수있도록 board객체만들고 거기에 데이터 set해준다.  
				board.setNo(rs.getInt("NO")); // 받아온 no도 되고 쿼리로 받아온 NO도 된다. 
				board.setTitle(rs.getString("TITLE")); // 괄호안엔 쿼리문의 이름 가져오기
				board.setWriterId(rs.getString("ID"));
				board.setReadCount(rs.getInt("READCOUNT"));
				board.setOriginalFileName(rs.getString("ORIGINAL_FILENAME"));
				board.setRenamedFileName(rs.getString("RENAMED_FILENAME"));
				board.setContent(rs.getString("CONTENT"));
				board.setCreateDate(rs.getDate("CREATE_DATE"));
				board.setModifyDate(rs.getDate("MODIFY_DATE"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rs); //rs가 마지막에 생겼으니 가장먼저 닫기
			close(pstmt);
			// DAO에서는 connection 안닫아줘도 된다.(서비스에서 닫음) 
		}
		return board;
	}
	

	public List<Board> findAll(Connection connection, PageInfo pageInfo) {
		List<Board> list=new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query=
				// STATUS 옆에 공백 하나 있어야 함. 
				"SELECT RNUM, NO, TITLE, ID, CREATE_DATE, ORIGINAL_FILENAME, READCOUNT, STATUS "
				+ "FROM ("
				+    "SELECT ROWNUM AS RNUM, "
				+           "NO, "
				+ 			"TITLE, "
				+ 			"ID, "
				+ 			"CREATE_DATE, "
				+ 			"ORIGINAL_FILENAME, "
				+  			"READCOUNT, "
				+     		"STATUS "
				+ 	 "FROM ("
				+ 	    "SELECT B.NO, "
				+ 			   "B.TITLE, "
				+  			   "M.ID, "
				+ 			   "B.CREATE_DATE, "
				+ 			   "B.ORIGINAL_FILENAME, "
				+ 			   "B.READCOUNT, "
				+ 	   		   "B.STATUS "
				+ 		"FROM BOARD B "
				+ 		"JOIN MEMBER M ON(B.WRITER_NO = M.NO) "
				+ 		"WHERE B.STATUS = 'Y' ORDER BY B.NO DESC"
				+ 	 ")"
				+ ") WHERE RNUM BETWEEN ? and ?";
				
		try {
			pstmt=connection.prepareStatement(query);
			pstmt.setInt(1, pageInfo.getStartList());
			pstmt.setInt(2, pageInfo.getEndList());
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				// board에 담아주기
				Board board=new Board();
				
				board.setNo(rs.getInt("NO"));
				board.setRowNum(rs.getInt("RNUM"));
				board.setWriterId(rs.getString("ID"));
				board.setTitle(rs.getString("TITLE"));
				board.setCreateDate(rs.getDate("CREATE_DATE"));
				board.setOriginalFileName(rs.getString("ORIGINAL_FILENAME"));
				board.setReadCount(rs.getInt("READCOUNT"));
				board.setStatus(rs.getString("STATUS"));
				
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);		
		}
		return list;
	}

	public int insertBoard(Connection connection, Board board) {
		int result=0;
		PreparedStatement pstmt = null;
		String query="INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL,?,?,?,?,?,DEFAULT,DEFAULT,DEFAULT,DEFAULT)";
		
		try {
			pstmt=connection.prepareStatement(query);
			pstmt.setInt(1, board.getWriterNo());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getOriginalFileName());
			pstmt.setString(5, board.getRenamedFileName());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	public int updateBoard(Connection connection, Board board) {
		int result=0;
		PreparedStatement pstmt=null;
		String query="UPDATE BOARD SET TITLE=?,CONTENT=?,ORIGINAL_FILENAME=?,RENAMED_FILENAME=?,MODIFY_DATE=SYSDATE WHERE NO=?";

		try {
			pstmt=connection.prepareStatement(query);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getOriginalFileName());
			pstmt.setString(4, board.getRenamedFileName());
			pstmt.setInt(5, board.getNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	

	public int updateStatus(Connection connection, int no, String status) {
		// 1. update이니 정수값으로 결과
		int result=0;
		// 2. 쿼리문 실행시킬 pstmt.. 
		PreparedStatement pstmt=null;
		// 3. 쿼리문까지 만들면 기본적인 준비 끝. 
		String query="UPDATE BOARD SET STATUS=? WHERE NO=?";
		
		
		try {
			// 4. connection객체에서 퀴리문 주면 pstm얻어옴(예외처리하기)
			pstmt=connection.prepareStatement(query);
			pstmt.setString(1, status);
			pstmt.setInt(2, no);
			
			// 6. update나 insert등등은 executeUpdate임(selet는 executeQuery임)
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}


}
