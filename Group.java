/* WHERE ALL THE OPERATIONAL LOGIC HAPPENS: IMPORTANT */

import java.net.*;
import java.io.*;
import java.util.ArrayList;

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
	public String getGroupName()
	{
		return groupName;
	}

	public String getAccessRights()
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

	public String getMembers()
	{
		String list = "";
		for(int i = 0; i < members.size(); i++)
			list = list + members.get(i) + ", ";
		return list;
	}

	public String getMemberRights()
	{
		/* Return all the rights of the group */
		String rightsList = "";
		for(int i = 0; i < memberRights.size(); i++)
			rightsList = rightsList + memberRights.get(i) + ", ";
		return rightsList;
	}

	public String getMemberIPAddress()
	{
		String ipList = "";
		for(int i = 0; i < memberIPAddresses.size(); i++)
			ipList = ipList + memberIPAddresses.get(i) + ", ";
		return ipList;
	}
}