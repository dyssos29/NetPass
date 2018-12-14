import java.util.*;
public class User
{
	/*USER CLASS
	Will contain the username, groups that the user is a member
	and the right that the user has in each group
	*/
	private String userName;
	private ArrayList<Group> groupMembership;
	private ArrayList<String> groupRight;

	//Default constructor (To call the methods for the User class)
	public User()
	{

	}
	
	public User(String userName, ArrayList<Group> groupMembership, ArrayList<String> groupRight)
	{
		this.userName = userName;
		this.groupMembership = new ArrayList<>();
		this.groupRight = new ArrayList<>();
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public void setGroupmembership(Group group)
	{
		this.groupMembership.add(group);
	}

	public void setGroupRight(String right)
	{
		this.groupRight.add(right);
	}

	public String getUserName()
	{
		return userName;
	}

	public Group getGroup(int position)
	{
		if(position >= 0 && position < groupMembership.size())
			return groupMembership.get(position);
		else
			return null;
	}
}