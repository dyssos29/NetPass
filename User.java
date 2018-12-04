import java.net.*;
import java.io.*;
import java.util.ArrayList;
/*
	User class: Saves all the data that is important to be stored for a user
*/


public class User
{
	String userName;
	String IPAddress;
	int port;
	ArrayList<String> memberGroups;

	public User(String userName, String IPAddress, ArrayList<String> memberGroups, int port)
	{
		this.userName = userName;
		this.IPAddress = IPAddress;
		this.memberGroups = memberGroups;
		this.port = port;
	}

	/* Getter methods */
	public String getUserName()
	{
		return userName;
	}

	public String getIPAddress()
	{
		return IPAddress;
	}

	public String getJoinedGroups(ArrayList<String> memberGroups)
	{
		String groups = "";
		for(int i = 0; i < memberGroups.size(); i++)
			groups = groups + memberGroups.get(i) + ", ";
		return groups;
	}

	public String getPort()
	{
		return port;
	}

	/* Setter methods */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public void addMember(String member)
	{
		memberGroups.add(member);
	}

	public void removeMember(String member)
	{
		memberGroups.remove(member);
	}
}