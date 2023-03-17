package customerManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class CustomerRemove extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerRemove dialog = new CustomerRemove(2);
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
	 * @param typeRemove 
	 */
	public CustomerRemove(int typeRemove) {
		setTitle("일반회원 탈퇴창");
		setBounds(100, 100, 761, 288);
				
		Customer customer = new Customer(typeRemove);
		getContentPane().add(customer, BorderLayout.CENTER);
	}

}
