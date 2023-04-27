package admin;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminPage extends JFrame {

	private DefaultTableModel model;
	private JPanel contentPane;
	private JTextField textField;
	JTextArea textArea;

	// ���Ӱ�ü�� �ʿ��� ���� ����
	private Connection con;
	PreparedStatement pstmt = null;

	// �˻��Ҷ� �ʿ��� ���� ����
	ResultSet rs = null;
	private JTable table;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPage frame = new AdminPage();
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
	public AdminPage() throws ClassNotFoundException, SQLException {

		// ���Ӱ�ü.�������ִ� �޼ҵ�()
		con = new DBConn().getConnection();
		System.out.println("AdminPage DB ���ӿϷ�");
		
		DAO dao = new DAO(); // DAO ��ü ����

		setTitle("������ ������");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl = new JLabel("\uC0AC\uC6D0 \uB9AC\uC2A4\uD2B8");
		lbl.setBounds(26, 17, 246, 81);
		lbl.setFont(new Font("���� ���", Font.BOLD, 45));
		contentPane.add(lbl);

		//Ŭ���ϸ� Updateâ�� ���� ������ ������ �ٽ� ������ �������� �̵��ϵ���
		JButton btn_update = new JButton("\uC218\uC815");
		btn_update.setBounds(469, 59, 149, 39);
		btn_update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				dispose();
				setVisible(false);
				try {
					new Update().setVisible(true);
				} catch (ClassNotFoundException e1) {
//					e1.printStackTrace();
				} catch (SQLException e1) {
//					e1.printStackTrace();
				}
				
			}
		});
		btn_update.setBackground(new Color(170, 255, 170));
		btn_update.setFont(new Font("���� ���", Font.PLAIN, 23));
		contentPane.add(btn_update);
		
		//Ŭ���ϸ� Deleteâ�� ���� ������ ������ �ٽ� ������ �������� �̵��ϵ���
		JButton btn_delete = new JButton("\uC0AD\uC81C");
		btn_delete.setBounds(469, 10, 149, 39);
		btn_delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				dispose();
				setVisible(false);
				try {
					new Delete().setVisible(true);
				} catch (ClassNotFoundException e1) {
//					e1.printStackTrace();
				} catch (SQLException e1) {
//					e1.printStackTrace();
				}
				
			}
		});
		btn_delete.setBackground(new Color(255, 128, 128));
		btn_delete.setFont(new Font("���� ���", Font.PLAIN, 23));
		contentPane.add(btn_delete);

		// �˻��ϴ� ���� ������ �ؽ�Ʈ �ʵ�
		textField = new JTextField();
		textField.setBounds(26, 108, 308, 39);
		textField.setText("\uC0AC\uC6D0\uC544\uC774\uB514, \uBD80\uC11C\uBA85 \uAC80\uC0C9\uD574\uC8FC\uC138\uC694");
		textField.setFont(new Font("���� ���", Font.PLAIN, 20));
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 174, 596, 189);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("���� ���", Font.PLAIN, 16));
		
		//�α��� â���� ���� ��ư
		JButton btn_login = new JButton("\uB85C\uADF8\uC778");
		btn_login.setBounds(248, 384, 149, 39);
		btn_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				dispose();
				setVisible(false);
				try {
					new Login().setVisible(true);
				} catch (ClassNotFoundException | SQLException e1) {
//					e1.printStackTrace();
				}
				
			}
		});
		btn_login.setFont(new Font("���� ���", Font.PLAIN, 23));
		btn_login.setBackground(new Color(170, 255, 170));
		contentPane.add(btn_login);
		
		//JScrollPane scrollpane = new JScrollPane(table);
	
		
		
		// J table ��ü �˻� ��ư
		JButton btn_All_1 = new JButton("\uC804\uCCB4\uAC80\uC0C9");
		btn_All_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				ArrayList<AdminVO> list = dao.getAdmin(); //�޼ҵ� ȣ��
				//model �÷��� ����
				String[] header = {"���̵�", "��й�ȣ", "�̸�", "��ȭ��ȣ", "�μ�"};
				// ���� �÷� ������ ���� [������ DB�� ������ ������][�÷� ����]
				String [][] Data = new String[list.size()][header.length];
				
				for(int i = 0; i < Data.length; i++) {
					Data[i][0] = list.get(i).getId();
					Data[i][1] = list.get(i).getPw();
					Data[i][2] = list.get(i).getName();
					Data[i][3] = list.get(i).getTel();
					Data[i][4] = list.get(i).getDepartment();		
				} //end for
				
				// �� ����� Jtable�� �� �Է�
				model = new DefaultTableModel(Data, header);
				table = new JTable(model);
				scrollPane.setViewportView(table);
				
			}
		});
		btn_All_1.setFont(new Font("���� ���", Font.PLAIN, 23));
		btn_All_1.setBackground(new Color(255, 255, 128));
		btn_All_1.setBounds(469, 108, 149, 39);
		contentPane.add(btn_All_1);
		
		
		// J table ���� �˻� ��ư
		JButton btn_select_1 = new JButton("\uAC80\uC0C9");
		btn_select_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		btn_select_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String textName = textField.getText();
				String textdepartment = textField.getText();
				
				ArrayList<AdminVO> list = dao.getSingleAdmin(textName,textdepartment); //�޼ҵ� ȣ��
				
				//model �÷��� ����
				String[] header = {"���̵�", "��й�ȣ", "�̸�", "��ȭ��ȣ", "�μ�"};
				// ���� �÷� ������ ���� [������ DB�� ������ ������][�÷� ����]
				String [][] Data = new String[list.size()][header.length];
				
				for(int i = 0; i < Data.length; i++) {
					Data[i][0] = list.get(i).getId();
					Data[i][1] = list.get(i).getPw();
					Data[i][2] = list.get(i).getName();
					Data[i][3] = list.get(i).getTel();
					Data[i][4] = list.get(i).getDepartment();		
				} //end for
				
				// �� ����� Jtable�� �� �Է�
				model = new DefaultTableModel(Data, header);
				table = new JTable(model);
				scrollPane.setViewportView(table);
		
				
				
			}
		});
		btn_select_1.setFont(new Font("���� ���", Font.PLAIN, 23));
		btn_select_1.setBackground(new Color(255, 255, 128));
		btn_select_1.setBounds(351, 107, 91, 39);
		contentPane.add(btn_select_1);
		
		JButton btninsert = new JButton("\uC0AC\uC6D0 \uCD94\uAC00");
		btninsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btninsert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				setVisible(false);
				try {
					new Join().setVisible(true);
				} catch (ClassNotFoundException e1) {
//					e1.printStackTrace();
				} catch (SQLException e1) {
//					e1.printStackTrace();
				}
			}
		});
		btninsert.setBackground(SystemColor.textHighlight);
		btninsert.setBounds(303, 10, 149, 39);
		btninsert.setFont(new Font("���� ���", Font.PLAIN, 23));
	
		contentPane.add(btninsert);

	}
}
