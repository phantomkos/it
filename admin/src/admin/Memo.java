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

	// ���Ӱ�ü�� �ʿ��� ���� ����
	private Connection con;
	PreparedStatement pstmt = null;

	// �˻��Ҷ� �ʿ��� ���� ����
	ResultSet rs = null;

	// insert�� �� �ƴ��� Ȯ�ο�
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

	// �⺻������
	public Memo() { }

	// ���ڸ� �޴� ������ (���̵�, �̸�, �μ�, ��ȭ��ȣ �ϴ� �޾ƾ� �� -> �޸��� �����ϰ� ���ڰ� �ִ� Calender�� �־����)
	public Memo(String id, String name, String department, String tel) throws ClassNotFoundException, SQLException {

		setTitle("�޸���");

		// ���Ӱ�ü.�������ִ� �޼ҵ�()
		con = new DBConn().getConnection();
		System.out.println("memo DB ���ӿϷ�");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uBA54\uBAA8\uC7A5");
		lbl.setFont(new Font("���� ���", Font.BOLD, 40));
		lbl.setBounds(151, 0, 127, 80);
		contentPane.add(lbl);

		// ���ڰ� �ԷµǴ� ��
		textArea = new JTextArea();
		textArea.setFont(new Font("���� ���", Font.PLAIN, 20));
		textArea.setBounds(48, 77, 351, 115);
		contentPane.add(textArea);

		// ���� Ŭ���ϸ� DB�� ����ǰ�
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

				// Ȯ���۾� -> �� ������ �ܼ�â�� insert OK~! n�� "�̸�" ����
				if (rowcnt >= 1) {
					System.out.println("insert OK~! " + rowcnt + "�� " + textArea.getText() + " ����");
					JOptionPane.showMessageDialog(null, "�޸� ���� �Ϸ�");

					dispose();
					setVisible(false);
					try {
						new Calender(id, name, department, tel).setVisible(true);
					} catch (ClassNotFoundException | SQLException e1) {
//						e1.printStackTrace();
					}

				} else {
					System.out.println("update error");
					JOptionPane.showMessageDialog(null, "�޸� ���� ����");
				}

			}
		});
		btnNewButton.setFont(new Font("����", Font.BOLD, 20));
		btnNewButton.setBounds(120, 212, 195, 41);
		contentPane.add(btnNewButton);

	}// �⺻������-end

}
