package admin;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;

import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;


import java.awt.FlowLayout;

public class Calender extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel panelMenu = new JPanel();
	private JPanel panelMonthSelc = new JPanel();
	private JComboBox comboBoxYear = new JComboBox(new Object[] { 2023, 2024, 2025 });

	// 접속객체에 필요한 변수 선언
	private Connection con;
	PreparedStatement pstmt = null;

	// 검색할때 필요한 변수 선언
	ResultSet rs = null;

	private JTextArea textArea_department;
	JTextArea textArea_my;

	private JButton buttMonth1 = new JButton("1월");
	private JButton buttMonth2 = new JButton("2월");
	private JButton buttMonth3 = new JButton("3월");
	private JButton buttMonth4 = new JButton("4월");
	private JButton buttMonth5 = new JButton("5월");
	private JButton buttMonth6 = new JButton("6월");
	private JButton buttMonth7 = new JButton("7월");
	private JButton buttMonth8 = new JButton("8월");
	private JButton buttMonth9 = new JButton("9월");
	private JButton buttMonth10 = new JButton("10월");
	private JButton buttMonth11 = new JButton("11월");
	private JButton buttMonth12 = new JButton("12월");
	private JPanel panelProject = new JPanel();
	private JPanel panelCalFamily = new JPanel();
	private JLabel label = new JLabel("달력");
	private JScrollPane scrPaneCal = new JScrollPane();
	private JScrollPane scrProject = new JScrollPane();
	private JTextArea txtProject = new JTextArea();
	private JPanel panelDiary = new JPanel();

	private JScrollPane scrDiary = new JScrollPane();
	private JTextArea txtDiary = new JTextArea();

	private static Object calendarRow[][] = new Object[6][7];
	private static Object calendarColumn[] = { "일", "월", "화", "수", "목", "금", "토" };
	private JTable tableCalendar = new JTable(calendarRow, calendarColumn);
	private Object weekPlanRow[][] = new Object[6][2]; // [][0]은 한 주의 순서, [][1]은
														// 할 일
	private Object weekPlanColumn[] = { "Week", "Todo" };

	JLabel lbl_my = new JLabel("나의정보");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calender frame = new Calender();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 기본생성자 <= 여기서 디자인 확인 가능
	// 로그인에서 가져온 아이디 등등 인자를 받는 생성자로 실행시켜줄것임
	public Calender() {
		// 접속객체.접속해주는 메소드()
//		con = new DBConn().getConnection();
//		System.out.println("Calender DB 접속완료");
//
//		setResizable(false);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 781, 525);
//		contentPane = new JPanel();
//		contentPane.setBackground(SystemColor.activeCaption);
//		contentPane.setForeground(new Color(255, 255, 255));
//		contentPane.setAutoscrolls(true);
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//
//		setLayout();
//		setAdd(id, name, department, tel);
//		getAction();
//		setTable();
//
//		cellClickListener();
	}

	// 인자를 받는 생성자 <= 로그인 하면 이쪽페이지가 뜸
	public Calender(String id, String name, String department, String tel) throws ClassNotFoundException, SQLException {

		setTitle("Mypage");

		// 접속객체.접속해주는 메소드()
		con = new DBConn().getConnection();
		System.out.println("Calender DB 접속완료");

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 781, 525);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setAutoscrolls(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setLayout();
		setAdd(id, name, department, tel);
		getAction();
		setTable();

		cellClickListener();
	}

	private void setLayout() {
		// TODO Auto-generated method stub
		panelMonthSelc.setBackground(new Color(102, 153, 204));
		panelMonthSelc.setLayout(new GridLayout(0, 1, 0, 0));

		panelMonthSelc.setBounds(0, 10, 92, 481);
		panelCalFamily.setBounds(104, 6, 468, 239);
		label.setForeground(new Color(255, 255, 255));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		tableCalendar.setForeground(SystemColor.inactiveCaptionText);
		tableCalendar.setBackground(new Color(255, 239, 213));
		tableCalendar.setRowSelectionAllowed(false);
		txtProject.setLineWrap(true);

		txtProject.setForeground(SystemColor.inactiveCaptionText);
		txtProject.setBackground(new Color(255, 250, 205));
		txtProject.setText("   ");

		comboBoxYear.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth1.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth2.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth3.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth4.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth5.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth6.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth7.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth8.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth9.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth10.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth11.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		buttMonth12.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		label.setFont(new Font("휴먼편지체", Font.BOLD, 14));
		tableCalendar.setFont(new Font("휴먼편지체", Font.PLAIN, 14));

		txtProject.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		txtDiary.setFont(new Font("휴먼편지체", Font.PLAIN, 14));

	}

	private void cellClickListener() {
		// TODO Auto-generated method stub
		tableCalendar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				int row = tableCalendar.rowAtPoint(event.getPoint());
				int col = tableCalendar.columnAtPoint(event.getPoint());

			}

		});

	}

	private void getAction() {
		// TODO Auto-generated method stub
		buttMonth1.addActionListener(this);
		buttMonth2.addActionListener(this);
		buttMonth3.addActionListener(this);
		buttMonth4.addActionListener(this);
		buttMonth5.addActionListener(this);
		buttMonth6.addActionListener(this);
		buttMonth7.addActionListener(this);
		buttMonth8.addActionListener(this);
		buttMonth9.addActionListener(this);
		buttMonth10.addActionListener(this);
		buttMonth11.addActionListener(this);
		buttMonth12.addActionListener(this);
	}

	private void setAdd(String id, String name, String department, String tel)
			throws ClassNotFoundException, SQLException {

		// 접속객체.접속해주는 메소드()
		con = new DBConn().getConnection();
		System.out.println("MainPage DB 접속완료");

		// TODO Auto-generated method stub
		contentPane.add(panelMenu);
		contentPane.add(panelMonthSelc);
		panelMonthSelc.add(comboBoxYear);
		panelMonthSelc.add(buttMonth1);
		panelMonthSelc.add(buttMonth2);
		panelMonthSelc.add(buttMonth3);
		panelMonthSelc.add(buttMonth4);
		panelMonthSelc.add(buttMonth5);
		panelMonthSelc.add(buttMonth6);
		panelMonthSelc.add(buttMonth7);
		panelMonthSelc.add(buttMonth8);
		panelMonthSelc.add(buttMonth9);
		panelMonthSelc.add(buttMonth10);
		panelMonthSelc.add(buttMonth11);
		panelMonthSelc.add(buttMonth12);
		contentPane.add(panelCalFamily);

		panelProject.add(scrProject);
		panelCalFamily.add(label, BorderLayout.NORTH);
		panelCalFamily.add(scrPaneCal, BorderLayout.CENTER);

		JLabel lbl_my = new JLabel("\uB098\uC758 \uC815\uBCF4");
		lbl_my.setBounds(597, 10, 114, 29);
		contentPane.add(lbl_my);

		textArea_my = new JTextArea(
				"이   름 : " + name + "\n" + "아이디 : " + id + "\n" + "부서명 : " + department + "\n" + "전화번호 : " + tel);
		textArea_my.setBounds(597, 38, 147, 207);
		contentPane.add(textArea_my);

		JLabel lbl_d = new JLabel(department + "사원 LIST");
		lbl_d.setBounds(597, 255, 114, 29);
		contentPane.add(lbl_d);

		// 여기에 부서별대로 안에 정보를 입력해줘야함!------------------------------------
		// 1)sql문으로 로그인한 아이디의 부서에 있는 이름, 전화번호가 조회되도록
		// 2)sql문에서 조회된 내용을 화면에 .append로 출력시키기
		textArea_department = new JTextArea();
		textArea_department.setBounds(597, 282, 147, 169);
		contentPane.add(textArea_department);

		// 조회
		String sql = "select id, name, tel, department from admin where department = ?";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, department);

			rs = pstmt.executeQuery();

			while (rs.next()) { // rs안에 값이 있으면 계속 무한반복

				String name1 = rs.getString("name");
				String tel1 = rs.getString("tel");

				// 출력
				textArea_department.append(name1 + "  " + tel1 + "\n");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 여길 클릭하면 메모장 창이 뜬다.
		JButton btnNewButton = new JButton("메모장 추가");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				dispose();
				setVisible(false);
				try {
					new Memo(id, name, department, tel).setVisible(true);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(104, 461, 475, 23);
		contentPane.add(btnNewButton);

		lbl_m.setBounds(104, 255, 114, 29);
		contentPane.add(lbl_m);
		
		// ---------------------------------------------------------------------------------Jlist

		ArrayList<MemoVO> list = new ArrayList<>();

		String sql2 = "SELECT memo_id, memo_name, Notepad FROM memo WHERE memo_id = ?";

		pstmt = con.prepareStatement(sql2);
		
		String myId = id;

		pstmt.setString(1, myId);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			myId = rs.getString("memo_id");
			String memo_name = rs.getString("memo_name");
			String notepad = rs.getString("notepad");

			MemoVO memovo = new MemoVO(myId, memo_name, notepad);
			list.add(memovo);
		}

		String[] memoData = new String[list.size()];

		for (int i = 0; i < memoData.length; i++) {
			memoData[i] = list.get(i).getMemo_id();
			memoData[i] = list.get(i).getMemo_name();
			memoData[i] = list.get(i).getNotepad();

			// if문으로 지금 로그인 한 사원의 메모만 뜨도록.........

			list_memo = new JList(memoData);
			list_memo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

		// ---------------------------------------------------------------------------------Jlist
		
		scrollPane.setBounds(104, 282, 475, 167);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(list_memo);

		panelDiary.add(scrDiary, BorderLayout.CENTER);

	}

	private void setTable() {
		// TODO Auto-generated method stub
		scrPaneCal.setViewportView(tableCalendar);

		tableCalendar.setFillsViewportHeight(true);
		tableCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		/* 셀 간격 조정 */
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
		tableCalendar.setRowHeight(29);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == buttMonth1)
			calendarHandler(1);
		else if (e.getSource() == buttMonth2)
			calendarHandler(2);
		else if (e.getSource() == buttMonth3)
			calendarHandler(3);
		else if (e.getSource() == buttMonth4)
			calendarHandler(4);
		else if (e.getSource() == buttMonth5)
			calendarHandler(5);
		else if (e.getSource() == buttMonth6)
			calendarHandler(6);
		else if (e.getSource() == buttMonth7)
			calendarHandler(7);
		else if (e.getSource() == buttMonth8)
			calendarHandler(8);
		else if (e.getSource() == buttMonth9)
			calendarHandler(9);
		else if (e.getSource() == buttMonth10)
			calendarHandler(10);
		else if (e.getSource() == buttMonth11)
			calendarHandler(11);
		else if (e.getSource() == buttMonth12)
			calendarHandler(12);

	}

	private void calendarHandler(int month) {
		Calender.setMonth = month;
		setCalendar();
		setTable();
	}

	private static int setYear = 2023; // 연도 지정
	private static int setMonth = 0; // 달 지정
	private static int setDate = 0; // 날짜 지정
	private JLabel lbl_m = new JLabel("MEMO LIST");
	private JList list_memo = new JList();
	private final JScrollPane scrollPane = new JScrollPane();

	private void setCalendar() {
		Calendar setDay = Calendar.getInstance();
		setDay.set(setYear, setMonth - 1, 1);

		int iStartDay = setDay.get(Calendar.DAY_OF_WEEK); // 지정한 달의 첫째날 알아오기
		int iEndDate = setDay.getActualMaximum(Calendar.DATE); // 지정한 달의 마지막 날
																// 알아오기
		int date = 1; // 늘어나면서 날짜 지정

		// 달력 출력 기능
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (i == 0) {
					if (iStartDay - 1 > j)
						calendarRow[i][j] = "";
					else
						calendarRow[i][j] = date++;
				} else {
					if (date > iEndDate)
						calendarRow[i][j] = "";
					else
						calendarRow[i][j] = date++;
				}
			}
		}
	} // 인자받는 생성자-end

}
