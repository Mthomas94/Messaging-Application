import java.sql.*;
import java.util.ArrayList;
import java.awt.List;
import java.net.*;
public class DataBase {
     Statement st;
     String reason;
     boolean getResponse;
     
     ArrayList<String> OnlineUsers = new ArrayList<String>();
     ArrayList<String> OfflineUsers = new ArrayList<String>();
    public DataBase() {
    try{
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection("jdbc:h2:~/test","sa","sa");
            Statement st = con.createStatement();
            String query = ("DROP TABLE IF EXISTS CREDENTIALS;");
            query = ("CREATE TABLE CREDENTIALS(username VARCHAR(15) PRIMARY KEY, password VARCHAR(100), screenname VARCHAR(15), loggedon(15));");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
     
    
    
    public void registorUser(String userName, String Password, String screenName) throws SQLException{ 
        try {
            
            String name = userName;
            String password = Password;
            String sn = screenName;
            
            String query = "SELECT * FROM CREDENTIALS;";
            ResultSet rs = st.executeQuery(query);
            
            boolean userexists = false;
            boolean screenNameExist = false;
            while (rs.next()){
                
                String username = rs.getString("USERNAME");
                String userpassword = rs.getString("PASSWORD");
                String screen = rs.getString("screenname");
                if(username.equals(name)){
                    
                 userexists = true;
                 
                     if(sn.equals(screen)){
                         
                     screenNameExist = true;
                         
                     }else{
                         query = ("INSERT INTO CREDENTIALS VALUES('','',"+sn+",'');");
                     }
                  
                }else{
                    query = ("INSERT INTO CREDENTIALS VALUES("+name+","+password+",'');");
                    
                    
                }
                
            }
            
            if(userexists){
                getResponse=false;
                reason="USERNAME ALREADY EXIST!!!";
                
            }else{
                    getResponse = true;
                    reason ="";
            }
            
            rs.close();
            ;
            
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
    }
    
    public void checkLogin(String userName, String password){
        try {
            
            String name = userName;
            String Password = password;
            
            String query = "SELECT * FROM CREDENTIALS;";
            ResultSet rs = st.executeQuery(query);
            
            boolean success = false;
            
            while (rs.next()){
                
                String username = rs.getString("USERNAME");
                String userpassword = rs.getString("PASSWORD");
                
                if(username.equals(name)&& userpassword.equals(Password))
                    
                 success = true;
                 
                }
                
            if(success){
            getResponse = false;
            reason = "CHECK LOGIN DETAILS";    
            }
            else{
                getResponse = true;
                reason = "";
            }
            rs.close();
            
            
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
            
    }
    public Boolean changeScreenName(String oldScreenName, String newScreenName){
        
        try{
            String query = "SELECT * FROM CREDENTIALS;";
            ResultSet rs = st.executeQuery(query);
            
            boolean exist = false;
            
            while (rs.next()){
                String screenname = rs.getString("screenname");
                if(screenname.equals(newScreenName)){
        
                 exist = true; 
                }
                else{
                    st.executeUpdate("UPDATE CREDENTIALS SET screenname = "+newScreenName+" WHERE screenname="+oldScreenName+");");
                }
            }
            
            if(exist){
                getResponse = false;
                reason = "SCREEN NAME ALREADY EXIST!!!";    
                }
                else{
                    getResponse = true;
                    reason = "";
                }
            rs.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return getResponse;
        
        
        
    }
    public void changePassword(String userName, String password){
        try{
            
            st.executeUpdate("UPDATE CREDENTIALS SET password = "+password+" WHERE username="+userName+");");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setState(String userName,boolean state){
        
        try {
            
            st.executeUpdate("UPDATE CREDENTIALS SET LOGGEDON = "+state+" WHERE username="+userName+");");
    
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public ArrayList<String> OnlineUsers(){
        
        try{
            String query = "SELECT * FROM CREDENTIALS;";
            ResultSet rs = st.executeQuery("SELECT SCREENNAME FROM CREDENTIALS WHERE LOGGEDON=TRUE;");
            
            while (rs.next()){
                
                OnlineUsers.add(rs.getString("loggedon"));
                }
        }catch(SQLException e){
            
            e.printStackTrace();
        }
        return OnlineUsers;
    }
        public ArrayList<String> OfflineUsers(){
            
            try{
                String query = "SELECT * FROM CREDENTIALS;";
                ResultSet rs = st.executeQuery("SELECT SCREENNAME FROM CREDENTIALS WHERE LOGGEDON=false;");
                
                while (rs.next()){
                    
                    OfflineUsers.add(rs.getString("loggedon"));
                    }
            }catch(Exception e){
                e.printStackTrace();
            }
            return OfflineUsers;
        }
     
        
}
    
 

