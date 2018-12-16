import java.io.*; 
import java.net.*;
import java.util.*;

public class Server
{
    /* This is the port on which the server is running */
    private int serverPort;
	
	/* Constructor Method */
	public Server( int port )
	{
		serverPort = port;
	}  /* End Contrucotr Method */

	/* Server will contain the total ammount of users and groups in the server*/
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Group> groups = new ArrayList<>();

	private String userNamesList, groupNamesList;

	/* Listen Method */
	public void listen()
	{
		/* Socket for connections made */
		Socket connectionSocket = null;
		/* Server's listening socket */
		ServerSocket welcomeSocket;          
		
			// Create a socket to listen on.
            try {
				welcomeSocket = new ServerSocket( serverPort );
            }
            catch (IOException e) {
                System.out.println("Could not use server port " + serverPort);
                return;
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
                    System.out.println("Error accepting connection.");
                    continue;
                }
	  
                /* A connection was made successfully */
                System.out.println("<-- Made connection on server socket -->");
                /* Create a thread to handle it. */
				handleClient( connectionSocket );
            }
	}  /* End listen method */

	public void handleClient(Socket clientConnectionSocket)
    {
		System.out.println("<-- Starting thread to handle connection -->");
		new Thread(new ConnectionHandler(clientConnectionSocket, this)).start();
	}  /* End handleClient method */

	// Methods to retrieve a user or a group through the help of the server

	public void addUser(User chatUser)
	{
		users.add(chatUser);
	}

	public User getUser(User userInput, Group groupInput)
	{
		if(groupInput.isMember(userInput))
			return userInput;
		return null;
	}

	public Group getGroup(String groupName)
	{
		for(Group group : groups)
		{
			if(groupName.equals(group.getGroupName()))
				return group;
		}
		return null;
	}

	//Methods to retrieve all users or all groups currently operating in the server
	public String getAllUsers()
	{
		userNamesList = "";
		for(User user : users)
			userNamesList = userNamesList + user.getUserName() + ",";
		return userNamesList;
	}

	public String getAllGroups()
	{
		if (!groups.isEmpty())
		{
			for(Group group : groups)
				groupNamesList = groupNamesList + group.getGroupName() + ", ";
			return groupNamesList;
		}
		else
			return "";
	}

	//Method to destory a specific group
	public void destory(Group groupInput)
	{
		for( Group group : groups)
			if(groupInput.getGroupName().equals(group.getGroupName()))
				groups.remove(groupInput);
	}

	// Main method: Where the server will run indefinitely and also listen for incoming connections
	public static void main( String argv[] )
	{ 
			int port;
			port = Integer.parseInt( argv[0] );
	        Server server = new Server( port );
			server.listen();
			System.out.println("<-- Server exiting -->");
	}
}