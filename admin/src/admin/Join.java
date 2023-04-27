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
	private JPasswordField textField_pw; //pw�Է��ϸ� *�� ��������
	private JTextField textField_name;
	private JTextField textField_tel;
	private JComboBox comboBox_d; //�μ����ùڽ�
	JLabel lbl_id_ck; //���̵� �ߺ�Ȯ�� -> ���̵� �ߺ� or ��� ���� ���̵� ǥ��


	// ���Ӱ�ü�� �ʿ��� ���� ����
	private Connection con;
	PreparedStatement pstmt = null;

	// insert�� �� �ƴ��� Ȯ�ο�
	int rowcnt1 = 0;
	
	// �˻��Ҷ� �ʿ��� ���� ���� (���̵� �ߺ� üũ)
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

					//���Ӱ�ü.�������ִ� �޼ҵ�()
		con = new DBConn().getConnection();
		System.out.println("Join.class DB ���ӿϷ�");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 569);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uC0AC\uC6D0\uCD94\uAC00");
		lbl.setFont(new Font("���� ���", Font.BOLD, 40));
		lbl.setBounds(134, 10, 160, 80);
		contentPane.add(lbl);

		JLabel lbl_id = new JLabel("ID");
		lbl_id.setFont(new Font("���� ���", Font.PLAIN, 30));
		lbl_id.setBounds(41, 110, 72, 46);
		contentPane.add(lbl_id);
		
		//���̵� �ߺ� üũ
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
						//�ڹٺ����� 			//sql�÷�
						int cnt = rs.getInt("count(*)");
						
						if(cnt > 0) {
							lbl_id_ck.setForeground(Color.RED);
							lbl_id_ck.setText("���̵� �ߺ�");
						} else {
							lbl_id_ck.setForeground(Color.BLUE);
							lbl_id_ck.setText("��� ���� ���̵�");
						}
						
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		textField_id.setFont(new Font("���� ���", Font.PLAIN, 20));
		textField_id.setColumns(10);
		textField_id.setBounds(175, 110, 229, 46);
		contentPane.add(textField_id);

		JLabel lbl_pw = new JLabel("PW");
		lbl_pw.setFont(new Font("���� ���", Font.PLAIN, 30));
		lbl_pw.setBounds(41, 172, 72, 46);
		contentPane.add(lbl_pw);

		textField_pw = new JPasswordField();
		textField_pw.setFont(new Font("���� ���", Font.PLAIN, 20));
		textField_pw.setColumns(10);
		((JPasswordField) textField_pw).setEchoChar('*');
		textField_pw.setBounds(175, 172, 229, 46);
		contentPane.add(textField_pw);

		JLabel lbl_name = new JLabel("\uC774\uB984");
		lbl_name.setFont(new Font("���� ���", Font.PLAIN, 30));
		lbl_name.setBounds(41, 233, 72, 46);
		contentPane.add(lbl_name);

		textField_name = new JTextField();
		textField_name.setFont(new Font("���� ���", Font.PLAIN, 20));
		textField_name.setColumns(10);
		textField_name.setBounds(175, 233, 229, 46);
		contentPane.add(textField_name);

		JLabel lbl_tel = new JLabel("\uC804\uD654\uBC88\uD638");
		lbl_tel.setFont(new Font("���� ���", Font.PLAIN, 30));
		lbl_tel.setBounds(41, 295, 129, 46);
		contentPane.add(lbl_tel);

		textField_tel = new JTextField();
		textField_tel.setFont(new Font("���� ���", Font.PLAIN, 20));
		textField_tel.setColumns(10);
		textField_tel.setBounds(175, 295, 229, 46);
		contentPane.add(textField_tel);

		JLabel lbl_department = new JLabel("\uBD80\uC11C");
		lbl_department.setFont(new Font("���� ���", Font.PLAIN, 30));
		lbl_department.setBounds(41, 356, 72, 46);
		contentPane.add(lbl_department);

		// ȸ������ ��ư�� ������ db�� �����Ͱ� ���� �ٽ� �α���â�� ���
		JButton btn_join = new JButton("\uCD94\uAC00");
		btn_join.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
									 //����ǥ����
				String idpwPattern = "^[0-9a-zA-Z]*$"; //���ڶ� ����
				String telPattern = "^\\d{2,3}-\\d{3,4}-\\d{4}$"; //��ȭ��ȣ 

				try {

					if ((textField_id.getText().isEmpty()) == true 
							|| textField_pw.getText().isEmpty() == true
							|| textField_name.getText().isEmpty() == true 
							|| textField_tel.getText().isEmpty() == true
							|| comboBox_d.getSelectedItem().toString().isEmpty() == true) {

						JOptionPane.showMessageDialog(null, "����ִ� ĭ�� �����մϴ�.");

					} else if (!Pattern.matches(idpwPattern, textField_id.getText())
							|| !Pattern.matches(idpwPattern, textField_pw.getText())) {

						JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� ����� ���ڸ� �����մϴ�.");

					} else if (!Pattern.matches(telPattern, textField_tel.getText())) {

						JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �ǹٸ��� �ʽ��ϴ�. �ٽ��Է����ּ���");

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
//					e1.printStackTrace(); //�����޼��� �Ⱥ��̰� �ּ� ó��
				}

				// Ȯ���۾� -> �� ������ �ܼ�â�� insert OK~! n�� "�̸�" ����
				if (rowcnt1 >= 1) {
					System.out.println("insert OK~! " + rowcnt1 + "�� " + textField_name.getText() + " ����");
					JOptionPane.showMessageDialog(null, "ȸ�� ���� �Ϸ�");
					
					//ȸ�������� �Ϸ�Ǹ� Joinâ ������ Loginâ�� ����
					dispose();
					setVisible(false);
					try {
						new AdminPage().setVisible(true);
					} catch (ClassNotFoundException | SQLException e1) {
//						e1.printStackTrace();
					}
					
					try {
						con.close();
						System.out.println("DB ���� ��~!");
					} catch (SQLException e1) {
//						e1.printStackTrace();
					}

				} else {
					System.out.println("update error");
					JOptionPane.showMessageDialog(null, "ȸ�� ���� ����");
				}

			}
		});
		btn_join.setFont(new Font("���� ���", Font.PLAIN, 30));
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

		btnBack.setFont(new Font("���� ���", Font.PLAIN, 30));
		btnBack.setBackground(new Color(152, 251, 152));
		btnBack.setBounds(242, 442, 162, 46);
		btnBack.setBounds(36, 442, 162, 46);
		contentPane.add(btnBack);

		//�μ����� �ڽ�
		comboBox_d = new JComboBox();
		comboBox_d.setFont(new Font("���� ���", Font.PLAIN, 20));
		comboBox_d.setBackground(Color.WHITE);
		comboBox_d.setModel(
				new DefaultComboBoxModel(new String[] { "\uCD1D\uBB34\uBD80\uC11C", "\uAE30\uD68D\uBD80\uC11C",
						"\uC601\uC5C5\uBD80\uC11C", "\uD68C\uACC4\uBD80\uC11C", "\uC778\uC0AC\uBD80\uC11C" }));
		comboBox_d.setBounds(175, 364, 229, 38);
		contentPane.add(comboBox_d);
		
		//���̵� Ȯ�� ��
		lbl_id_ck = new JLabel("");
		lbl_id_ck.setHorizontalAlignment(JLabel.RIGHT); //JLabel ������ ����
		lbl_id_ck.setFont(new Font("���� ��� Semilight", Font.PLAIN, 15));
		lbl_id_ck.setToolTipText("");
		lbl_id_ck.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_id_ck.setBounds(291, 86, 114, 25);
		contentPane.add(lbl_id_ck);
	}
}
