import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Message implements Serializable{

    private String type;
    private byte[] b;
    private File file;
    String srcUser;
    String messageBody;
    ArrayList<String> destUsers=new ArrayList<>();
    ArrayList<String> onlineUsers=new ArrayList<>();
    ArrayList<String> offlineUsers=new ArrayList<>();
    String username;
    String password;
    boolean response;
    String chatName;
    //clientSendingMessages type MESSAGE
    public Message(String type, String srcUser, ArrayList<String> destUsers, String messageBody, String chatName){
		this.type=type;
		this.srcUser=srcUser;
		this.destUsers=destUsers;
		this.messageBody=messageBody;
		this.chatName=chatName;
	}
    //clientLogin type= LOGIN
    public Message(String type, String username, String password){
  		this.type=type;
  		this.username=username;
  		this.password=password;
  	}
    //clientSendFile type=FILE
    public Message(String type, String srcUser, String destUser, byte[] file, String fileName, String chatName){
  		this.type=type;
  		this.srcUser=srcUser;
  	}
    
    //serverregister type=REGISTER & type=LOGIN
    public Message(String type, boolean response,String reason){
    	
  	}
    
    //server users type=USER
    public Message(String type , ArrayList<String> onlineUsers,ArrayList<String> offlineUsers){
    	this.onlineUsers=onlineUsers;
   	}
    
    //client userListrequest type=USER
    public Message(String type){
 
   	}

    public String getReason() {
        return this.type;
    }
    public String getType() {
        return this.type;
    }
    
    public String getChatName() {
        return this.chatName;
    }
    
    public ArrayList<String> getOnlineUser(){
    	return this.onlineUsers;
    }
    public ArrayList<String> getOfflineUser(){
    	return this.offlineUsers;
    }
    
    public String getsrcUser() {
        return this.srcUser;
    }
    
    public String getMessageBody() {
        return this.messageBody;
    }

    public boolean getResponse() {
        return this.response;
    }
    
    public byte[] getBody() {
        return this.b;
    }

    public byte[] getFile() {
        Path f = file.toPath();
        try {
            return Files.readAllBytes(f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}