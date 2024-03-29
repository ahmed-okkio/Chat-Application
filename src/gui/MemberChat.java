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
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;

public class MemberChat extends JFrame {

	private JPanel contentPane;
	private String messageHistory = "";
	private JTextArea sendMessageField;
	public JTextArea messageField;
	private JButton Main_Button;
	private JButton M2_Button;
	private JButton M3_Button;
	private JButton Exit_Button;
	private JLabel M1_Label;
	private JComboBox comboBox;
	public String recipientID;
	private MainRoom mainRoom = null;
	
	


	public void updateChatLabel() {
		M1_Label.setText(recipientID);
		messageField.setText("Welcome to the private chat with: "+ recipientID);
	}
	public MemberChat(MainRoom mainRoom) {
		this.mainRoom = mainRoom;
		setTitle(mainRoom.client.username);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 664, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
                JTextArea txtArea = (JTextArea) e.getSource();

				//SEND
				if(mainRoom != null) {
					mainRoom.client.sendPrivateMessageUI(recipientID,txtArea.getText());
				}
                txtArea.setText("");
            }
        });

		contentPane.add(scroll2);
		
		JButton Send_Button = new JButton("Send");
		Send_Button.setBounds(558, 309, 85, 89);
		contentPane.add(Send_Button);
		
		Main_Button = new JButton("Main Room");
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
		
		M1_Label = new JLabel("MEMBER");
		M1_Label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		M1_Label.setBounds(256, 4, 85, 25);
		contentPane.add(M1_Label);
		
	}
	
	
	
}