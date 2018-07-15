package Client;
public class Credentials 
{
	private String password, username,screenName;
	
	public String getUsername()
	{	return username;
	}
	
	public String getPassword()
	{	return password;
	}
	
	public void setUsername(String username)
	{	this.username = username;
	}

	public void setPassword(String password)
	{	this.password = password;
	}
	
	public boolean equals(Object ob)
	{	Credentials c = (Credentials) ob;
		if(c.username.equals(this.username))
		{	return true;
		}
		return false;
	}
}

