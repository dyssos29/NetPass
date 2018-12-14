import java.util.*;

public class Group
{
	private String groupName;
	private String creator;
	private ArrayList<User> members;
	
	//Default constructor (To call the methods for the Group class)	
	public Group()
	{

	}

	public Group(String groupName, String creator, ArrayList<User> members)
	{
		this.groupName = groupName;
		this.creator = creator;
		this.members = new ArrayList<>();
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public void addMember(User member)
	{
		if(!isMember(member))
			this.members.add(member);
		else
			System.out.println("Error: User already in the chat!");
	}

	public void deleteMember(User member)
	{
		if(isMember(member))
			this.members.remove(member);
		else
			System.out.println("Error: User does not exist in the first place");
	}

	public String getGroupName()
	{
		return groupName;
	}

	public String getCreator()
	{
		return creator;
	}
	
	public String getMember(User user)
	{
		if(isMember(user))
			return user.getUserName();
		else
			return null;
	}

	public boolean isMember(User user)
	{
		for(User member : members)
			return user.getUserName().equals(member.getUserName());
		return false;
	}
}