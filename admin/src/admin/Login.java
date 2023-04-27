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

	// ���Ӱ�ü�� �ʿ��� ���� ����
	private Connection con;
	PreparedStatement pstmt = null;

	// ���̵� ��й�ȣ ã���� �ʿ��� ���� ����
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

		// ���Ӱ�ü.�������ִ� �޼ҵ�()
		con = new DBConn().getConnection();
		System.out.println("Login.class DB ���ӿϷ�");
		
		setTitle("Login");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uC0AC\uC6D0\uAD00\uB9AC \uD504\uB85C\uADF8\uB7A8");
		lbl.setFont(new Font("���� ���", Font.BOLD, 40));
		lbl.setBounds(81, 21, 345, 108);
		contentPane.add(lbl);

		JLabel lbl_id = new JLabel("ID");
		lbl_id.setFont(new Font("���� ���", Font.PLAIN, 30));
		lbl_id.setBounds(101, 125, 72, 46);
		contentPane.add(lbl_id);

		text_id = new JTextField();
		text_id.setFont(new Font("���� ���", Font.PLAIN, 20));
		text_id.setBounds(164, 125, 229, 46);
		contentPane.add(text_id);
		text_id.setColumns(10);

		JLabel lbl_pw = new JLabel("PW");
		lbl_pw.setFont(new Font("���� ���", Font.PLAIN, 30));
		lbl_pw.setBounds(101, 188, 72, 46);
		contentPane.add(lbl_pw);

		text_pw = new JTextField();
		text_pw.setFont(new Font("���� ���", Font.PLAIN, 20));
		text_pw.setColumns(10);
		text_pw.setBounds(164, 188, 229, 46);
		contentPane.add(text_pw);

		// �α����ϸ� 1)������������ 2)����������
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

					while (rs.next()) { //rs�ȿ� ���� ������ ���ѹݺ�
						
						String id = text_id.getText();
						String pw = text_pw.getText();
						String name = rs.getString("name");
						String department = rs.getString("department");
						String tel = rs.getString("tel");

						if (id.equals("admin")) {

							if (pw.equals("admin")) {

								JOptionPane.showMessageDialog(null, "������ �������� �̵��մϴ�.");
								
								//�����ھ��̵�� �����ϸ� --> �������������� �̵�
								dispose();
								setVisible(false);
								try {
									new AdminPage().setVisible(true);
								} catch (ClassNotFoundException e1) {
//									e1.printStackTrace();
								}

							} else {
								JOptionPane.showMessageDialog(null, "ID �Ǵ� PW�� ��ġ���� �ʽ��ϴ�.");
							}
							
						} else if (id.equals(text_id.getText())) {

							if (pw.equals(text_pw.getText())) {
								JOptionPane.showMessageDialog(null, rs.getString("name") + "�� �������� �̵��մϴ�.");
								
//								Socket s1 = new Socket("127.0.0.1", 7777);
//								System.out.println("������ ����.....");
//								
//								DataOutputStream outputStream = new DataOutputStream(s1.getOutputStream());
//								DataInputStream inputStream = new DataInputStream(s1.getInputStream());

								//������̵�� �����ϸ� --> ������������ �̵�
								dispose();
								setVisible(false);
								//ä�ù����� �̵��Ҷ� ���̵�, �̸�, �μ�, �����͵��� ������
								try {
									new Calender(id, name, department,tel).setVisible(true);
								} catch (ClassNotFoundException e1) {
//									e1.printStackTrace();
								}

							} else {
								JOptionPane.showMessageDialog(null, "ID �Ǵ� PW�� ��ġ���� �ʽ��ϴ�.");
							}
							
						} else {
							JOptionPane.showMessageDialog(null, "ID �Ǵ� PW�� ��ġ���� �ʽ��ϴ�.");
						}
						
					}

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ID �Ǵ� PW�� ��ġ���� �ʽ��ϴ�.");
				}

			}
		});
		btn_login.setBackground(new Color(152, 251, 152));
		btn_login.setFont(new Font("���� ���", Font.PLAIN, 30));
		btn_login.setBounds(185, 256, 139, 46);
		contentPane.add(btn_login);

	}

}
