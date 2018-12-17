import java.io.*;
import java.net.*;
import java.util.*;

public class Server
{
	/* This is the port on which the server is running */
	private int serverPort;
	/* Server will contain the total amount of users and groups in the server*/
	private ArrayList<ClientThread> clientThreadList;
	private ArrayList<Group> groups;
	/* The names of the all the clients and groups in total */
	private String clientNamesList, groupNamesList;
	/* Socket for connections made */
	private Socket connectionSocket = null;
	/* Server's listening socket */
	private ServerSocket welcomeSocket;
	// Holds messages we get from client
	private String clientSentence = "";
	// Holds messages we send to client
	private String responseToClient;
	// Input object
	private BufferedReader inFromClient;
	// Output object
	private PrintStream outToClient;

	/* Constructor Method */
	public Server(String port)
	{
		serverPort = Integer.parseInt(port);
		clientThreadList = new ArrayList<ClientThread>();
		groups = new ArrayList<>();
	}  /* End Contrucotr Method */

	/* Listen Method */
	public void listen()
	{

		// Create a socket to listen on.
		try {
			welcomeSocket = new ServerSocket( serverPort );
		}
		catch (IOException e) {
			System.err.println("Could not use server port " + serverPort);
		}

		// Listen forever for new connections.  When a new connection
		// is made a new socket is created to handle it.
		while ( true )
		{
			System.out.println("<-- Server listening on socket " + serverPort + " -->");
			/* Try and accept the connection */
			try {
				connectionSocket = welcomeSocket.accept();
			}
			catch (IOException e) {
				System.err.println("Error accepting connection.");
				continue;
			}

			System.out.println("<-- Successfully accepted connection from a client -->");
			System.out.println("<-- Starting thread to handle connection -->");
			addUser(new ClientThread(connectionSocket,this));
		}
	}  /* End listen method */
	//
//    // Methods to retrieve a user or a group through the help of the server
//
	public void addUser(ClientThread clientThread)
	{
		clientThread.start();
		clientThreadList.add(clientThread);
	}
	//
	public ClientThread getClientThread(int index)
	{
		return clientThreadList.get(index);
	}

	public int getNumberOfClientThreads()
	{
		return clientThreadList.size();
	}

	public void createGroup(String groupName, String creator)
	{
		groups.add(new Group(groupName,creator));
	}

	public void joinGroup(String clientName, String groupName)
	{
		for (Group group: groups)
		{
			if (group.getGroupName().equals(groupName))
				group.addMember(clientName);
		}
	}

	public String getGroupMember(String groupName, int index)
	{
		String member = "";
		for (Group group: groups)
		{
			if (group.getGroupName().equals(groupName))
				member = group.getMember(index);
		}

		return member;
	}

	public int getNumberOfGroupMembers(String groupName)
	{
		int number = 0;
		for (Group group: groups)
		{
			if (group.getGroupName().equals(groupName))
				number = group.getNumberOfMembers();
		}

		return number;
	}
//
//    public Group getGroup(String groupName)
//    {
//        for(Group group : groups)
//        {
//            if(groupName.equals(group.getGroupName()))
//                return group;
//        }
//        return null;
//    }
//
//    //Methods to retrieve all users or all groups currently operating in the server
//    public String getAllUsers()
//    {
//        clientNamesList = "";
//        for(User user : users)
//            clientNamesList = clientNamesList + user.getUserName() + ",";
//        return clientNamesList;
//    }
//
//    public String getAllGroups()
//    {
//        if (!groups.isEmpty())
//        {
//            for(Group group : groups)
//                groupNamesList = groupNamesList + group.getGroupName() + ", ";
//            return groupNamesList;
//        }
//        else
//            return "";
//    }
//
//    //Method to destory a specific group
//    public void destory(Group groupInput)
//    {
//        for( Group group : groups)
//            if(groupInput.getGroupName().equals(group.getGroupName()))
//                groups.remove(groupInput);
//    }

	// Main method: Where the server will run indefinitely and also listen for incoming connections
	public static void main( String args[] )
	{
		Server server = new Server(args[0]);
		server.listen();
		System.out.println("<-- Server exiting -->");
	}
}