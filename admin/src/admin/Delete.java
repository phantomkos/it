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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Delete extends JFrame {

	private JPanel contentPane;
	private JTextField textField_id;

	// 접속객체에 필요한 변수 선언
	private Connection con;
	PreparedStatement pstmt = null;

	int rowcnt1 = 0; // 반환값은? 행의 갯수가 들어간다.

	// 검색할때 필요한 변수 선언 (DB에 아이디 체크)
	ResultSet rs = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delete frame = new Delete();
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
	public Delete() throws ClassNotFoundException, SQLException {
		
		// 접속객체.접속해주는 메소드()
		con = new DBConn().getConnection();
		System.out.println("delete.class DB 접속완료");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uC0AC\uC6D0 \uC0AD\uC81C");
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		lbl.setBounds(116, 10, 182, 80);
		contentPane.add(lbl);

		JLabel lbl_id = new JLabel("ID");
		lbl_id.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_id.setBounds(67, 109, 57, 46);
		contentPane.add(lbl_id);

		textField_id = new JTextField();
		textField_id.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textField_id.setColumns(10);
		textField_id.setBounds(125, 109, 229, 46);
		contentPane.add(textField_id);

		// 1)DB에 아이디가 있는지 없는지 먼저 확인 ->
		//   DB에 없는 아이디는 "없는 아이디 입니다."를 출력
		// 2)DB에 아이디가 있으면 삭제시키면 됨
		JButton btn_delete = new JButton("DELETE");
		btn_delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String sql = "select count(*) from admin where id = ?";

				try {
					pstmt = con.prepareStatement(sql);

					pstmt.setString(1, textField_id.getText());

					rs = pstmt.executeQuery();

					if (rs.next()) {
						// 자바변수명 //sql컬럼
						int cnt = rs.getInt("count(*)");

						if (cnt > 0) {

							String sql2 = "delete from admin where id = ?";

							try {

								int i = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "경고",
											JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

								if (i == 0) {
									pstmt = con.prepareStatement(sql2);
									pstmt.setString(1, textField_id.getText());
									rowcnt1 = pstmt.executeUpdate();
								}

							} catch (SQLException e1) {
								e1.printStackTrace();
							}

							if (rowcnt1 >= 1) { // 바뀌는 행이 1이상이면

								System.out.println("delete OK~! " + rowcnt1 + "행 " + textField_id.getText() + " 삭제완료");
								JOptionPane.showMessageDialog(null, "사원 삭제 완료");
								
								//성공하면 관리자페이지로 전환
								dispose();
								setVisible(false);
								try {
									new AdminPage().setVisible(true);
								} catch (ClassNotFoundException e1) {
//									e1.printStackTrace();
								} catch (SQLException e1) {
//									e1.printStackTrace();
								}

							} else {
								System.out.println("delete error");
								JOptionPane.showMessageDialog(null, "사원 삭제 실패");
							}

						} else {
							JOptionPane.showMessageDialog(null, "없는 아이디 입니다. 다시 입력해주세요.");
						}

					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btn_delete.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		btn_delete.setBackground(new Color(255, 0, 0));
		btn_delete.setBounds(130, 189, 162, 46);
		contentPane.add(btn_delete);
	}
}
