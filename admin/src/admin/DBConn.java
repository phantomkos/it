package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//접속객체를 생성하는 클래스 (이 접속객체를 공통으로 사용할 예정)
public class DBConn {

	// 속성
	private Connection con; // con이 접속객체

	// 접속객체를 꺼내는 메소드
	public Connection getConnection() {
		return con;
	}

	// 기본생성자
	public DBConn() throws ClassNotFoundException, SQLException {

		// DB 접속
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// url 사용하여 접속
		con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:" + 
											"1521:xe", "admin", "0000");

	}

}
