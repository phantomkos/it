package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//���Ӱ�ü�� �����ϴ� Ŭ���� (�� ���Ӱ�ü�� �������� ����� ����)
public class DBConn {

	// �Ӽ�
	private Connection con; // con�� ���Ӱ�ü

	// ���Ӱ�ü�� ������ �޼ҵ�
	public Connection getConnection() {
		return con;
	}

	// �⺻������
	public DBConn() throws ClassNotFoundException, SQLException {

		// DB ����
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// url ����Ͽ� ����
		con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:" + 
											"1521:xe", "admin", "0000");

	}

}
