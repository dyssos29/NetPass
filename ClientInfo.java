import java.util.ArrayList;

public class ClientInfo
{
	private String clientAlias;
	private String clientPassword;
	private ArrayList<String> participateGroupNames;


	public ClientInfo(String clientAlias, String clientPassword)
	{
		this.clientAlias = clientAlias;
		this.clientPassword = clientPassword;
		participateGroupNames = new ArrayList<String>();
	}

	public String getClientAlias()
	{
		return clientAlias;
	}

	public String getClientPassword()
	{
		return clientPassword;
	}

	public void addParticipateGroupName(String groupName)
	{
		participateGroupNames.add(groupName);
	}

	public String getParticipateGroupNameAt(int index)
	{
		return participateGroupNames.get(index);
	}

	public int getNumberOfParticipateGroupNames()
	{
		return participateGroupNames.size();
	}
}
