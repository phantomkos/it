package admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Memo extends JFrame {

	private JPanel contentPane;
	JTextArea textArea;

	// 접속객체에 필요한 변수 선언
	private Connection con;
	PreparedStatement pstmt = null;

	// 검색할때 필요한 변수 선언
	ResultSet rs = null;

	// insert가 잘 됐는지 확인용
	int rowcnt = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Memo frame = new Memo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 기본생성자
	public Memo() { }

	// 인자를 받는 생성자 (아이디, 이름, 부서, 전화번호 싹다 받아야 함 -> 메모장 저장하고 인자가 있는 Calender에 넣어야함)
	public Memo(String id, String name, String department, String tel) throws ClassNotFoundException, SQLException {

		setTitle("메모장");

		// 접속객체.접속해주는 메소드()
		con = new DBConn().getConnection();
		System.out.println("memo DB 접속완료");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uBA54\uBAA8\uC7A5");
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		lbl.setBounds(151, 0, 127, 80);
		contentPane.add(lbl);

		// 문자가 입력되는 곳
		textArea = new JTextArea();
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textArea.setBounds(48, 77, 351, 115);
		contentPane.add(textArea);

		// 여길 클릭하면 DB에 저장되고
		JButton btnNewButton = new JButton("\uC800\uC7A5");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String sql = "insert into memo values (?, ?, ?)";

				try {
					pstmt = con.prepareStatement(sql);

					pstmt.setString(1, id);
					pstmt.setString(2, name);
					pstmt.setString(3, textArea.getText());

					rowcnt = pstmt.executeUpdate();

				} catch (SQLException e1) {
//					e1.printStackTrace();
				}

				// 확인작업 -> 잘 들어갔으면 콘솔창에 insert OK~! n행 "이름" 삽입
				if (rowcnt >= 1) {
					System.out.println("insert OK~! " + rowcnt + "행 " + textArea.getText() + " 삽입");
					JOptionPane.showMessageDialog(null, "메모 삽입 완료");

					dispose();
					setVisible(false);
					try {
						new Calender(id, name, department, tel).setVisible(true);
					} catch (ClassNotFoundException | SQLException e1) {
//						e1.printStackTrace();
					}

				} else {
					System.out.println("update error");
					JOptionPane.showMessageDialog(null, "메모 삽입 실패");
				}

			}
		});
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 20));
		btnNewButton.setBounds(120, 212, 195, 41);
		contentPane.add(btnNewButton);

	}// 기본생성자-end

}
