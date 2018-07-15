package Client;

import java.awt.ComponentOrientation;
import java.util.ArrayList;

public class chats {
	static ArrayList<chatGUI>listChats= new ArrayList<>();

	public static synchronized void addchat(chatGUI c) {
		listChats.add(c);
		if(c.getChatType().equals("private"))
			MainActivityGUI.addToPrivateChatList(c);
		else
			MainActivityGUI.addToGroupChatList(c);
	}

	public static synchronized void removechat(chatGUI chatName) {
		listChats.remove(chatName);
	}

	public static synchronized void updatechat(String chatName, String srcUser, String message,ComponentOrientation alignment) {
		chatGUI chat = getChat(chatName);
		chat.setMessagesOrientation(alignment);
	
		if(alignment.isLeftToRight()) {
			chat.setMessages(srcUser+ " ) : " + message);
		}
		else {
			chat.setMessages(message+": ( "+srcUser); 
		}
	}
	
	public static boolean chatExists(String chatName) {
		for(chatGUI chat:chats.listChats) {
			if(chat.getFullChatName().equals(chatName)) {
				return true;
			}
		}
		return false;
	}

	public static chatGUI getChat(String chatName) {
		for(chatGUI chat:chats.listChats) {
			if(chat.getFullChatName().equals(chatName)) {
				return chat;
			}
		}
		return null;
	}
}