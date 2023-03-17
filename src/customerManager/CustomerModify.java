package customerManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class CustomerModify extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerModify dialog = new CustomerModify(3);
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
	 * @param typeModify 
	 */
	public CustomerModify(int typeModify) {
		setTitle("일반회원 정보 변경창");
		setBounds(100, 100, 767, 288);
				
		Customer customer = new Customer(typeModify);
		getContentPane().add(customer, BorderLayout.CENTER);
	}

}
