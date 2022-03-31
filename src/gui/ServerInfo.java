package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Client.MemberState;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;


public class ServerInfo extends JFrame {

	private JPanel contentPane;



	public ServerInfo(MainRoom mainRoom, ArrayList<MemberState> state) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton Main_Button = new JButton("Main Room");
		Main_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		Main_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Main_Button.isEnabled()) {
					mainRoom.setVisible(true);
					setVisible(false);
					dispose();
					
				}
				else {
					JOptionPane.showMessageDialog(null, "No Button Pressed", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
						
				}
			}
			
		});
		Main_Button.setBounds(558, 36, 85, 49);
		contentPane.add(Main_Button);

		JLabel ClientInfoLabel = new JLabel("CLIENT INFORMATION");
		ClientInfoLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ClientInfoLabel.setBounds(220, 10, 500, 25);
		contentPane.add(ClientInfoLabel);
		JLabel IDHeaderLabel = new JLabel("ID");
		IDHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		IDHeaderLabel.setBounds(100, 50, 100, 25);
		IDHeaderLabel.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(IDHeaderLabel);
		JLabel IPHeaderLabel = new JLabel("IP:PORT");
		IPHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		IPHeaderLabel.setBounds(250, 50, 200, 25);
		IPHeaderLabel.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(IPHeaderLabel);

		int y = 100;
		for(MemberState member: state) {
			JLabel IDLabel = new JLabel(member.ID);
			IDLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			IDLabel.setBounds(100, y, 100, 25);
			IDLabel.setHorizontalAlignment(JLabel.CENTER);
			contentPane.add(IDLabel);

			JLabel IPLabel = new JLabel(member.IP);
			IPLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			IPLabel.setBounds(250, y, 200, 25);
			IPLabel.setHorizontalAlignment(JLabel.CENTER);
			contentPane.add(IPLabel);
			y+=50;
		}
	}

}
