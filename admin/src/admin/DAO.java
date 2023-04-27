package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DAO {

	private Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;  

	public DAO() throws ClassNotFoundException, SQLException {// DAO�� ������
		con = new DBConn().getConnection();
		// ��ü.�޼ҵ�()
	}

	// ��ü�˻�
	public ArrayList<AdminVO> getAdmin() {

		try {
			con = new DBConn().getConnection();
			System.out.println("AdminPage DB ���ӿϷ�");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sql = "select * from admin order by id";
		try {
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<AdminVO> list = new ArrayList<AdminVO>();

		try {
			while (rs.next()) {

				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String department = rs.getString("department");
				
				list.add(new AdminVO(id,pw,name,tel,department));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
//			DBConn.close(rs, pstmt, con);
		}

		return list;

	}

	// �����˻�
	public ArrayList<AdminVO> getSingleAdmin(String textName, String textDepartment){
		
		try {
			con = new DBConn().getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "select id, pw, name, tel, department"
				+ " from admin"
				+ " where id = ? or department = ?";
		
		ArrayList<AdminVO> list = new ArrayList<AdminVO>();
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, textName); //������̵�
			pstmt.setString(2, textDepartment); //�μ���
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String department = rs.getString("department");
				
				list.add(new AdminVO(id,pw,name,tel,department));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
//			DBConn.close(rs, pstmt, con);
		}
			
		return list;
		
	}
	
	
}
