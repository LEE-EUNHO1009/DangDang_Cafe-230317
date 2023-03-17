package DogManager;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class DogUpdate extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DogUpdate dialog = new DogUpdate(7);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param typeAdd 
	 */
	public DogUpdate(int DogModify) {
		setTitle("댕댕이 정보 수정");
		setBounds(100, 100, 560, 616);
				
		Dog dog = new Dog(DogModify);
		
		getContentPane().add(dog, BorderLayout.CENTER);
	}

}
