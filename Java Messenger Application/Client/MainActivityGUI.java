package Client;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class MainActivityGUI implements ActionListener, ListSelectionListener {

	static JFrame frame;
	private JList<chatGUI> privateChatList, groupChatList;
	private JList<String> onlineList;
	private static DefaultListModel<chatGUI> privateChatDLM, groupChatDLM;
	private DefaultListModel<String> onlineUsersListDLM, offlineUsersListDLM;
	private JButton Change, newPrivateChatBtn, newGroupChatBtn, deleteChatBtn, cancelDeleteBtn;
	static ArrayList<String> onlineUsers,offlineUsers;
	private static String screenName;
	private JPanel panel, deleteChatPanel ;
	private JMenuItem mnLogout, mnChangeDisplay;

	public MainActivityGUI(ArrayList<String> onlineUsers, ArrayList<String>offlineUsers) {
		MainActivityGUI.onlineUsers=onlineUsers;
		MainActivityGUI.offlineUsers=offlineUsers;
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 873, 589);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnCustomize = new JMenu("Customize");
		mnCustomize.setBackground(Color.GRAY);
		menuBar.add(mnCustomize);
		
		JMenu mnChangeThem = new JMenu("Change theme");
		mnCustomize.add(mnChangeThem);
		
		JMenuItem mntmBlack = new JMenuItem("Dark");
		mnChangeThem.add(mntmBlack);
		
		JMenuItem mntmLight = new JMenuItem("Light");
		mnChangeThem.add(mntmLight);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		JPanel about = new JPanel();
		about.setLayout(new BorderLayout());
		JLabel aboutTitle = new JLabel("About");
		JTextArea aboutText = new JTextArea("Chat messenger to connect with others");
		about.add(aboutTitle,BorderLayout.NORTH);
		about.add(aboutText,BorderLayout.CENTER);
		JMenuItem mntmNewMenuItem = new JMenuItem("About");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, about, "About", JOptionPane.PLAIN_MESSAGE);
				
			}
		});
		mnHelp.add(mntmNewMenuItem);

		JMenu mnAccount = new JMenu("Account");
		menuBar.add(mnAccount);

		mnChangeDisplay = new JMenuItem("Change Display Name");
		mnChangeDisplay.addActionListener(this);
		mnAccount.add(mnChangeDisplay);

		mnLogout = new JMenuItem("Logout");
		mnAccount.add(mnLogout);
		mnLogout.addActionListener(this);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setEnabled(false);
		splitPane_1.setResizeWeight(0.5);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setEnabled(false);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setLeftComponent(splitPane_2);

		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setEnabled(false);
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setRightComponent(splitPane_4);

		JLabel label = new JLabel("< Online Users >");
		splitPane_4.setLeftComponent(label);

		onlineUsersListDLM= new DefaultListModel<String>();
		onlineList = new JList<String>(onlineUsersListDLM);
		JScrollPane scrollPane_1 = new JScrollPane(onlineList);
		onlineList.addListSelectionListener(this);
		onlineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		offlineUsersListDLM= new DefaultListModel<String>();
		JList<String> offlineList = new JList<String>(offlineUsersListDLM);	
		JScrollPane scrollPane_2 = new JScrollPane(offlineList);

		fillUserLists();
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(10, 30));
		splitPane_2.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JLabel lblUsername = new JLabel("Screen name: "+screenName);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel_3.add(lblUsername, BorderLayout.CENTER);

		Change = new JButton("Change");
		panel_3.add(Change, BorderLayout.EAST);
		Change.addActionListener(this);

		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_5.setEnabled(false);
		splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_5);

		splitPane_5.setRightComponent(scrollPane_2);
		splitPane_4.setRightComponent(scrollPane_1);
		JLabel label_1 = new JLabel("<Off-line Users >");
		splitPane_5.setLeftComponent(label_1);

		splitPane_1.setDividerLocation(275);

		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setEnabled(false);
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_3);

		panel = new JPanel();
		splitPane_3.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		privateChatDLM = new DefaultListModel<chatGUI>();

		JPanel panel_5 = new JPanel();
		splitPane_3.setLeftComponent(panel_5);

		newPrivateChatBtn = new JButton("+ New Private Chat");
		newPrivateChatBtn.addActionListener(this);
		panel_5.add(newPrivateChatBtn);

		newGroupChatBtn = new JButton("+ New Group Chat");
		panel_5.add(newGroupChatBtn);

		deleteChatBtn = new JButton("- Delete Chat");
		panel_5.add(deleteChatBtn);
		deleteChatBtn.addActionListener(this);
		newGroupChatBtn.addActionListener(this);
		splitPane.setDividerLocation(500);
		deleteChatPanel =new JPanel();
		deleteChatPanel.setLayout(new BorderLayout());
		cancelDeleteBtn = new JButton("Cancel");
		cancelDeleteBtn.addActionListener(this);
		deleteChatPanel.add(new JLabel("Please select chat to delete:"),BorderLayout.CENTER);
		deleteChatPanel.add(cancelDeleteBtn,BorderLayout.EAST);
		panel.add(deleteChatPanel,BorderLayout.NORTH);
		deleteChatPanel.setVisible(false);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);
		privateChatList = new JList<chatGUI>(privateChatDLM);
		tabbedPane.addTab("Private Chats", null, privateChatList, null);
		privateChatList.setFixedCellHeight(30);
		privateChatList.addListSelectionListener(this);
		privateChatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		groupChatDLM = new DefaultListModel<chatGUI>();
		groupChatList = new JList<chatGUI>(groupChatDLM);
		tabbedPane.addTab("Group Chats", null, groupChatList, null);
		groupChatList.setFixedCellHeight(30);
		groupChatList.addListSelectionListener(this);
		groupChatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		frame.setLocationRelativeTo(null);
	}

	public static void addToPrivateChatList(chatGUI c) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				privateChatDLM.addElement(c);
			}
		});
	}

	public static void addToGroupChatList(chatGUI c) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				groupChatDLM.addElement(c);
			}
		});
	}

	public static String getScreenName() {
		return screenName;
	}

	public void fillUserLists(){
		for(String q:onlineUsers) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					onlineUsersListDLM.addElement(q);
				}
			});
		}
		for(String q:offlineUsers) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					offlineUsersListDLM.addElement(q);
				}
			});
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==newGroupChatBtn) {
			NewChatGUI newChat = new NewChatGUI("group");
		}
		if(e.getSource()==newPrivateChatBtn) {
			NewChatGUI newChat = new NewChatGUI("private");
		}
		if(e.getSource()==deleteChatBtn) {
			deleteChatPanel.setVisible(true);
		}
		if(e.getSource()==cancelDeleteBtn) {
			deleteChatPanel.setVisible(false);
		}
		if(e.getSource()==mnLogout) {
			int res=JOptionPane.showConfirmDialog(frame, "Are You Sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
			if(res==JOptionPane.YES_OPTION) {
				Communication.closeConnection();
				frame.dispose();
				LoginGUI a = new LoginGUI();
			}
		}
		if(e.getSource()==mnChangeDisplay||e.getSource()==Change) {
			String newScreenName=JOptionPane.showInputDialog(frame,"Enter New ScreenName:");
			while(newScreenName!=null&&newScreenName.isEmpty()) {
				newScreenName=JOptionPane.showInputDialog(frame,"Error! Incomplete Fields\nPlease enter new Screen Name:");
			}		
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		if(onlineList.getSelectedIndex()!=-1) {
			boolean exists=false;
			for(chatGUI chat :chats.listChats) {
				if(chat.getChatName().equals(onlineList.getSelectedValue())){
					chat.resetMessageCount();
					chat.setVisible(true);
					exists=true;
				}
			}
			if(!exists) {
				chatGUI chat =new chatGUI(NewChatGUI.createChatRoomName(onlineList.getSelectedValue()));
				chats.addchat(chat);
			}
			onlineList.clearSelection();
		}
		if(privateChatList.getSelectedIndex()!=-1) {
			if(deleteChatPanel.isVisible()) {
				int res = JOptionPane.showConfirmDialog(null, "Are You Sure you want to delete "+privateChatList.getSelectedValue()+" ?", "Error", JOptionPane.YES_NO_OPTION);
				if(res==JOptionPane.YES_OPTION) {
					chats.removechat(privateChatDLM.get(privateChatList.getSelectedIndex()));
					privateChatDLM.remove(privateChatList.getSelectedIndex());
					JOptionPane.showMessageDialog(null, "Chat deleted!", "", JOptionPane.ERROR_MESSAGE);
				}
				deleteChatPanel.setVisible(false);
			}
			else {
				chatGUI chat=chats.getChat(privateChatList.getSelectedValue().getFullChatName());
				chat.resetMessageCount();
				chat.setVisible(true);
			}
			privateChatList.clearSelection();
		}

		if(groupChatList.getSelectedIndex()!=-1) {
			if(deleteChatPanel.isVisible()) {
				int res = JOptionPane.showConfirmDialog(null, "Are You Sure you want to delete "+groupChatList.getSelectedValue()+" ?", "Error", JOptionPane.YES_NO_OPTION);
				if(res==JOptionPane.YES_OPTION) {
					chats.removechat(groupChatDLM.get(groupChatList.getSelectedIndex()));
					groupChatDLM.remove(groupChatList.getSelectedIndex());
					JOptionPane.showMessageDialog(null, "Chat deleted!", "", JOptionPane.ERROR_MESSAGE);
				}
				deleteChatPanel.setVisible(false);
			}
			else {
				chatGUI chat=chats.getChat(groupChatList.getSelectedValue().getFullChatName());
				chat.resetMessageCount();
				chat.setVisible(true);
			}
			groupChatList.clearSelection();
		}
	}
}