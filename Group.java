/* WHERE ALL THE OPERATIONAL LOGIC HAPPENS: IMPORTANT */

import java.net.*;
import java.io.*;
import util.ArrayList;

public class Group
{
	String groupName; 
	ArrayList<String> members;
	ArrayList<String> memberRights; 
	ArrayList<String> memberIPAddresses; 
	String accessRights;

	public Group(String groupName, ArrayList<String> members, ArrayList<String> memberRights, ArrayList<String> memberIPAddresses, String accessRights)
	{
		this.groupName = groupName;
		this.accessRights = accessRights;
		/* Arraylists are gonna be filled with packets so they are not initalized */
	}
	
	/* Setter methods */
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public void setAccessRights(String accessRights)
	{
		this.accessRights = accessRights;
	}


	/* Getter methods */
	public String getGroupName(String groupName)
	{
		return groupName;
	}

	public String getAccessRights(String accessRights)
	{
		return accessRights;
	}

	/* Arraylist methods */

	public void addMembers(String member)
	{
		this.members.add(member);
	}

	public void addMemberRights(String memberRight)
	{
		this.memberRights.add(memberRight);
	}

	public void addMemberIPAddress(String memberIPAaddress)
	{
		this.memberIPAddresses.add(memberIPAaddress);
	}

	public String getMembers(Arraylist<String> members)
	{
		String list = "";
		for(int i = 0; i < members.size(); i++)
			list = list + members.get(i) + ", ";
	}

	public String getMemberRights(Arraylist<String> memberRights)
	{
		/* Return all the rights of the group */
	}

	public String getMemberIPAddress()
	{
		/* Get all the IP addresses */
	}
}