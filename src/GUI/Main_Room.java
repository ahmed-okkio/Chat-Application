package gui;

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

public class Main_Room extends JFrame {

	private JPanel contentPane;
	private JTextField Text_Field;
	private JTextField Message_Field;
	private JButton Server_Button;
	private JButton M1_Button;
	private JButton M2_Button;
	private JButton M3_Button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Room frame = new Main_Room();
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
	public Main_Room() {
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
		Message_Field.setBounds(10, 33, 538, 266);
		contentPane.add(Message_Field);
		Message_Field.setColumns(10);
		
		JButton Send_Button = new JButton("Send");
		Send_Button.setBounds(558, 309, 85, 89);
		contentPane.add(Send_Button);
		
		Server_Button = new JButton("Server Info");
		Server_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
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
		Server_Button.setBounds(558, 22, 85, 49);
		contentPane.add(Server_Button);
		
		M1_Button = new JButton("Member 1");
		M1_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		M1_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (M1_Button.isEnabled()) {
					Member_1 info= new Member_1();
					Member_1.main(null);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "No Button Pressed", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
						
				}
			}
			
		});
		
		M1_Button.setBounds(558, 91, 85, 49);
		contentPane.add(M1_Button);
		
		
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
		
		M2_Button.setBounds(558, 161, 85, 49);
		contentPane.add(M2_Button);
		
		
		M3_Button = new JButton("Member 3");
		M3_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		M3_Button.setBounds(558, 233, 85, 49);
		contentPane.add(M3_Button);
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
		
		JButton Exit_Button = new JButton("Exit ");
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
		
		JLabel lblNewLabel = new JLabel("MAIN ROOM");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(253, 4, 85, 25);
		contentPane.add(lblNewLabel);
		
		//JComboBox comboBox = new JComboBox();
		//comboBox.setModel(new DefaultComboBoxModel(new String[] {"Member 1", "Member 2", "Member 3"}));
		//comboBox.setToolTipText("");
		//comboBox.setBounds(455, 8, 93, 21);
		//contentPane.add(comboBox);
	}
}
