package customerManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class CustomerAdd extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerAdd dialog = new CustomerAdd(1);
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
	 * @param typeAdd 
	 */
	public CustomerAdd(int typeAdd) {
		setTitle("일반회원 추가");
		setBounds(100, 100, 769, 288);
				
		Customer customer = new Customer(typeAdd);
		getContentPane().add(customer, BorderLayout.CENTER);
	}

}
