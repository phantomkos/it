package admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField text_id;
	private JTextField text_pw;

	// 접속객체에 필요한 변수 선언
	private Connection con;
	PreparedStatement pstmt = null;

	// 아이디 비밀번호 찾을때 필요한 변수 선언
	ResultSet rs = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Login() throws ClassNotFoundException, SQLException {

		// 접속객체.접속해주는 메소드()
		con = new DBConn().getConnection();
		System.out.println("Login.class DB 접속완료");
		
		setTitle("Login");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uC0AC\uC6D0\uAD00\uB9AC \uD504\uB85C\uADF8\uB7A8");
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		lbl.setBounds(81, 21, 345, 108);
		contentPane.add(lbl);

		JLabel lbl_id = new JLabel("ID");
		lbl_id.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_id.setBounds(101, 125, 72, 46);
		contentPane.add(lbl_id);

		text_id = new JTextField();
		text_id.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		text_id.setBounds(164, 125, 229, 46);
		contentPane.add(text_id);
		text_id.setColumns(10);

		JLabel lbl_pw = new JLabel("PW");
		lbl_pw.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_pw.setBounds(101, 188, 72, 46);
		contentPane.add(lbl_pw);

		text_pw = new JTextField();
		text_pw.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		text_pw.setColumns(10);
		text_pw.setBounds(164, 188, 229, 46);
		contentPane.add(text_pw);

		// 로그인하면 1)관리자페이지 2)마이페이지
		JButton btn_login = new JButton("\uB85C\uADF8\uC778");
		btn_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String sql = "SELECT id, name, department, tel FROM admin" 
								+ " WHERE id = ? AND pw = ?";

				try {
					pstmt = con.prepareStatement(sql);

					pstmt.setString(1, text_id.getText());
					pstmt.setString(2, text_pw.getText());

					rs = pstmt.executeQuery();

				} catch (SQLException e1) {
//					e1.printStackTrace();
				}

				try {

					while (rs.next()) { //rs안에 값이 있으면 무한반복
						
						String id = text_id.getText();
						String pw = text_pw.getText();
						String name = rs.getString("name");
						String department = rs.getString("department");
						String tel = rs.getString("tel");

						if (id.equals("admin")) {

							if (pw.equals("admin")) {

								JOptionPane.showMessageDialog(null, "관리자 페이지로 이동합니다.");
								
								//관리자아이디로 접속하면 --> 관리자페이지로 이동
								dispose();
								setVisible(false);
								try {
									new AdminPage().setVisible(true);
								} catch (ClassNotFoundException e1) {
//									e1.printStackTrace();
								}

							} else {
								JOptionPane.showMessageDialog(null, "ID 또는 PW가 일치하지 않습니다.");
							}
							
						} else if (id.equals(text_id.getText())) {

							if (pw.equals(text_pw.getText())) {
								JOptionPane.showMessageDialog(null, rs.getString("name") + "님 페이지로 이동합니다.");
								
//								Socket s1 = new Socket("127.0.0.1", 7777);
//								System.out.println("서버에 연결.....");
//								
//								DataOutputStream outputStream = new DataOutputStream(s1.getOutputStream());
//								DataInputStream inputStream = new DataInputStream(s1.getInputStream());

								//사원아이디로 접속하면 --> 마이페이지로 이동
								dispose();
								setVisible(false);
								//채팅방으로 이동할때 아이디, 이름, 부서, 데이터들을 가지고감
								try {
									new Calender(id, name, department,tel).setVisible(true);
								} catch (ClassNotFoundException e1) {
//									e1.printStackTrace();
								}

							} else {
								JOptionPane.showMessageDialog(null, "ID 또는 PW가 일치하지 않습니다.");
							}
							
						} else {
							JOptionPane.showMessageDialog(null, "ID 또는 PW가 일치하지 않습니다.");
						}
						
					}

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ID 또는 PW가 일치하지 않습니다.");
				}

			}
		});
		btn_login.setBackground(new Color(152, 251, 152));
		btn_login.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		btn_login.setBounds(185, 256, 139, 46);
		contentPane.add(btn_login);

	}

}
