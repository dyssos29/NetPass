import java.util.*;

public class Group
{
	private String groupName;
	private String creator;
	private ArrayList<String> members;

	public Group(String groupName, String creator)
	{
		this.groupName = groupName;
		this.creator = creator;
		members = new ArrayList<>();
		members.add(creator);
	}

	public void addMember(String member)
	{
		if(!isMember(member))
			members.add(member);
		else
			System.out.println("Error: User already in the chat!");
	}

	public boolean isMember(String user)
	{
		for(String member : members)
			if (user.equals(member))
				return true;
		return false;
	}
//    public void deleteMember(Client member)
//    {
//        if(isMember(member))
//            this.members.remove(member);
//        else
//            System.out.println("Error: User does not exist in the first place");
//    }

	public String getGroupName()
	{
		return groupName;
	}

	public String getCreator()
	{
		return creator;
	}

	public String getMember(int index)
	{
		return members.get(index);
	}

	public int getNumberOfMembers()
	{
		return members.size();
	}
}