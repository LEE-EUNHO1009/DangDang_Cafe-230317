package customerManager;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.im.InputContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Customer extends JPanel {
	private JTextField tfName;
	private JTextField tfMobile;
	private JTextField tfAddress;
	private JTextField tfBirthSolar;
	private JTextField tfBirthLunar;
	private JButton btnSearchAddress;
	private JButton btnCalendar;
	private int type;
	private JLabel lblImage;
	private JLabel lblCountDown;
	private JCheckBox ckLunar;
	private String file;
	private String originAttrs[] = new String[8];
	private JButton btnAll;
	private JTextField tfCustomerCoupon;
	private JTextField tfMobile2;
	private JButton btnCouponAll;
	private int count = 0;
	private JButton btnCouponReset;
	
	public Customer() {
		setLayout(null);
		
		lblImage = new JLabel("");
		lblImage.addMouseListener(new MouseAdapter() {
			

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					if(type % 2 == 1 ) {  // 삭제/조회가 아니라면 열기 대화상자를 열어 사진을 선택한다.						
						JFileChooser chooser = new JFileChooser();
						FileNameExtensionFilter filter = 
								new FileNameExtensionFilter("그림파일", "png", "gif", "jpg");
						chooser.setFileFilter(filter);
						
						int ret = chooser.showOpenDialog(null);
						if(ret == JFileChooser.APPROVE_OPTION) {
							file = chooser.getSelectedFile().getPath();
							ImageIcon icon = new ImageIcon(file);
							Image image = icon.getImage();
							image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
							ImageIcon pic = new ImageIcon(image);
							lblImage.setIcon(pic);
						}
					}
				}
			}
		});
		lblImage.setToolTipText("더블클릭하여 신분증을 선택하시오");
		lblImage.setOpaque(true);
		lblImage.setBackground(new Color(135, 206, 250));
		lblImage.setBounds(22, 22, 271, 180);
		add(lblImage);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(305, 36, 57, 15);
		add(lblName);
		
		JLabel lblMobile = new JLabel("전화번호:");
		lblMobile.setBounds(305, 66, 57, 15);
		add(lblMobile);
		
		JLabel lblAddress = new JLabel("주소:");
		lblAddress.setBounds(305, 96, 57, 15);
		add(lblAddress);
		
		JLabel lblBirth = new JLabel("생일:");
		lblBirth.setBounds(305, 126, 57, 15);
		add(lblBirth);
		
		tfName = new JTextField();
		tfName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try{
                   InputContext inCtx2 = tfName.getInputContext(); // comp는 text component
                   Character.Subset[] subset2 = { Character.UnicodeBlock.HANGUL_SYLLABLES  };
                   inCtx2.setCharacterSubsets( subset2 );
                  }catch(Exception ee) { ee.printStackTrace(); }
			}
		});
		tfName.setColumns(10);
		tfName.setBounds(373, 33, 116, 21);
		add(tfName);
		
		tfMobile = new JTextField();
		tfMobile.setColumns(10);
		tfMobile.setBounds(373, 63, 116, 21);
		add(tfMobile);
		
		tfAddress = new JTextField();
		tfAddress.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try{
                   InputContext inCtx2 = tfAddress.getInputContext(); // comp는 text component
                   Character.Subset[] subset2 = { Character.UnicodeBlock.HANGUL_SYLLABLES  };
                   inCtx2.setCharacterSubsets( subset2 );
                  }catch(Exception ee) { ee.printStackTrace(); }
			}
		});
		tfAddress.setColumns(10);
		tfAddress.setBounds(373, 93, 271, 21);
		add(tfAddress);
		
		tfBirthSolar = new JTextField();
		tfBirthSolar.setBackground(new Color(255, 255, 0));
		tfBirthSolar.setHorizontalAlignment(SwingConstants.RIGHT);
		tfBirthSolar.setColumns(10);
		tfBirthSolar.setBounds(612, 123, 116, 21);
		add(tfBirthSolar);
		
		btnSearchAddress = new JButton("주소찾기");
		btnSearchAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String doro = JOptionPane.showInputDialog("도로명 입력:");
				WinDoroList winDoroList = new WinDoroList(doro);
				winDoroList.setModal(true);
				winDoroList.setVisible(true);
				tfAddress.setText(winDoroList.getAddress());
				tfAddress.requestFocus();  // 커서를 주는 이유는 상세 주소를 입력하기 위해서
			}
		});
		btnSearchAddress.setBounds(648, 92, 80, 23);
		add(btnSearchAddress);
		
		btnCalendar = new JButton("달력보기");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfBirthLunar.setText(winCalendar.getDate());
				
				Calendar cal = Calendar.getInstance();
				String strSolar = winCalendar.getDate();
				int year = cal.get(Calendar.YEAR);
				strSolar = year + strSolar.substring(4);
				tfBirthSolar.setText(strSolar);
				
				showCountDown();				
			}
		});
		btnCalendar.setBounds(501, 122, 43, 23);
		add(btnCalendar);
		
		ckLunar = new JCheckBox("음력");
		ckLunar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				
				if(ckLunar.isSelected()) { // 음력=>양력
					String strLunar = tfBirthLunar.getText();
					strLunar = strLunar.replaceAll("-", "");					
					strLunar = year + strLunar.substring(4, 6) + strLunar.substring(6);
					SolarLunar sl = new SolarLunar();
					String strSolar = sl.fromLunar(strLunar);
					strSolar = year + "-" + strSolar.substring(4, 6) + "-" + strSolar.substring(6);
					tfBirthSolar.setText(strSolar);
					
				}else { // 양력=>양력					
					String strSolar = tfBirthLunar.getText();
					strSolar = year + strSolar.substring(4);
					tfBirthSolar.setText(strSolar);
				}				
				showCountDown();
			}
		});
		ckLunar.setBounds(552, 122, 57, 23);
		add(ckLunar);
		
		tfBirthLunar = new JTextField();
		tfBirthLunar.setHorizontalAlignment(SwingConstants.RIGHT);
		tfBirthLunar.setColumns(10);
		tfBirthLunar.setBounds(374, 123, 116, 21);
		add(tfBirthLunar);
		
		lblCountDown = new JLabel("");
		lblCountDown.setForeground(new Color(255, 0, 0));
		lblCountDown.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountDown.setFont(new Font("굴림", Font.BOLD, 20));
		lblCountDown.setBounds(373, 154, 116, 34);
		add(lblCountDown);
		
		btnAll = new JButton("통합");
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(type==1) { //멤버 추가 sql
					addCustomer();
					clearInfo();
				}else if(type==2) { // 멤버 삭제 sql
					if(JOptionPane.showConfirmDialog(null, "정말 삭제할까요?") == JOptionPane.YES_OPTION) {
						removeRecord();
						clearInfo();
					}
				}else if(type==3) { // 멤버 변경 sql
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
		btnAll.setBounds(635, 32, 93, 49);
		add(btnAll);
		
		JLabel lblCoupon = new JLabel("도장:");
		lblCoupon.setBounds(544, 158, 50, 15);
		add(lblCoupon);
		
		tfCustomerCoupon = new JTextField();
		tfCustomerCoupon.setColumns(10);
		tfCustomerCoupon.setBounds(597, 154, 47, 21);
		add(tfCustomerCoupon);
		
		btnCouponAll = new JButton("쿠폰");
		btnCouponAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnCouponAll) {
					count++;
					tfCustomerCoupon.setText(count + "");
				} else {
					count = 0;
					tfCustomerCoupon.setText(count + "");
				}
				//showCoupon();
			}
		});
		btnCouponAll.setBounds(664, 154, 64, 23);
		add(btnCouponAll);
		
		btnCouponReset = new JButton("리셋");
		btnCouponReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnCouponReset) {
					count=0;
					tfCustomerCoupon.setText(count + "");
				} else {
					;
				}
			}
		});
		btnCouponReset.setBounds(664, 187, 64, 23);
		add(btnCouponReset);
		
		tfMobile2 = new JTextField();
		tfMobile2.setColumns(10);
		tfMobile2.setBounds(501, 63, 116, 21);
		add(tfMobile2);
	}
	protected void modifyRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "update customerTBL set mobile=?, mobile2=?, address=?, ";
			sql = sql +  "birth=?, lunar=?, coupon=?, image=? where name=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfMobile.getText());
			pstmt.setString(2, tfMobile2.getText());
			pstmt.setString(3, tfAddress.getText());
			pstmt.setString(4, tfBirthLunar.getText());
			if(ckLunar.isSelected())
				pstmt.setString(5, "1");
			else
				pstmt.setString(5, "0");
			pstmt.setString(6, tfCustomerCoupon.getText());
			pstmt.setString(7, file);
			pstmt.setString(8, tfName.getText());
			
			pstmt.executeUpdate();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류44");
		} 
		
	}
	protected boolean isModify() {
		if(!originAttrs[0].equals(tfName.getText())) {
			originAttrs[0] = tfName.getText();
			return true;
		}else if(!originAttrs[1].equals(tfMobile.getText())) {
			originAttrs[1] = tfMobile.getText();
			return true;
		}else if(!originAttrs[2].equals(tfMobile2.getText())) {
			originAttrs[2] = tfMobile2.getText();
			return true;
		}else if(!originAttrs[3].equals(tfAddress.getText())) {
			originAttrs[3] = tfAddress.getText();
			return true;
		}else if(!originAttrs[4].equals(tfBirthLunar.getText())) {
			originAttrs[4] = tfBirthLunar.getText();
			return true;
		}else if(!originAttrs[5].equals(ckLunar.isSelected() ? "1" : "0")) {
			originAttrs[5] = ckLunar.isSelected() ? "1" : "0";
			return true;
		}else if(!originAttrs[6].equals(tfCustomerCoupon.getText())) {
			originAttrs[6] = tfCustomerCoupon.getText();
			return true;	
		}else if(!originAttrs[7].equals(file))
			return true;
		
		else
			return false;		
	}
	protected void removeRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "delete from customerTBL where name='" + tfName.getText() + "'";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류55");
		} 		
	}
	
	protected void clearInfo() {
		// TODO Auto-generated method stub
		tfName.setText("");
		tfMobile.setText("");
		tfMobile2.setText("");
		tfAddress.setText("");
		tfBirthLunar.setText("");
		tfBirthSolar.setText("");
		lblCountDown.setText("");
		ckLunar.setSelected(false);
		tfCustomerCoupon.setText("");
		file = "";
		
		tfName.requestFocus();
	}
	protected void addCustomer() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			//=============================================		
			String sql = "insert into customerTBL values(?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfName.getText());
			pstmt.setString(2, tfMobile.getText());
			pstmt.setString(3, tfMobile2.getText());
			pstmt.setString(4, tfAddress.getText());
			pstmt.setString(5, tfBirthLunar.getText());
			if(ckLunar.isSelected())
				pstmt.setString(6, "1");  // 음력
			else
				pstmt.setString(6, "0");  // 양력		
			pstmt.setString(7, tfCustomerCoupon.getText());
			pstmt.setString(8, file);
			pstmt.executeUpdate();
			pstmt.close();
			
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류11");
		} 
		
	}
	protected void showCountDown() {		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select datediff('" + tfBirthSolar.getText() + "', now()) as CD";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				if(rs.getInt("CD")==0)
					lblCountDown.setText("생일 축하!!!");
				else
					lblCountDown.setText(rs.getInt("CD")+"일 남음");
			rs.close();
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류33");
		} 
		
	}
	public Customer(int sType) {
		// TODO Auto-generated constructor stub
		this();

		type = sType;
		
		if(type == 1) { // 추가
			
			btnAll.setText("회원 추가");
		}else if(type==2) { //삭제
			btnCouponAll.setVisible(false);
			btnCouponReset.setVisible(false);	
			btnAll.setText("회원 탈퇴");			
		}else if(type==3){ //변경
			
			btnAll.setText("회원 변경");
		}else { //조회
			btnCouponAll.setVisible(false);
			btnCouponReset.setVisible(false);
			btnAll.setVisible(false);
		}
	}
	
	public Customer(String[] temp) {
		this(4);		
		tfName.setText(temp[0]);
		tfMobile.setText(temp[1]);
		tfMobile2.setText(temp[2]);
		tfAddress.setText(temp[3]);
		tfBirthLunar.setText(temp[4]);
		if(temp[5].equals("1"))
			ckLunar.setSelected(true);
		else
			ckLunar.setSelected(false);
		tfCustomerCoupon.setText(temp[6]);
		file = temp[7];
		ImageIcon icon = new ImageIcon(file);
		Image image = icon.getImage();
		image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
		ImageIcon pic = new ImageIcon(image);
		lblImage.setIcon(pic);
	}
}
