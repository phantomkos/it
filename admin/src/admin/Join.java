package admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

public class Join extends JFrame {

	private JPanel contentPane;
	private JTextField textField_id;
//	private JTextField textField_pw;
	private JPasswordField textField_pw; //pw입력하면 *이 나오도록
	private JTextField textField_name;
	private JTextField textField_tel;
	private JComboBox comboBox_d; //부서선택박스
	JLabel lbl_id_ck; //아이디 중복확인 -> 아이디 중복 or 사용 가능 아이디 표시


	// 접속객체에 필요한 변수 선언
	private Connection con;
	PreparedStatement pstmt = null;

	// insert가 잘 됐는지 확인용
	int rowcnt1 = 0;
	
	// 검색할때 필요한 변수 선언 (아이디 중복 체크)
	ResultSet rs = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Join frame = new Join();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Join() throws ClassNotFoundException, SQLException {

					//접속객체.접속해주는 메소드()
		con = new DBConn().getConnection();
		System.out.println("Join.class DB 접속완료");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 569);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uC0AC\uC6D0\uCD94\uAC00");
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		lbl.setBounds(134, 10, 160, 80);
		contentPane.add(lbl);

		JLabel lbl_id = new JLabel("ID");
		lbl_id.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_id.setBounds(41, 110, 72, 46);
		contentPane.add(lbl_id);
		
		//아이디 중복 체크
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
							lbl_id_ck.setForeground(Color.RED);
							lbl_id_ck.setText("아이디 중복");
						} else {
							lbl_id_ck.setForeground(Color.BLUE);
							lbl_id_ck.setText("사용 가능 아이디");
						}
						
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		textField_id.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textField_id.setColumns(10);
		textField_id.setBounds(175, 110, 229, 46);
		contentPane.add(textField_id);

		JLabel lbl_pw = new JLabel("PW");
		lbl_pw.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_pw.setBounds(41, 172, 72, 46);
		contentPane.add(lbl_pw);

		textField_pw = new JPasswordField();
		textField_pw.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textField_pw.setColumns(10);
		((JPasswordField) textField_pw).setEchoChar('*');
		textField_pw.setBounds(175, 172, 229, 46);
		contentPane.add(textField_pw);

		JLabel lbl_name = new JLabel("\uC774\uB984");
		lbl_name.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_name.setBounds(41, 233, 72, 46);
		contentPane.add(lbl_name);

		textField_name = new JTextField();
		textField_name.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textField_name.setColumns(10);
		textField_name.setBounds(175, 233, 229, 46);
		contentPane.add(textField_name);

		JLabel lbl_tel = new JLabel("\uC804\uD654\uBC88\uD638");
		lbl_tel.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_tel.setBounds(41, 295, 129, 46);
		contentPane.add(lbl_tel);

		textField_tel = new JTextField();
		textField_tel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textField_tel.setColumns(10);
		textField_tel.setBounds(175, 295, 229, 46);
		contentPane.add(textField_tel);

		JLabel lbl_department = new JLabel("\uBD80\uC11C");
		lbl_department.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		lbl_department.setBounds(41, 356, 72, 46);
		contentPane.add(lbl_department);

		// 회원가입 버튼을 누르면 db에 데이터가 들어가고 다시 로그인창을 띄움
		JButton btn_join = new JButton("\uCD94\uAC00");
		btn_join.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
									 //정규표현식
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
						
						String sql = "insert into admin values (?, ?, ?, ?, ?)";

						pstmt = con.prepareStatement(sql);

						pstmt.setString(1, textField_id.getText());
						pstmt.setString(2, textField_pw.getText());
						pstmt.setString(3, textField_name.getText());
						pstmt.setString(4, textField_tel.getText());
						pstmt.setString(5, comboBox_d.getSelectedItem().toString());

						rowcnt1 = pstmt.executeUpdate();
						
					}

				} catch (SQLException e1) {
//					e1.printStackTrace(); //에러메세지 안보이게 주석 처리
				}

				// 확인작업 -> 잘 들어갔으면 콘솔창에 insert OK~! n행 "이름" 삽입
				if (rowcnt1 >= 1) {
					System.out.println("insert OK~! " + rowcnt1 + "행 " + textField_name.getText() + " 삽입");
					JOptionPane.showMessageDialog(null, "회원 가입 완료");
					
					//회원가입이 완료되면 Join창 꺼지고 Login창이 켜짐
					dispose();
					setVisible(false);
					try {
						new AdminPage().setVisible(true);
					} catch (ClassNotFoundException | SQLException e1) {
//						e1.printStackTrace();
					}
					
					try {
						con.close();
						System.out.println("DB 접속 끝~!");
					} catch (SQLException e1) {
//						e1.printStackTrace();
					}

				} else {
					System.out.println("update error");
					JOptionPane.showMessageDialog(null, "회원 가입 실패");
				}

			}
		});
		btn_join.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		btn_join.setBackground(new Color(152, 251, 152));
		btn_join.setBounds(242, 442, 162, 46);
		contentPane.add(btn_join);

		JButton btnBack = new JButton("\uB4A4\uB85C");
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
		btnBack.setBounds(242, 442, 162, 46);
		btnBack.setBounds(36, 442, 162, 46);
		contentPane.add(btnBack);

		//부서선택 박스
		comboBox_d = new JComboBox();
		comboBox_d.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		comboBox_d.setBackground(Color.WHITE);
		comboBox_d.setModel(
				new DefaultComboBoxModel(new String[] { "\uCD1D\uBB34\uBD80\uC11C", "\uAE30\uD68D\uBD80\uC11C",
						"\uC601\uC5C5\uBD80\uC11C", "\uD68C\uACC4\uBD80\uC11C", "\uC778\uC0AC\uBD80\uC11C" }));
		comboBox_d.setBounds(175, 364, 229, 38);
		contentPane.add(comboBox_d);
		
		//아이디 확인 라벨
		lbl_id_ck = new JLabel("");
		lbl_id_ck.setHorizontalAlignment(JLabel.RIGHT); //JLabel 오른쪽 정렬
		lbl_id_ck.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 15));
		lbl_id_ck.setToolTipText("");
		lbl_id_ck.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_id_ck.setBounds(291, 86, 114, 25);
		contentPane.add(lbl_id_ck);
	}
}
