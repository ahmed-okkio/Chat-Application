import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Member_1 extends JFrame {

	private JPanel contentPane;
	private JTextField Text_Field;
	private JTextField Message_Field;
	private JButton Main_Button;
	private JButton M2_Button;
	private JButton M3_Button;
	private JButton Exit_Button;
	private JLabel M1_Label;
	private JComboBox comboBox;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Member_1 frame = new Member_1();
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
	public Member_1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Text_Field = new JTextField();
		Text_Field.setBounds(10, 309, 538, 89);
		contentPane.add(Text_Field);
		Text_Field.setColumns(10);
		
		Message_Field = new JTextField();
		Message_Field.setBounds(10, 36, 538, 263);
		contentPane.add(Message_Field);
		Message_Field.setColumns(10);
		
		JButton Send_Button = new JButton("Send");
		Send_Button.setBounds(558, 309, 85, 89);
		Send_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = Text_Field.getText();
				if (s.equals("")) {
					return;
				}
				Message_Field.setText(s + "\n");
				Text_Field.setText("");
				
			}
			
		});
		
		contentPane.add(Send_Button);
		
		Main_Button = new JButton("Main Room");
		Main_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
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
		
		Main_Button.setBounds(558, 36, 85, 49);
		contentPane.add(Main_Button);
		
		M2_Button = new JButton("Member 2");
		M2_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		M2_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (M2_Button.isEnabled()) {
					Member_2 info= new Member_2();
					Member_2.main(null);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "No Button Pressed", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
						
				}
			}
			
		});
		
		M2_Button.setBounds(558, 113, 85, 49);
		contentPane.add(M2_Button);
		
		M3_Button = new JButton("Member 3");
		M3_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		M3_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (M3_Button.isEnabled()) {
					Member_3 info= new Member_3();
					Member_3.main(null);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "No Button Pressed", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
						
				}
			}
			
		});
		
		M3_Button.setBounds(558, 188, 85, 49);
		contentPane.add(M3_Button);
		
		Exit_Button = new JButton("Exit ");
		Exit_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		Exit_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (Exit_Button.isEnabled()) {
					System.exit(0);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "No Button Pressed", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
						
				}
			}
			
		});
		
		Exit_Button.setBounds(10, 10, 67, 19);
		contentPane.add(Exit_Button);
		
		M1_Label = new JLabel("MEMBER 1");
		M1_Label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		M1_Label.setBounds(256, 4, 85, 25);
		contentPane.add(M1_Label);
		
		//comboBox = new JComboBox();
		//comboBox.setModel(new DefaultComboBoxModel(new String[] {"Member 2", "Member 3"}));
		//comboBox.setBounds(463, 8, 85, 21);
		//contentPane.add(comboBox);
	}
	
	
	
}