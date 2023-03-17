package DogManager;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class DogRemove extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DogRemove dialog = new DogRemove(6);
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
	public DogRemove(int DogDelete) {
		setTitle("댕댕이 삭제");
		setBounds(100, 100, 560, 616);
				
		Dog dog = new Dog(DogDelete);
		
		getContentPane().add(dog, BorderLayout.CENTER);
	}

}
