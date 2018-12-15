import java.io.*;
import java.net.*;
import java.util.*;

public class ConnectionHandler implements Runnable
{

	/* The protocol of the server (In order to understand how typing works)
		Sending message to a specific client -->
	*/
	// Socket for our endpoint
	protected Socket echoSocket;

	//Server object to utilize the getters for users/groups in the server
	private Server server;

	// Connection handler will have a user object and a group arraylist object to work with
	User user = new User();
	private ArrayList<Group> groups = new ArrayList<>();

	public ConnectionHandler(Socket aSocketToHandle, Server server)
	{
		this.echoSocket = aSocketToHandle;
		this.server = server;
	}

	/**  * New thread for handling client interaction will start here.   */
    public void run(  )
        {
			// Holds messages we get from client
			String clientSentence = "";
			// Holds messages we send to client
			String capitalizedSentence; 
			// Input object
			BufferedReader inFromClient; 
		    // Output object
			PrintStream outToClient;
			// Client's name
			String peerName;            

            // Attach a println/readLine interface to the socket so we can read and write strings to the socket.
            try {
                /* Get the IP address from the client */
                peerName = echoSocket.getInetAddress().getHostAddress();
				/* Create a writing stream to the socket */
				outToClient = new PrintStream( echoSocket.getOutputStream(), true );				
				/* Create a reading stream to the socket */
				inFromClient = new BufferedReader( new InputStreamReader( echoSocket.getInputStream() ) ); 
            }
            catch (IOException e) {
                System.out.println("Error creating buffered handles.");
                return;
            }
	  
           System.out.println("Handling connection to client at " + peerName + " --");

           /*
				Main part of the Handler class.
				Here we will have all the methods that will interact with the client input
           */
            while ( true )
            {
				try {
					/* Read client's message through the socket's input buffer */
					clientSentence = inFromClient.readLine();
				}
				catch (IOException e) {
					System.out.println( echoSocket.getInetAddress() + "-" + peerName + " broke the connection." );
				}

				if (clientSentence.charAt(0) == 'U')
				{
					String userNameStr = clientSentence.split(":")[1];
					System.out.println("User " + userNameStr + " connected");
					server.addUser(new User(userNameStr,null,null));
				}
				else
				{
					System.out.println( "Message Received: " + clientSentence );
				}
				/* Output to screen the message received by the client */

				// Main part of the connection handler

				// User wants to message another user
				if( clientSentence.charAt(0) == 'm' )
				{
					// Logic to message a specific user
					String subsentence[] = clientSentence.split(":");
					String userName = subsentence[1];
					String content = subsentence[2];

					// clientSentence.substring(3, colonPosition - 1);
					// Send the message to the user with the specfiic username
					String[] users = server.getAllUsers().split(",");
					System.out.println("Ody test --> username: " + userName + " content: " + content + " number of users: " + users.length);
					for(String user : users)
					{
						System.out.println("Inside for --> user: " + user);
						if(userName.equals(user))
						{
							System.out.println("Inside if --> user: " + user);
							outToClient.println(content);
						}
					}
				}

				// Case to send a message to all the user of a specific group
				else if( clientSentence.charAt(0) == 'b' )
				{
					// Logic to message a specific group

					char sample = ':';
					int colonPosition = 0;
					for(int i = 10; i < clientSentence.length(); i++)
					{
						if(clientSentence.charAt(i) == sample)
							colonPosition = i;
					}

					String groupName = clientSentence.substring(9, colonPosition - 1);
					// Send the message to all the users in the specific group name
				}

				else if ( clientSentence.equals( "Users" ) )
				{
					// Logic to view all users

					String list = server.getAllUsers();
					int position = 0;
					for(int i = 0; i < list.length(); i++)
					{
						if(list.charAt(i) == ',')
						{
							outToClient.println(list.substring(position, i));
							position = i + 1;
						}
					}
				}

				else if( clientSentence.equals( "Groups" ) )
				{
					// Logic to view all groups
					String list = server.getAllGroups();
					int position = 0;
					for(int i = 0; i < list.length(); i++)
					{
						if(list.charAt(i) == ',')
						{
							outToClient.println(list.substring(position, i));
							position = i + 1;
						}
					}
				}

				else if( clientSentence.charAt(1) == 'j')
				{
					// Logic to join specific group
					char sample = ':';
					int colonPosition = 0;
					for(int i = 0; i < clientSentence.length(); i++)
					{
						if(clientSentence.charAt(i) == sample)
							colonPosition = i;
					}

					String groupName = clientSentence.substring(5, clientSentence.length());
					String allGroups = server.getAllGroups();

					boolean flag = false;
					int position = 0;
					for(int i = 0; i < allGroups.length(); i++)
					{
						if(allGroups.charAt(i) == ',')
						{
							if(groupName.equals(allGroups.substring(position, allGroups.charAt(i) - 1)))
							{
								Group currentGroup = server.getGroup(groupName);
								currentGroup.addMember(user);
								flag = true;
							}
						}
					}

					if(!flag)
						outToClient.println( "Wrong group name mate" );
				}

				else if( clientSentence.charAt(1) == 'e' )
				{
					// Logic to exit specific group
					char sample = ':';
					int colonPosition = 0;
					for(int i = 0; i < clientSentence.length(); i++)
					{
						if(clientSentence.charAt(i) == sample)
							colonPosition = i;
					}

					String groupName = clientSentence.substring(5, clientSentence.length());
					String allGroups = server.getAllGroups();

					boolean flag = false;
					int position = 0;
					for(int i = 0; i < allGroups.length(); i++)
					{
						if(allGroups.charAt(i) == ',')
						{
							if(groupName.equals(allGroups.substring(position, allGroups.charAt(i) - 1)))
							{
								Group currentGroup = server.getGroup(groupName);
								currentGroup.deleteMember(user);
								flag = true;
							}
						}
					}

					if(!flag)
						outToClient.println("Wrong group name mate");
				}

				// Logic to delete a specific group (if you are the creator of it)
				else if( clientSentence.charAt(1) == 'd' )
				{
					char sample = ':';
					int colonPosition = 0;
					for(int i = 0; i < clientSentence.length(); i++)
					{
						if(clientSentence.charAt(i) == sample)
							colonPosition = i;
					}

					String groupName = clientSentence.substring(5, clientSentence.length());
					String[] allGroups = server.getAllGroups().split(",");

					int position = 0;
					System.out.println("Number of groups: " + allGroups.length);
					for(int i = 0; i < allGroups.length; i++)
					{
						if(groupName.equals(allGroups[i]))
						{
							Group currentGroup = server.getGroup(groupName);
							server.destory(currentGroup);
						}
					}
				}

				/* If message is exit then terminate specific connection - exit the loop */
				else if ( clientSentence.equals( "exit" ) )
				{
					System.out.println( "Closing connection with " + echoSocket.getInetAddress() + "." );
					System.out.println("Bye bye autist XD");
					break;
				}

				else
					System.out.println("Invalid command mate");
            }

            System.out.println("Closing " + peerName + " connection");

            // Close all the handles
            try {
				/* Close input stream */
				inFromClient.close();
				/* Close output stream */
				outToClient.close();
				/* Close TCP connection with client on specific port */
				echoSocket.close();
            }
            catch ( IOException e ) {
            }
	}  /* End run method */

} // end class