package com.kh.mvc.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.kh.mvc.common.jdbc.JDBCTemplate.*;
import com.kh.mvc.member.model.vo.Member;

public class MemberDao {
	// 이 객체가 실제 DB와 연결되서 DB를 가져오는 역할

	public Member findMemberById(Connection connection, String id) {
		Member member = null;
		// 얘를 통해 sql문 실행하고 결과값을 받아올것임
		PreparedStatement  pstm = null;
		ResultSet rs = null;
		String query = "SELECT * FROM MEMBER WHERE ID=? AND STATUS='Y'";
		// Prepared안쓰면 쿼리문 안에다 + +쓰고 ID쓰고 이런 귀찮은 작업을 해야함.
		
		try {
			pstm = connection.prepareStatement(query);
			
			// ?(위치홀더) 만들어서 쿼리문 수행 전에 id로 세팅해둠
			pstm.setString(1, id);
			
			// 쿼리문 실행 후 그 결과값을 resultset으로 리턴해주는 역할
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				member= new Member();
				member.setNo(rs.getInt("NO"));
				member.setId(rs.getString("ID"));
				member.setPassword(rs.getString("PASSWORD"));
				member.setRole(rs.getString("ROLE"));
				member.setName(rs.getString("NAME"));
				member.setPhone(rs.getString("PHONE"));
				member.setEmail(rs.getString("EMAIL"));
				member.setAddress(rs.getString("ADDRESS"));
				member.setHobby(rs.getString("HOBBY"));
				member.setStatus(rs.getString("STATUS"));
				member.setEnrollDate(rs.getDate("ENROLL_DATE"));
				member.setModifyDate(rs.getDate("MODIFY_DATE"));
				
				// 컬럼라벨을 줘도 되고, 순번을 줘도 된다.  
				System.out.println("ID : "+rs.getString("ID")+", password : "+rs.getString("PASSWORD")+", NAME : "+rs.getString("NAME"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// JDBCTemplate도 static import해서 좀 더 줄여준다. 
			close(rs);
			close(pstm);	
		}
		
		return member;
	}

	public int insertMember(Connection connection, Member member) {
		int result=0;
		// ResultSet은 필요없음
		PreparedStatement pstmt = null; // 이걸 씀으로써 아래처럼 위치홀더로 좀더 간편하게 입력가능
		String query ="INSERT INTO MEMBER VALUES(SEQ_UNO.NEXTVAL,?,?,DEFAULT,?,?,?,?,?,DEFAULT,DEFAULT,DEFAULT)";
		
		try {
			pstmt = connection.prepareStatement(query);
			
			pstmt.setString(1,  member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getAddress());
			pstmt.setString(7, member.getHobby());
			
			result = pstmt.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int updateMember(Connection connection, Member member) {
		int result=0;
		PreparedStatement pstmt=null;
		String query="UPDATE MEMBER SET NAME=?,PHONE=?,EMAIL=?,ADDRESS=?,HOBBY=?,MODIFY_DATE=SYSDATE WHERE NO=?";
		
		try {
			pstmt=connection.prepareStatement(query);
			
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getHobby());
			pstmt.setInt(6, member.getNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateMemberStatus(Connection connection, int no, String status) {
		int result=0;
		PreparedStatement pstmt=null;
		String query = "UPDATE MEMBER SET STATUS=? WHERE NO=?";
		
		try {
			pstmt=connection.prepareStatement(query);
			
			pstmt.setString(1, status);
			pstmt.setInt(2, no);
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			// 사용하는 리소스 정리하기
			close(pstmt);
		}
		return result;
	}

	public int updateMemberPassword(Connection connection, int no, String password) {
		int result=0;
		PreparedStatement pstmt=null;
		String query = "UPDATE MEMBER SET PASSWORD=? WHERE NO=?";
		
		try {
			pstmt=connection.prepareStatement(query);
			
			pstmt.setString(1, password);
			pstmt.setInt(2, no);
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}	
}
