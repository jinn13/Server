package com.kh.mvc.common.jdbc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
 * 공통(common)모듈 : 애플리케이션 개발 시 여러 클래스에 공통으로 사용되는 코드를 분리해서 작성한 클래스
 * 싱글톤(SingleTon) 디자인 패턴 적용 : 프로그램 구동 내낸 한개의 객체를 가지고 작동되게 하는 방법론 
 * static을 이용하면 됨, 클래스 안의 모든 메소드를 static 메소드로 처리함 
 * - static메모리는 프로그램 실행 될때 기록되었다가 종료되었을때 사라진다. 매번 실행될때마다 메모리에 기록되지 않음
 * 
 */
public class JDBCTemplate {
	// ↓ static 메모리에서 사용하도록 하겠다. Connection을 리턴하는 getConnection메소드 
	public static Connection getConnection() {
		Connection connection=null;
		Properties properties = new Properties();
		String filePath = JDBCTemplate.class.getResource("./driver.properties").getPath();
//		System.out.println(filePath);
		
		try {
			properties.load(new FileReader(filePath));
			Class.forName(properties.getProperty("db.driver"));
			
			connection = DriverManager.getConnection(
					properties.getProperty("db.url"), 
					properties.getProperty("db.username"), 
					properties.getProperty("db.password")
			);
			
			// 원래는 true인데, 오토커밋을 false로 해줘야 트렌젝션 단위 작업을 수월하게 할 수 있음 
			connection.setAutoCommit(false);
			
			// 그럼 오토 커밋이 안되기 때문에 connection 객체를 통해 커밋과 롤백을 호출해주어야 한다. 
//			connection.commit();
// 여기서 예외처리를 하니 getConnection 사용할때는 매번 trycatch안해도됨
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public static void commit(Connection connection) {
		try {
			if(connection != null && !connection.isClosed()) {				
				connection.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public static void rollback(Connection connection) {
		try {
			if(connection != null && !connection.isClosed()) {				
				connection.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 여기서 close메소드를 작성하면 나중에 사용할때 trycatch또 안해도 되니 코드가 간결해짐
	public static void close(Connection connection) {
		try {
			// connection이 null이 아니고, 닫혀있지 않다면 그때 닫아라~
			if(connection != null && !connection.isClosed()) {
				connection.close();	
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 메소드 오버로딩으로 같은이름 메소드 만들수있음
	public static void close(Statement statement) {
		try {
			if(statement != null && !statement.isClosed()) {
				statement.close();
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet resultSet) {
		try {
			if(resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
