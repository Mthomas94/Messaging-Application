import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class ThreadedClient extends Thread{
	private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Message message;
    private Dictionary<String,Socket>onlineUsersWithSockets = new Hashtable<String,Socket>();
    private ArrayList<String>toWho;
    private String reason;
    private Boolean result;
    Database db;
    public ThreadedClient(Socket socket,Database db) {
        this.socket = socket;
        this.db=db;
    }

    @Override
    public void run() {
        try {
            //start database class
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
          
            while(socket.isConnected()){
            	message = (Message) in.readObject();
            	switch(message.getType()){
            	case ("LOGIN"):
            		result = db.checkLogin(message.getUserName(), message.getPassword());
            		reason = db.getResponse();
            		Message loginMessage = new Message("LOGIN",result,reason);
            		out.writeObject(loginMessage);
            		if(result){
            			//IF RESULT IS TRUE THEN ADD SOCKET AND USERNAME TO DICTIONARY
            			onlineUsersWithSockets.put(message.getUserName(), socket);
       
            			for(Enumeration<Socket> e = onlineUsersWithSockets.elements(); e.hasMoreElements();){
            				Message updateUser = new Message("UPDATE USERS",db.getOnlineUsers(),db.getOfflineUsers());
            				out = new ObjectOutputStream(e.nextElement().getOutputStream());
            				out.writeObject(updateUser);
            			}
            		}
            		break;
            	case ("REGISTER"):
            		result = db.registerUser(message.getUserName(), message.getPassword(),message.getScreenName());
            		reason = db.getResponse();
            		Message registerMessage = new Message("REGISTER",result,reason,message.getScreenName()); 
            		out.writeObject(registerMessage); 
            		if(result){
            			//IF RESULT IS TRUE THEN ADD SOCKET AND USERNAME TO DICTIONARY
            			onlineUsersWithSockets.put(message.getUserName(), socket);
            		}
            		break;
            	case ("GET USERS"):
            		Message getUserMessage = new Message("USER",db.getOnlineUsers(),db.getOfflineUsers());
            		out.writeObject(getUserMessage);
            		break;
            	case ("FILE"):
            		toWho = message.getDestUsers();
            		byte[]b = message.getFile();
            		for(String dest: toWho){
            		//RETRIEVING THE SOCKET OF THE USERNAME THEN SENDING FILE TO USER
            		out = new ObjectOutputStream(onlineUsersWithSockets.get(dest).getOutputStream());
            		Message fileMessage = new Message("FILE",message.getsrcUser(),toWho,b,message.getFileName(),message.getChatName());
            		out.writeObject(fileMessage);
            		}
            		break;
            	case ("MESSAGE"):
            		toWho = message.getDestUsers();
            		for(String dest: toWho){
            			//RETRIEVING THE SOCKET OF THE USERNAME THEN SENDING FILE TO USER
            		out = new ObjectOutputStream(onlineUsersWithSockets.get(dest).getOutputStream());
            		Message textMessage = new Message("MESSAGE",message.getsrcUser(),toWho,message.getMessageBody(),message.getChatName());
            		out.writeObject(textMessage);
            		}
            	case ("LOGOUT"):
            		onlineUsersWithSockets.remove(message.getUserName());
            		db.setState(message.getUserName(), false);
            		for(Enumeration<Socket> e = onlineUsersWithSockets.elements(); e.hasMoreElements();){
        				Message loggedOutUser = new Message("UPDATE USERS",db.getOnlineUsers(),db.getOfflineUsers());
        				out = new ObjectOutputStream(e.nextElement().getOutputStream());
        				out.writeObject(loggedOutUser);
        			}
            	case ("SCREENNAME"): 
            		result = db.changeScreenName(message.getOldScreenName(),message.getNewScreenName());
            		logOut();
            		break;
            	case ("PASSWORD"):
            		db.changePassword(message.getUserName(), message.getPassword());
            	default:     	
            	}
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        } finally {
        	try {
        		if(socket != null){
        			socket.close();
        		}
        	}catch(IOException e){
        	}
        }
    }
    
    public void logOut(){
    	if(socket != null){
    		try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }


}
