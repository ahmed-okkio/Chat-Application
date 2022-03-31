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
import javax.swing.JDialog;
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
	private JTextField ip_Address;
	private JTextField Port_Field;
	JDialog dialogBox;



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
	private void InitializeChat(Client NewClient){
		Window_2 info= new Window_2(NewClient);
		info.setVisible(true);
		setVisible(false);
		dispose();	
	}


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
		
		JLabel IP_Label = new JLabel("IP Address:");
		IP_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		IP_Label.setBounds(97, 166, 118, 35);
		contentPane.add(IP_Label);
		
		JLabel Port_Label = new JLabel("Port:");
		Port_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Port_Label.setBounds(155, 232, 84, 35);
		contentPane.add(Port_Label);
		
		JButton Connect = new JButton("CONNECT");
		Connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String UniqueID = ID_Field.getText();
				String Port = Port_Field.getText();
				String IP = ip_Address.getText();
				
				try {
					if (UniqueID.isEmpty()){
						throw new NumberFormatException("ID Can't be empty");
					}
					try {
						int PORT = Integer.parseInt(Port);
						try {
							Socket socket = new Socket(IP, PORT);
							Client NewClient = new Client(socket, UniqueID);
							if(NewClient.role == -1) {
								JOptionPane.showMessageDialog(null, "ID already exists on server", "Invalid ID", JOptionPane.ERROR_MESSAGE);
							} else {
								InitializeChat(NewClient);
							}

						} catch (IOException error) {
							JOptionPane.showMessageDialog(null, "Invalid IP Address / Server Unavailable", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);		
							error.printStackTrace();
							
						}
					} catch (NumberFormatException  error) {
						JOptionPane.showMessageDialog(null, "Invalid PORT Details", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException  error) {
					JOptionPane.showMessageDialog(null, "Invalid ID Details", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
					
				}

			}
			
		});
				
		
				
		Connect.setFont(new Font("Tahoma", Font.BOLD, 10));
		Connect.setBounds(112, 315, 177, 40);
		contentPane.add(Connect);
		
		ID_Field = new JTextField();
		ID_Field.setBounds(227, 102, 233, 33);
		contentPane.add(ID_Field);
		ID_Field.setColumns(10);
		
		ip_Address = new JTextField();
		ip_Address.setBounds(227, 171, 233, 35);
		contentPane.add(ip_Address);
		ip_Address.setColumns(10);
		
		Port_Field = new JTextField();
		Port_Field.setBounds(227, 236, 55, 33);
		contentPane.add(Port_Field);
		Port_Field.setColumns(10);
		
		
		JButton Reset = new JButton("RESET");
		Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ID_Field.setText(null);
				ip_Address.setText(null);
				Port_Field.setText(null);
				
			}
		});
		Reset.setFont(new Font("Tahoma", Font.BOLD, 10));
		Reset.setBounds(363, 315, 177, 40);
		contentPane.add(Reset);
	}
}
