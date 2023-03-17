package DogManager;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.ibm.icu.util.Calendar;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Dog extends JPanel {
	private JTextField tfDogName;
	private JTextField tfDogBirth;
	private JTextField tfDogWeight;
	private JTextField tfDogCoupon;
	private JTextField tfDogGuardianName;
	private JTextField tfDogNotandum;
	private int type;
	private JLabel lblDogCount;
	private JButton btnDogGuardian;
	private JButton btnDogCalendar;
	private JButton btnMinor;
	private JButton btnCharge;
	private JButton btnDogAll;
	
	private JButton btnDogRemove;
	private JButton btnDogAdd;
	private JButton btnDogModify;
	private JButton btnDogCouponAdd;
	private JButton btnDogCouponReset;
	private String file;
	private JTextArea taDogNotandum;
	private JComboBox<String> cbDog;
	private String originAttrs[] = new String[8];
	private JLabel lblDogImage;
	private int count = 0;
	private JButton btnDogCouponAll;


	/**
	 * Create the panel.
	 */
	public Dog() {
		setForeground(new Color(255, 0, 0));
		setLayout(null);
		
		lblDogImage = new JLabel("");
		lblDogImage.addMouseListener(new MouseAdapter() {
			

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					if (type % 2 ==1) {
						JFileChooser chooser = new JFileChooser();
						FileNameExtensionFilter filter =
								new FileNameExtensionFilter("그림파일", "png", "gif","jpg");
						chooser.setFileFilter(filter);
						
						int ret = chooser.showOpenDialog(null);
						if(ret == JFileChooser.APPROVE_OPTION) {
							file = chooser.getSelectedFile().getPath();
							ImageIcon icon = new ImageIcon(file);
							Image image = icon.getImage();
							image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
							ImageIcon Pic = new ImageIcon(image);
							lblDogImage.setIcon(Pic);
						}
					}
				}
			}
		});
		
		lblDogImage.setToolTipText("더블클릭하여 강아지 사진 경로를 입력하시오");
		lblDogImage.setOpaque(true);
		lblDogImage.setBackground(new Color(224, 255, 255));
		lblDogImage.setBounds(12, 10, 150, 200);
		add(lblDogImage);
		
		JLabel lblDogName = new JLabel("이름:");
		lblDogName.setBounds(174, 22, 64, 15);
		add(lblDogName);
		
		JLabel lblDog = new JLabel("견종:");
		lblDog.setBounds(174, 52, 57, 15);
		add(lblDog);
		
		JLabel lblDogBirth = new JLabel("생년월일:");
		lblDogBirth.setBounds(174, 115, 57, 15);
		add(lblDogBirth);
		
		JLabel lblDogCoupon = new JLabel("도장:");
		lblDogCoupon.setBounds(174, 176, 57, 15);
		add(lblDogCoupon);
		
		JLabel lblDogNotandum = new JLabel("주의사항:");
		lblDogNotandum.setBounds(12, 220, 57, 15);
		add(lblDogNotandum);
		
		tfDogName = new JTextField();
		tfDogName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					showRecord(tfDogName.getText());
				}
			}
		});
		tfDogName.setColumns(10);
		tfDogName.setBounds(242, 19, 116, 21);
		add(tfDogName);
		
		tfDogBirth = new JTextField();
		tfDogBirth.setColumns(10);
		tfDogBirth.setBounds(242, 112, 116, 21);
		add(tfDogBirth);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 240, 520, 325);
		add(scrollPane);
		
		taDogNotandum = new JTextArea();
		scrollPane.setViewportView(taDogNotandum);
		
		tfDogNotandum = new JTextField();
		tfDogNotandum.setText("가족 | 신체사항 | 약 | 식사...");
		tfDogNotandum.setEnabled(false);
		scrollPane.setColumnHeaderView(tfDogNotandum);
		tfDogNotandum.setColumns(10);
		
		btnDogAll = new JButton("통합");
		btnDogAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(type==5) { //강아지 추가 sql
					addDog();
					clearInfo();
				}else if(type==6) { // 강아지 삭제 sql
					if(JOptionPane.showConfirmDialog(null, "정말 삭제할까요?") == JOptionPane.YES_OPTION) {
						removeRecord();
						clearInfo();
					}
				}else if(type==7) { // 강아지 변경 sql
					if(isModify()) {
						modifyRecord();
						System.out.println("변경O");
					}
					else
						System.out.println("변경X");
				}else {
					;
				}
			}
		});
		btnDogAll.setBounds(370, 172, 150, 49);
		add(btnDogAll);
		
		btnDogCalendar = new JButton("달력보기");
		btnDogCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfDogBirth.setText(winCalendar.getDate());
				
				Calendar cal= Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				
				showCountDown();
			}
		});
		btnDogCalendar.setBounds(370, 111, 116, 23);
		add(btnDogCalendar);
		
		
		
		cbDog = new JComboBox();
		cbDog.setModel(new DefaultComboBoxModel(new String[] {"미니어처 푸들", "베들링턴 테리어", "보더콜리", "보스턴 테리어", "비글", "사모예드", "셰틀랜드 쉽독", "시바 이누", "시베리안 허스키", "아메리칸 코카 스파니엘", "웰시 코기", "제페니스 스피츠", "진돗새", "코커 스패니얼", "펨브록 웰시코기", "풍산개", "프렌치 불도그", "몰티즈", "미니어처 핀셔", "시츄", "요크셔 테리어", "이탈리안 그레이하운드", "치와와", "토이 푸들", "파피용", "페키니즈", "포메라니안", "닥스훈트", "미니어처 슈나우저", "비숑 프리제", "카바리에 킹찰스 스파니", "퍼그", "푸들"}));
		cbDog.setBounds(243, 48, 116, 23);
		add(cbDog);
		
		JLabel lblDogWeight = new JLabel("몸무게:");
		lblDogWeight.setBounds(174, 84, 43, 15);
		add(lblDogWeight);
		
		tfDogWeight = new JTextField();
		tfDogWeight.setColumns(10);
		tfDogWeight.setBounds(242, 81, 116, 21);
		add(tfDogWeight);
		
		JLabel lblDogKg = new JLabel("Kg");
		lblDogKg.setEnabled(false);
		lblDogKg.setBounds(364, 84, 43, 15);
		add(lblDogKg);
		
		lblDogCount = new JLabel("");
		lblDogCount.setToolTipText("생일 계산");
		lblDogCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblDogCount.setForeground(Color.RED);
		lblDogCount.setBounds(242, 143, 116, 15);
		add(lblDogCount);
		
		btnDogCouponAll = new JButton("쿠폰");
		btnDogCouponAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnDogCouponAll) {
					count++;
					tfDogCoupon.setText(count + "");
				} else {
					count = 0;
					tfDogCoupon.setText(count + "");
				}
				//showCoupon();
			}
		});
		btnDogCouponAll.setBounds(294, 172, 64, 23);
		add(btnDogCouponAll);
		
		tfDogCoupon = new JTextField();
		tfDogCoupon.setColumns(10);
		tfDogCoupon.setBounds(243, 173, 47, 21);
		add(tfDogCoupon);
		
		JLabel lblDogGuardianName = new JLabel("보호자명:");
		lblDogGuardianName.setBounds(370, 22, 57, 15);
		add(lblDogGuardianName);
		
		tfDogGuardianName = new JTextField();
		tfDogGuardianName.setColumns(10);
		tfDogGuardianName.setBounds(426, 19, 106, 21);
		add(tfDogGuardianName);
		
		JLabel lblCouponMax = new JLabel("");
		
		lblCouponMax.setToolTipText("10개 도장 되면 리셋");
		lblCouponMax.setForeground(Color.RED);
		lblCouponMax.setBounds(242, 199, 48, 15);
		add(lblCouponMax);
		
		btnDogGuardian = new JButton("보호자정보");
		btnDogGuardian.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				DogGuardian dogGuardian = new DogGuardian();
				dogGuardian.setModal(true);
				dogGuardian.setVisible(true);
				tfDogGuardianName.setText(dogGuardian.getName());
			}
		});
		btnDogGuardian.setBounds(370, 48, 162, 23);
		add(btnDogGuardian);
		
		btnMinor = new JButton("미성년자 동의서");
		btnMinor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DogMinor dogMinor = new DogMinor();
				dogMinor.setModal(true);
				dogMinor.setVisible(true);
				
			}
		});
		btnMinor.setBounds(370, 144, 150, 23);
		add(btnMinor);
		
		btnDogCouponReset = new JButton("리셋");
		btnDogCouponReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnDogCouponReset) {
					count=0;
					tfDogCoupon.setText(count + "");
				} else {
					;
				}
			}
		});
		btnDogCouponReset.setBounds(294, 205, 64, 23);
		add(btnDogCouponReset);

	}

	

	protected void showRecord(String dName) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select * from dogTBL where name ='"+dName+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				tfDogGuardianName.setText(rs.getString("guardian"));
				cbDog.setSelectedItem(rs.getString("class"));
				tfDogWeight.setText(rs.getString("weight"));
				tfDogBirth.setText(rs.getString("birth"));
				tfDogCoupon.setText(rs.getString("coupon"));
				tfDogNotandum.setText(rs.getString("notandum"));
				
				file = rs.getString("image");
				ImageIcon icon = new ImageIcon(file);
				Image image = icon.getImage();
				image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
				ImageIcon pic = new ImageIcon(image);
				lblDogImage.setIcon(pic);
			}
			
			
			rs.close(); 
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 
		
	}



	protected void modifyRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "update dogTBL set guardian=?, class=?, weight=?, ";
			sql = sql +  "birth=?, coupon=?, notandum=?, image=? where name=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, tfDogGuardianName.getText());
			pstmt.setString(2, cbDog.getSelectedItem().toString());	
			pstmt.setString(3, tfDogWeight.getText());
			pstmt.setString(4, tfDogBirth.getText());
			pstmt.setString(5, tfDogCoupon.getText());
			pstmt.setString(6, taDogNotandum.getText());
			pstmt.setString(7, file);
			pstmt.setString(8, tfDogName.getText());
			
			pstmt.executeUpdate();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 
		
	}

	protected boolean isModify() {
		/*if(!originAttrs[0].equals(tfDogName.getText())) {
			originAttrs[0] = tfDogName.getText();
			return true;
		}else if(!originAttrs[1].equals(tfDogGuardianName.getText())) {
			originAttrs[1] = tfDogGuardianName.getText();
			return true;
		}else if(!originAttrs[2].equals(cbDog.getSelectedItem().toString())) {
			originAttrs[2] = cbDog.getSelectedItem().toString();
			return true;
		}else if(!originAttrs[3].equals(tfDogWeight.getText())) {
			originAttrs[3] = tfDogWeight.getText();
			return true;
		}else if(!originAttrs[4].equals(tfDogBirth.getText())) {
			originAttrs[4] = tfDogBirth.getText();
			return true;
		}else if(!originAttrs[5].equals(tfDogCoupon.getText())) {
			originAttrs[5] = tfDogCoupon.getText();
			return true;
		}else if(!originAttrs[6].equals(taDogNotandum.getText())) {
			originAttrs[6] = taDogNotandum.getText();
			return true;
		}else if(!originAttrs[7].equals(file))
			return true;
		else
			return false;*/
		return true;
	}

	protected void removeRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "delete from dogTBL where name='" + tfDogName.getText() + "'";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 		
		
		
	}

	protected void clearInfo() {
		tfDogName.setText("");
		tfDogGuardianName.setText("");
		cbDog.setSelectedIndex(0);
		tfDogWeight.setText("");
		tfDogBirth.setText("");
		tfDogCoupon.setText("");
		taDogNotandum.setText("");
		file = "";		
		tfDogName.requestFocus();
		
	}

	protected void showDog(String gID) {
		clearInfo();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select * from dogTBL where guardian='" + gID + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int idx=0;
			if(rs.next()) {
				for(idx=0 ; idx < originAttrs.length ; idx++)
					originAttrs[idx] = rs.getString(idx+1);
				
				//=====================================================
				tfDogName.setText(rs.getString(1));
				tfDogGuardianName.setText(gID);
				cbDog.setSelectedItem(rs.getString(3));
				tfDogWeight.setText(rs.getString(4));
				tfDogBirth.setText(rs.getString(5));
				tfDogCoupon.setText(rs.getString(6));
				taDogNotandum.setText(rs.getString(7));
				
				file = rs.getString(8);
				ImageIcon icon = new ImageIcon(file);
				Image image = icon.getImage();
				image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
				ImageIcon pic = new ImageIcon(image);
				lblDogImage.setIcon(pic);
					
				}
			rs.close(); 
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 
		
		
	}

	protected void addDog() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "insert into dogTBL values(?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfDogName.getText());
			pstmt.setString(2, tfDogGuardianName.getText());
			pstmt.setString(3, cbDog.getSelectedItem().toString());		
			pstmt.setString(4, tfDogWeight.getText());
			pstmt.setString(5, tfDogBirth.getText());
			pstmt.setString(6, tfDogCoupon.getText());
			pstmt.setString(7, taDogNotandum.getText());			
			pstmt.setString(8, file);
			
			pstmt.executeUpdate();
			pstmt.close();
			
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 
		
	}

	protected void showCountDown() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select datediff('" + tfDogBirth.getText() + "', now()) as CD";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				if(rs.getInt("CD")==0)
					lblDogCount.setText("생일 축하!!!");
				else
					lblDogCount.setText(rs.getInt("CD")+"일 남음");
			rs.close();
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		}
	}
	public Dog(int dType) {
		// TODO Auto-generated constructor stub
		this();
		type = dType;
		System.out.println(type);
		if(type == 5) { // 추가
			
			btnDogAll.setText("강아지 추가");
		}else if(type==6) { //삭제
			btnDogCalendar.setVisible(false);
			btnDogCouponAll.setVisible(false);
			btnDogCouponReset.setVisible(false);
			btnDogAll.setText("강아지 탈퇴");
			
		}else if(type==7){ //변경
			btnDogAll.setText("강아지 변경");
			
		}else { //강아지 조회
			btnDogCouponAll.setVisible(false);
			btnDogCouponReset.setVisible(false);
			btnDogAll.setVisible(false);
		}
		
	}
	public Dog(String[] temp, int dogSelect) {
		
		this(dogSelect);
		tfDogName.setText(temp[0]);
		tfDogGuardianName.setText(temp[1]);
		cbDog.setSelectedItem(temp[2]);
		tfDogWeight.setText(temp[3]);
		tfDogBirth.setText(temp[4]);
		tfDogCoupon.setText(temp[5]);
		taDogNotandum.setText(temp[6]);
		file = temp[7];
		ImageIcon icon = new ImageIcon(file);
		Image image = icon.getImage();
		image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
		ImageIcon pic = new ImageIcon(image);
		lblDogImage.setIcon(pic);
		
	}
}
