package customerManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.border.BevelBorder;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class CustomerAllShow extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerAllShow dialog = new CustomerAllShow();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public CustomerAllShow() {
		setTitle("전체 회원 보기");
		setBounds(100, 100, 795, 449);
		
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
						
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select count(*) from customerTBL";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int total = 0;
			if(rs.next())
				total = rs.getInt(1);
			
			panel.setLayout(new GridLayout(total,1,10,10));
			panel.setPreferredSize(new Dimension(600, total*200));
			
			sql = "select * from customerTBL";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String temp[] = new String[8];
				for(int i=0; i<temp.length ; i++)
					temp[i] = rs.getString(i+1);
				
				Customer customer = new Customer(temp);
				panel.add(customer);
			}		
			
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 		
		
		
		
		JScrollPane jsp = new JScrollPane(
				panel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
			);
		
		jsp.getVerticalScrollBar().setUnitIncrement(20); // 스크롤 속도 조절
		
		container.add(jsp);	
		
		
	}
}
