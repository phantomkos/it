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

	// ���Ӱ�ü�� �ʿ��� ���� ����
	private Connection con;
	PreparedStatement pstmt = null;

	int rowcnt1 = 0; // ��ȯ����? ���� ������ ����.

	// �˻��Ҷ� �ʿ��� ���� ���� (DB�� ���̵� üũ)
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
		
		// ���Ӱ�ü.�������ִ� �޼ҵ�()
		con = new DBConn().getConnection();
		System.out.println("delete.class DB ���ӿϷ�");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uC0AC\uC6D0 \uC0AD\uC81C");
		lbl.setFont(new Font("���� ���", Font.BOLD, 40));
		lbl.setBounds(116, 10, 182, 80);
		contentPane.add(lbl);

		JLabel lbl_id = new JLabel("ID");
		lbl_id.setFont(new Font("���� ���", Font.PLAIN, 30));
		lbl_id.setBounds(67, 109, 57, 46);
		contentPane.add(lbl_id);

		textField_id = new JTextField();
		textField_id.setFont(new Font("���� ���", Font.PLAIN, 20));
		textField_id.setColumns(10);
		textField_id.setBounds(125, 109, 229, 46);
		contentPane.add(textField_id);

		// 1)DB�� ���̵� �ִ��� ������ ���� Ȯ�� ->
		//   DB�� ���� ���̵�� "���� ���̵� �Դϴ�."�� ���
		// 2)DB�� ���̵� ������ ������Ű�� ��
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
						// �ڹٺ����� //sql�÷�
						int cnt = rs.getInt("count(*)");

						if (cnt > 0) {

							String sql2 = "delete from admin where id = ?";

							try {

								int i = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "���",
											JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

								if (i == 0) {
									pstmt = con.prepareStatement(sql2);
									pstmt.setString(1, textField_id.getText());
									rowcnt1 = pstmt.executeUpdate();
								}

							} catch (SQLException e1) {
								e1.printStackTrace();
							}

							if (rowcnt1 >= 1) { // �ٲ�� ���� 1�̻��̸�

								System.out.println("delete OK~! " + rowcnt1 + "�� " + textField_id.getText() + " �����Ϸ�");
								JOptionPane.showMessageDialog(null, "��� ���� �Ϸ�");
								
								//�����ϸ� �������������� ��ȯ
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
								JOptionPane.showMessageDialog(null, "��� ���� ����");
							}

						} else {
							JOptionPane.showMessageDialog(null, "���� ���̵� �Դϴ�. �ٽ� �Է����ּ���.");
						}

					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btn_delete.setFont(new Font("���� ���", Font.PLAIN, 30));
		btn_delete.setBackground(new Color(255, 0, 0));
		btn_delete.setBounds(130, 189, 162, 46);
		contentPane.add(btn_delete);
	}
}
