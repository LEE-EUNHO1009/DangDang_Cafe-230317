import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.thehowtotutorial.splashscreen.JSplash;

public class LoadingBooks {
	
	public static void main(String[] args) throws InterruptedException {
		JSplash splash = new JSplash(LoadingBooks.class.getResource("/images/books.png"),
		true, true, false, "ICI corp.", null, Color.RED, Color.BLUE);
		splash.splashOn();
		
		String columnNames[] = {"강아지이름", "보호자명", "견종", "일반고객이름", "전화번호"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0);
		
		//================================

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select count(*) from dogTBL";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int total=0;
			if(rs.next())
				total = rs.getInt(1);
			
			sql = "select * from dogTBL";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			int count=0;
			while(rs.next()) {
				splash.setProgress (++count*100/total, "도서 레코드 로딩중...");
				Thread.sleep(10);
				
				//============================================
				Vector <String> vec = new Vector<>();
				for(int i=1;i<=8;i++) {
					if(i!=6 && i!=8)
						vec.add(rs.getString(i));
				}
				dtm.addRow(vec);				
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
		
		//================================
		splash.splashOff();
		
		CafeMain cafeMain = new CafeMain(dtm);
		cafeMain.setModal(true);
		cafeMain.setVisible(true);		
	}
}
