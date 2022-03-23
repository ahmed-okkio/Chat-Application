package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.Socket;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.awt.event.ActionEvent;
import backend.Client;


public class Window_1 extends JFrame {

	private JPanel contentPane;
	private JTextField ID_Field;
	private JTextField Port_Field;
	private JTextField IP_1;
	private JTextField IP_2;
	private JTextField IP_3;
	private JTextField IP_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window_1 frame = new Window_1();
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
	public Window_1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Title = new JLabel("CONNECT TO SERVER");
		Title.setFont(new Font("Tahoma", Font.PLAIN, 24));
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setBounds(212, -14, 267, 110);
		contentPane.add(Title);
		
		JLabel ID_Label = new JLabel("ID: ");
		ID_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ID_Label.setBounds(165, 105, 84, 35);
		contentPane.add(ID_Label);
		
		JLabel Port_Label = new JLabel("Port:");
		Port_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Port_Label.setBounds(155, 166, 84, 35);
		contentPane.add(Port_Label);
		
		JLabel IP_Label = new JLabel("IP Address:");
		IP_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		IP_Label.setBounds(97, 232, 118, 35);
		contentPane.add(IP_Label);
		
		JButton Connect = new JButton("CONNECT");
		Connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String UniqueID = ID_Field.getText();
				String Port = Port_Field.getText();
				String IP = IP_1.getText();
				String IP2 = IP_2.getText();
				String IP3 = IP_3.getText();
				String IP4 = IP_4.getText();
			
				
				
//				if (UniqueID.contains("1")) {
//					//JOptionPane.showMessageDialog(null,  "Redirecting Now", "Success", JOptionPane.INFORMATION_MESSAGE);
//					
//					//Window_2 info= new Window_2();
//					//Window_2.main(null);
//					
//				}
//				//else {
//				//	JOptionPane.showMessageDialog(null, "Invalid ID Details", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
//					
//					
//					
//					
//				}
//				
//				//if (Port.contains("20")) {
//					//JOptionPane.showMessageDialog(null,  "Redirecting Now", "Success", JOptionPane.INFORMATION_MESSAGE);
//					
//					//Window_2 info= new Window_2();
//					//Window_2.main(null);
//					
//				}
//				else {
//					JOptionPane.showMessageDialog(null, "Invalid Port Details", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
//					
//					
//					
//					
//				}
//				if (IP.contains("1") && IP2.contains("2") && IP3.contains("3") && IP4.contains("4")  ) {
//					JOptionPane.showMessageDialog(null,  "Redirecting Now", "Success", JOptionPane.INFORMATION_MESSAGE);
//					
//					//Window_2 info= new Window_2();
//					//Window_2.main(null);
//					
//				}
//				else {
//					JOptionPane.showMessageDialog(null, "Invalid IP Details", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
//					
//					
//					
//					
//				}
				
				try {
					
					
		            
					Socket socket = new Socket("81.110.249.60", Integer.parseInt(Port));
					Client NewClient = new Client(socket, UniqueID);
					Window_2 info= new Window_2(NewClient);
					info.setVisible(true);
					System.out.println("Hello");
					
		        } catch (IOException error) {
		        	
		        	error.printStackTrace();
		        	
		        }
				
				
//				if (IP.contains("1") && IP2.contains("2") && IP3.contains("3") && IP4.contains("4") && UniqueID.contains("1") && Port.contains("20")  ) {
//					//JOptionPane.showMessageDialog(null,  "Redirecting Now", "Success", JOptionPane.INFORMATION_MESSAGE);
//					
//					
//					
//					
//				
//				}
//				else {
//					JOptionPane.showMessageDialog(null, "Try Again", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
//					
//					
//					
//					
//				}
//				
				
			}
			
			
		});
				
		
				
		Connect.setFont(new Font("Tahoma", Font.BOLD, 10));
		Connect.setBounds(112, 315, 177, 40);
		contentPane.add(Connect);
		
		ID_Field = new JTextField();
		ID_Field.setBounds(227, 102, 233, 33);
		contentPane.add(ID_Field);
		ID_Field.setColumns(10);
		
		Port_Field = new JTextField();
		Port_Field.setBounds(227, 171, 233, 33);
		contentPane.add(Port_Field);
		Port_Field.setColumns(10);
		
		IP_1 = new JTextField();
		IP_1.setBounds(227, 236, 52, 35);
		contentPane.add(IP_1);
		IP_1.setColumns(10);
		
		IP_2 = new JTextField();
		IP_2.setColumns(10);
		IP_2.setBounds(289, 236, 52, 35);
		contentPane.add(IP_2);
		
		IP_3 = new JTextField();
		IP_3.setColumns(10);
		IP_3.setBounds(351, 236, 52, 35);
		contentPane.add(IP_3);
		
		IP_4 = new JTextField();
		IP_4.setColumns(10);
		IP_4.setBounds(413, 236, 52, 35);
		contentPane.add(IP_4);
		
		JButton Reset = new JButton("RESET");
		Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ID_Field.setText(null);
				Port_Field.setText(null);
				IP_1.setText(null);
				IP_2.setText(null);
				IP_3.setText(null);
				IP_4.setText(null);
				
				
			}
		});
		Reset.setFont(new Font("Tahoma", Font.BOLD, 10));
		Reset.setBounds(363, 315, 177, 40);
		contentPane.add(Reset);
	}
}
