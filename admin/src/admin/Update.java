package admin;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Update extends JFrame {

	private JPanel contentPane;
	private JTextField textField_id;
	private JTextField textField_pw;
	private JTextField textField_name;
	private JTextField textField_tel;
	private JTextField textField_d;
	private JComboBox comboBox_d;
	JLabel lbl_id_ck;
	// 접속객체에 필요한 변수 선언
	private Connection con;
	PreparedStatement pstmt = null;

	// 반환값은? 행의 갯수가 들어간다.
	int rowcnt1 = 0;
	ResultSet rs = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Update frame = new Update();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Update() throws ClassNotFoundException, SQLException {
		
		//접속객체.접속해주는 메소드()
		con = new DBConn().getConnection();
		System.out.println("update.class DB 접속완료");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 441, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uC0AC\uC6D0 \uC218\uC815");
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		lbl.setBounds(111, 10, 182, 80);
		contentPane.add(lbl);

		JLabel lbl_id = new JLabel("ID");
		lbl_id.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_id.setBounds(28, 100, 145, 46);
		contentPane.add(lbl_id);

		textField_id = new JTextField();
		textField_id.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				String sql = "select count(*) from admin where id = ?";
				
				try {
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, textField_id.getText());
					
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						//자바변수명 			//sql컬럼
						int cnt = rs.getInt("count(*)");
						
						if(cnt > 0) {
							lbl_id_ck.setForeground(Color.BLUE);
							lbl_id_ck.setText("수정가능한 ID 입니다");
						} else {
							lbl_id_ck.setForeground(Color.RED);
							lbl_id_ck.setText("일치하는 ID가 없습니다");
						}
						
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		textField_id.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textField_id.setColumns(10);
		textField_id.setBounds(185, 100, 229, 46);
		contentPane.add(textField_id);

		JLabel lbl_pw = new JLabel("PW");
		lbl_pw.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_pw.setBounds(28, 226, 72, 46);
		contentPane.add(lbl_pw);

		textField_pw = new JTextField();
		textField_pw.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textField_pw.setColumns(10);
		textField_pw.setBounds(185, 226, 229, 46);
		contentPane.add(textField_pw);

		JLabel lbl_name = new JLabel("\uC774\uB984");
		lbl_name.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_name.setBounds(28, 293, 72, 46);
		contentPane.add(lbl_name);

		textField_name = new JTextField();
		textField_name.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textField_name.setColumns(10);
		textField_name.setBounds(185, 293, 229, 46);
		contentPane.add(textField_name);

		JLabel lbl_tel = new JLabel("\uC804\uD654\uBC88\uD638");
		lbl_tel.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_tel.setBounds(28, 351, 129, 46);
		contentPane.add(lbl_tel);

		textField_tel = new JTextField();
		textField_tel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textField_tel.setColumns(10);
		textField_tel.setBounds(185, 351, 229, 46);
		contentPane.add(textField_tel);

		JLabel lbl_department = new JLabel("\uBD80\uC11C");
		lbl_department.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_department.setBounds(28, 417, 72, 46);
		contentPane.add(lbl_department);

		
		comboBox_d = new JComboBox();
		comboBox_d.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		comboBox_d.setBackground(Color.WHITE);
		comboBox_d.setModel(
				new DefaultComboBoxModel(new String[] { "\uCD1D\uBB34\uBD80\uC11C", "\uAE30\uD68D\uBD80\uC11C",
						"\uC601\uC5C5\uBD80\uC11C", "\uD68C\uACC4\uBD80\uC11C", "\uC778\uC0AC\uBD80\uC11C" }));
		comboBox_d.setBounds(185, 417, 229, 46);
		contentPane.add(comboBox_d);
		
		// 아이디를 주면 비번, 전화번호, 부서 수정하고 -> 다시 관리자페이지로 이동
		JButton btn_update = new JButton("UPDATE");
		btn_update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String idpwPattern = "^[0-9a-zA-Z]*$"; //숫자랑 영어
				String telPattern = "^\\d{2,3}-\\d{3,4}-\\d{4}$"; //전화번호 

				try {

					if ((textField_id.getText().isEmpty()) == true 
							|| textField_pw.getText().isEmpty() == true
							|| textField_name.getText().isEmpty() == true 
							|| textField_tel.getText().isEmpty() == true
							|| comboBox_d.getSelectedItem().toString().isEmpty() == true) {

						JOptionPane.showMessageDialog(null, "비어있는 칸이 존재합니다.");

					} else if (!Pattern.matches(idpwPattern, textField_id.getText())
							|| !Pattern.matches(idpwPattern, textField_pw.getText())) {

						JOptionPane.showMessageDialog(null, "아이디와 비밀번호는 영어와 숫자만 가능합니다.");

					} else if (!Pattern.matches(telPattern, textField_tel.getText())) {

						JOptionPane.showMessageDialog(null, "전화번호가 옳바르지 않습니다. 다시입력해주세요");

					} else {

						String sql = "update admin" 
									+ " set pw = ?, name = ?, tel = ?, department = ?" 
									+ " where id = ?";

						pstmt = con.prepareStatement(sql);

						pstmt.setString(1, textField_pw.getText());
						pstmt.setString(2, textField_name.getText());
						pstmt.setString(3, textField_tel.getText());
						pstmt.setString(4, comboBox_d.getSelectedItem().toString());
						pstmt.setString(5, textField_id.getText());

						rowcnt1 = pstmt.executeUpdate();
						
					}

				} catch (SQLException e1) {
//					e1.printStackTrace(); //에러메세지 안보이게 주석 처리
				}

				
				

				if (rowcnt1 >= 1) { // 바뀌는 행이 1이상이면
					System.out.println("update OK~! " + rowcnt1 + "행 " + textField_name.getText() + " 수정완료");
					JOptionPane.showMessageDialog(null, "사원 수정 완료");
					dispose();
					setVisible(false);
					try {
						new AdminPage().setVisible(true);
					} catch (ClassNotFoundException e1) {
//						e1.printStackTrace();
					} catch (SQLException e1) {
//						e1.printStackTrace();
					}

					
				} 

			
			
				
			}
		});
		btn_update.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		btn_update.setBackground(new Color(152, 251, 152));
		btn_update.setBounds(238, 483, 162, 46);
		contentPane.add(btn_update);

		JLabel lblNewLabel = new JLabel(
				"-----------------------------------------------------------------------------------------------");
		lblNewLabel.setBounds(28, 156, 386, 15);
		contentPane.add(lblNewLabel);

		JLabel lbl2 = new JLabel("\uC218\uC815 \uB0B4\uC6A9\uC744 \uC801\uC5B4\uC8FC\uC138\uC694");
		lbl2.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lbl2.setBounds(28, 170, 386, 46);
		contentPane.add(lbl2);
		
		JButton btnBack = new JButton("뒤로");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				setVisible(false);
				try {
					new AdminPage().setVisible(true);
				} catch (ClassNotFoundException | SQLException e1) {
//					e1.printStackTrace();
				}

			}
		});
		btnBack.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		btnBack.setBackground(new Color(152, 251, 152));
		btnBack.setBounds(28, 483, 162, 46);
		contentPane.add(btnBack);
		
		lbl_id_ck = new JLabel("");
		lbl_id_ck.setHorizontalAlignment(JLabel.RIGHT); //JLabel 오른쪽 정렬
		lbl_id_ck.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 15));
		lbl_id_ck.setToolTipText("");
		lbl_id_ck.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_id_ck.setBounds(185, 76, 229, 25);
		contentPane.add(lbl_id_ck);
		
	}
}
