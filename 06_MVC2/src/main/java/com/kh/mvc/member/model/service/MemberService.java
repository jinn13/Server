package com.kh.mvc.member.model.service;

import java.sql.Connection;

import static com.kh.mvc.common.jdbc.JDBCTemplate.*;
import com.kh.mvc.member.model.dao.MemberDao;
import com.kh.mvc.member.model.vo.Member;

public class MemberService {
	private MemberDao dao = new MemberDao();

	public Member login(String id, String password) {
		Member member=this.findMemberById(id);
		
//		// MemberById 메소드로 따로 빼두었으니 이 부분은 주석처리함
//		Connection connection = getConnection();
//		// 멤버 있으면 그 객체가 대입되고 아니면 null로 되게끔 하기
//		// 하지만 얘도 실제로 DB에 접근하진 않는다. MemberDao라는 객체가 할 것임
//		Member member=dao.findMemberById(connection, id);
//		close(connection);
		
		
		// 조회한 멤버가 패스워드까지 일치하면 리턴, 아니면 null리턴
		if(member!=null && member.getPassword().equals(password)) {
			return member;
		}else {
			return null;
		}
	}

	// insert와 update작업을 해준다.
	// insert할때는 no값이 아직 없고, update의 경우 이미 no이 있으므로 그걸로 구분한다. 
	public int save(Member member) {
		int result = 0;
		Connection connection = getConnection();
		
		if(member.getNo() != 0) {
			result = dao.updateMember(connection, member);
			
		}else {
			result = dao.insertMember(connection, member);			
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
		int result = 0;
		Connection connection = getConnection();
		
		// 쿼리문의 SET STAUS="N"이어도 되지만.. 만약 다시 회원을 되돌릴 경우 또 다른 메소드 작성해야 함. 
		// 그래서 추후 확장성을 위해 N을 쿼리문에 안넣고 여기에 입력
		result = dao.updateMemberStatus(connection, no, "N");
		if(result>0) {
			commit(connection);
		}else {
			rollback(connection);
		}
		return result;
	}
	
	public int updatePassword(int no, String password) {
		int result = 0;
		Connection connection = getConnection();
		
		result=dao.updateMemberPassword(connection, no, password);
		
		if(result>0) {
			commit(connection);
		}else {
			rollback(connection);
		}
		close(connection);
		
		return result;
	}

	public Boolean isDuplicateId(String id) {
		return this.findMemberById(id)!=null;
	}
	
	// 중복되면 코드 길어지니 따로 빼둠
	public Member findMemberById(String id) {
		Connection connection = getConnection();
		Member member=dao.findMemberById(connection, id);
		close(connection);
		
		return member;
	}
}
