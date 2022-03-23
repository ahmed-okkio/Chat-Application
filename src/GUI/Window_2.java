import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window_2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window_2 frame = new Window_2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window_2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Label1 = new JLabel("You are the coordinator of the group");
		Label1.setBounds(124, 10, 401, 173);
		Label1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(Label1);
		
		JButton Main_Button = new JButton("Main Room");
		Main_Button.setBounds(236, 180, 170, 45);
		Main_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (Main_Button.isEnabled()) {
					Main_Room info= new Main_Room();
					Main_Room.main(null);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "No Button Pressed", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
						
				}
			}
			
		});
		
		
		
		
		Main_Button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(Main_Button);
		
		
		
		JButton Server_Button = new JButton("Server Info");
		Server_Button.setBounds(236, 265, 170, 45);

		Server_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (Server_Button.isEnabled()) {
					Server_Info info= new Server_Info();
					Server_Info.main(null);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "No Button Pressed", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
						
				}
			}
			
		});

		Server_Button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(Server_Button);
	}
}
