package DogManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import customerManager.Customer;


public class DogAllShow extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DogAllShow dialog = new DogAllShow(8);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param DogSelect 
	 */
	public DogAllShow(int DogSelect) {
		setTitle("댕댕이 조회");
		setBounds(100, 100, 586, 616);
		
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
				
		JPanel panel = new JPanel();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select count(*) from dogTBL";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int total = 0;
			if(rs.next())
				total = rs.getInt(1);
			
			panel.setLayout(new GridLayout(total,1,10,10));
			panel.setPreferredSize(new Dimension(600, total*590));
			
			sql = "select * from dogTBL";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String temp[] = new String[8];
				for(int i=0; i<temp.length ; i++)
					temp[i] = rs.getString(i+1);
				
				Dog dog = new Dog(temp, DogSelect);
				panel.add(dog);
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
