package Client;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.*;

public class chatGUI extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private String chatName,chatType;
	private String nmessages="";
	private JLabel chatNameLabel;
	private JButton sendBtn = new JButton("Send");
	private JButton attachFileBtn;
	JTextArea listOfUsers,messages;
	private JTextArea text = new JTextArea();
	private ArrayList<String> users;
	private int numberOfNewMessages=1;
	JButton btnEmoji;
	 
	//private chat
	public chatGUI(String user) {
		setTitle("Private Chat");
		chatType="private";
		users= new ArrayList<String>();
		this.users.add(cutName(user));
		this.chatName=user;
		initialize();
	}
	//groupChat
	/**
	 * @wbp.parser.constructor
	 */
	public chatGUI(String chatName, ArrayList<String> users){
		setTitle("Group Chat");
		chatType="public";
		this.users=users;
		this.chatName=chatName;
		initialize();
	}

	public void initialize() {
			setBounds(100, 100, 596, 451);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			contentPane.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_2 = new JPanel();
			panel_2.setPreferredSize(new Dimension(10, 40));
			panel_2.setMinimumSize(new Dimension(23, 29));
			contentPane.add(panel_2, BorderLayout.NORTH);
			
			chatNameLabel = new JLabel(cutName(chatName));
			chatNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel_2.add(chatNameLabel);
			
			JSplitPane splitPane = new JSplitPane();
			splitPane.setEnabled(false);
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			contentPane.add(splitPane);
			
			JSplitPane splitPane_2 = new JSplitPane();
			splitPane_2.setEnabled(false);
			splitPane_2.setPreferredSize(new Dimension(179, 37));
			splitPane.setLeftComponent(splitPane_2);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			splitPane_2.setLeftComponent(scrollPane_1);
			
			messages = new JTextArea();
			messages.setMargin(new Insets(10, 5, 10, 5));
			messages.setEditable(false);
			scrollPane_1.setViewportView(messages);
			
			JScrollPane scrollPane_2 = new JScrollPane();
			splitPane_2.setRightComponent(scrollPane_2);
			
			listOfUsers = new JTextArea();
			listOfUsers.setMargin(new Insets(10, 10, 10, 10));
			listOfUsers.setEditable(false);
			scrollPane_2.setViewportView(listOfUsers);
			for(String a:users) {
				listOfUsers.append('\n' + a);
			}
			
			splitPane_2.setDividerLocation(375);
			
			JPanel panel = new JPanel();
			splitPane.setRightComponent(panel);
			panel.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_3 = new JPanel();
			panel.add(panel_3, BorderLayout.NORTH);
			panel_3.setLayout(new GridLayout(0, 5, 0, 0));
			
			attachFileBtn = new JButton("Send File");
			attachFileBtn.addActionListener(this);
	
			panel_3.add(attachFileBtn);
			
			btnEmoji = new JButton("emoji");
			btnEmoji.addActionListener(this);
			panel_3.add(btnEmoji);
			
			JLayeredPane layeredPane = new JLayeredPane();
			panel_3.add(layeredPane);
			
			JSplitPane splitPane_1 = new JSplitPane();
			splitPane_1.setEnabled(false);
			panel.add(splitPane_1, BorderLayout.CENTER);
			
			JPanel panel_1 = new JPanel();
			splitPane_1.setRightComponent(panel_1);
			text.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER) {
						if(!text.getText().trim().isEmpty()){
							chats.updatechat(chatName, MainActivityGUI.getScreenName(), text.getText(),ComponentOrientation.RIGHT_TO_LEFT);
							text.setText("");
							Communication.send(new Message("MESSAGE",MainActivityGUI.getScreenName(),users,text.getText(),chatName));
						}	
					}
				}
			});
			sendBtn.addActionListener(this);
			sendBtn.setBounds(10, 21, 87, 38);
			panel_1.setLayout(null);
			panel_1.add(sendBtn);
			
			JScrollPane scrollPane = new JScrollPane();
			splitPane_1.setLeftComponent(scrollPane);
			text.setWrapStyleWord(true);
			text.setLineWrap(true);
		
			text.setMargin(new Insets(10, 10, 10, 10));
			scrollPane.setViewportView(text);
				
			splitPane_1.setDividerLocation(450);
			splitPane.setDividerLocation(250);
			setContentPane(contentPane);
			setDefaultLookAndFeelDecorated(true);
			setVisible(true);
			setLocationRelativeTo(null);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==sendBtn) {
			if(!text.getText().trim().isEmpty()){
				chats.updatechat(chatName, MainActivityGUI.getScreenName(), text.getText(),ComponentOrientation.RIGHT_TO_LEFT);
				Communication.send(new Message("MESSAGE",MainActivityGUI.getScreenName(),users,text.getText(),chatName));
			}	
			text.setText("");
		}
		if(e.getSource()==attachFileBtn) {
			files f=new files(getFullChatName(),users);
		}
		if(e.getSource()==btnEmoji) {
	       
		}
	}
	
	public String getChatName() {
		return cutName(chatName);
	}

	public void setMessages(String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				messages.append("\n\r"+message);
			}
		});
	}
	
	public void setMessages(Icon message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JLabel a=new JLabel(message);
				messages.add(a);
				a.setVisible(true);
				}
		});
	}
	
	public void setMessagesOrientation(ComponentOrientation alignment) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				messages.setComponentOrientation(alignment);
			}
		});
	}
	
	public String getFullChatName() {
		return chatName;
	}

	public static String cutName(String fc){
		int index = fc.indexOf("_");
		String date = fc.substring(index);
		String chatName = fc.substring(0, index);
		return chatName;
	}
	
	public String getChatType(){
		return this.chatType;
	}
	
	public void newMessage() {
		nmessages="[NEW MESSAGE]" + numberOfNewMessages++;
	}
	
	public void resetMessageCount() {
		nmessages="";
		numberOfNewMessages=1;
	}
	
	public String toString() {
		return getChatName() + nmessages;
	}
}
