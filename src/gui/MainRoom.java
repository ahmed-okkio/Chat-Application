package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;

import backend.Client;
import backend.Client.MemberState;

public class MainRoom extends JFrame {

	private String messageHistory = "";
	private String privateMessageHistory1 = "";
	private String privateMessageHistory2= "";
	private String privateMessageHistory3 = "";
	private JPanel contentPane;
	private JTextArea messageField;
	private JTextArea sendMessageField;
	private JButton Server_Button;
	private JButton M1_Button;
	MemberChat M1_Chat;
	private JButton M2_Button;
	MemberChat M2_Chat;
	private JButton M3_Button;
	MemberChat M3_Chat;
	private boolean isCoordinator = false;
	public static ArrayList<MemberChat> memberChats = new ArrayList<>();
	private ArrayList<JButton> memberButtons = new ArrayList<>();

	public Client client;
	
	public static class Rooms {
		public static final String MAIN_ROOM = "MAINROOM";
		public static final String MEMBER_ROOM_1 = "MemberRoom_1";
		public static final String MEMBER_ROOM_2 = "MemberRoom_2";
		public static final String MEMBER_ROOM_3 = "MemberRoom_3";
	}
	private MainRoom reference;

	public void deliverMessages(String location, String message) {
		if (location.equals("MAINROOM")){
			messageHistory = messageHistory + message+ "\n";
			messageField.setText(messageHistory);
		}
		if (location.equals(M1_Chat.recipientID)){
			privateMessageHistory1 = privateMessageHistory1 + message + "\n";
			M1_Chat.messageField.setText(privateMessageHistory1);
		}
		if (location.equals(M2_Chat.recipientID)){
			privateMessageHistory2 = privateMessageHistory2 + message + "\n";
			M2_Chat.messageField.setText(privateMessageHistory2);
		}
		if (location.equals(M3_Chat.recipientID)){
			privateMessageHistory3 = privateMessageHistory3 + message + "\n";
			M3_Chat.messageField.setText(privateMessageHistory3);
		}

	}

	public void updateCoordinator(boolean isCoordinator) {
		if( isCoordinator) {
			contentPane.add(Server_Button);
			contentPane.repaint();
		} else {
			contentPane.remove(Server_Button);
			contentPane.repaint();
		}
	}

	public void updateMembersList() {
		int y = 90;
		for (JButton button : memberButtons) {
			contentPane.remove(button);
		}
		for(int i = 0; i < client.members.size(); i++) {
			if(client.members.get(i).ID.equals(client.username)){continue;}
			
			memberChats.get(i).recipientID = client.members.get(i).ID;
			memberChats.get(i).updateChatLabel();

			JButton button = memberButtons.get(i);
			button.setText(client.members.get(i).ID);
			button.putClientProperty("index", i);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (M1_Button.isEnabled()) {
						int memberIndex = (Integer)((JButton)e.getSource()).getClientProperty( "index" );
						
						memberChats.get(memberIndex).setVisible(true);
						setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "No Button Pressed", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);

					}
				}
				
			});
			button.setBounds(558,y, 85, 49);
			contentPane.add(button);
			y += 90;
		}

		contentPane.repaint();
	}
	public MainRoom(Client client) {
		this.client = client;
		if(client.role == 1) {
			isCoordinator = true;
		}
		setTitle(client.username);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		M1_Button = new JButton("Member 1");
		M1_Chat = new MemberChat(this);
		M1_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		memberButtons.add(M1_Button);
		memberChats.add(M1_Chat);

		M2_Button = new JButton("Member 2");
		M2_Chat = new MemberChat(this);
		M2_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		memberButtons.add(M2_Button);
		memberChats.add(M2_Chat);

		M3_Button = new JButton("Member 3");
		M3_Chat = new MemberChat(this);
		M3_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		memberButtons.add(M3_Button);
		memberChats.add(M3_Chat);

		updateMembersList();

		messageField = new JTextArea();
		
		messageField.setEditable(false);
        messageField.setLineWrap(true);
		messageField.setBounds(10, 33, 538, 266);
		messageField.setColumns(10);
		
		DefaultCaret caret = (DefaultCaret)messageField.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane scroll = new JScrollPane (messageField);
		scroll.setBounds(10, 33, 538, 266);
		contentPane.add(scroll);
		
		sendMessageField = new JTextArea();
		
		sendMessageField.setBounds(10, 309, 538, 89);
		sendMessageField.setColumns(10);
		sendMessageField.setLineWrap(true);
        sendMessageField.setWrapStyleWord(true);

		DefaultCaret caret2 = (DefaultCaret)sendMessageField.getCaret();
		caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane scroll2 = new JScrollPane (sendMessageField);
		scroll2.setBounds(10, 309, 538, 89);
		
		int condition = JComponent.WHEN_FOCUSED;
        InputMap inputMap = sendMessageField.getInputMap(condition);
        ActionMap actionMap = sendMessageField.getActionMap();

        KeyStroke enterKey = KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0);

        inputMap.put(enterKey, enterKey.toString());
        actionMap.put(enterKey.toString(), new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

				//SEND
				client.sendMessageUI(sendMessageField.getText());
                sendMessageField.setText("");
            }
        });

		contentPane.add(scroll2);
		
		JButton Send_Button = new JButton("Send");
		Send_Button.setBounds(558, 309, 85, 89);
		Send_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//SEND
				client.sendMessageUI(sendMessageField.getText());
                sendMessageField.setText("");
			}
		});
		contentPane.add(Send_Button);
		
		Server_Button = new JButton("Server Info");
		Server_Button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		Server_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (Server_Button.isEnabled()) {
					ServerInfo info= new ServerInfo(reference,client.members);
					info.setVisible(true);
					setVisible(false);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "No Button Pressed", "FaiLed Attempt", JOptionPane.ERROR_MESSAGE);
						
				}
			}
		});

		Server_Button.setBounds(558, 22, 85, 49);
		if(isCoordinator) {
			contentPane.add(Server_Button);
		}
		

		
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
		

		client.mainRoom = this;
		this.reference = this;
		deliverMessages("MAINROOM","Welcome to the main room.");
	}
}
