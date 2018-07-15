package Client;

import java.awt.ComponentOrientation;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Communication extends Thread{
	private static Socket s;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	private Message m;
	public Communication() {
		try {
			Properties p = new Properties();
			FileInputStream fis = new FileInputStream("properties.prop");
			p.load(fis);
			fis.close();
			s = new Socket(p.getProperty("SERVERIPADDRESS"),Integer.parseInt(p.getProperty("PORT")));
			ois=new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			while(!s.isClosed()) {
				Thread t = new Communication();
				m= (Message)ois.readObject();
				t.start();
			}
		}  catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void send(Message m) {
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		readMessage(m);
	}

	public static void readMessage(Message m) {
		switch(m.getType()) {
		case "LOGIN" :
			if(m.getResponse()) {
				LoginGUI.frmLogin.setVisible(false);
				JOptionPane.showMessageDialog(null, "Logging in......", "", JOptionPane.INFORMATION_MESSAGE);
				Communication.send(new Message("USERS"));
			}
			if(!m.getResponse()) {
				JOptionPane.showMessageDialog(null, m.getReason(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "REGISTER" :
			if(m.getResponse()) {
				JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
				LoginGUI.frmLogin.setVisible(true);
			}
			if(!m.getResponse()) {
				JOptionPane.showMessageDialog(null, m.getReason(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;		
		case "MESSAGE" :
			if(chats.chatExists(m.getChatName())) {
				chats.updatechat(m.getChatName(),m.getsrcUser(),m.getMessageBody(),ComponentOrientation.LEFT_TO_RIGHT);
			}
			else {
				chatGUI q = new chatGUI(m.getChatName(), m.getdestUsers());
				chats.addchat(q);
				chats.updatechat(m.getChatName(),m.getsrcUser(),m.getMessageBody(),ComponentOrientation.LEFT_TO_RIGHT);	
			}
			chats.getChat(m.getChatName()).newMessage();
			break;
		case "FILE" :
			byte[]fileBytes=m.getFileBytes();
			try {
				File f = new File(m.getFileName());
				BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(f));
				for(byte a:fileBytes) {
					bos.write(a);
				}
				bos.flush();
				bos.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "USERS" :
			LoginGUI.frmLogin.dispose();
			MainActivityGUI window = new MainActivityGUI(m.getOnlineUser(),m.getOfflineUser());
		}
	}	

	public static void closeConnection(){
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}