import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread
{

	/* The protocol of the server (In order to understand how typing works)
        Sending message to a specific client -->
    */
	// A client socket to handle
	private Socket socketToHandle;

	//Server object to utilize the getters for users/groups in the server
	private Server server;
	// Input object
	private BufferedReader inFromClient;
	// Output object
	private PrintStream outToClient;
	// Holds messages we get from client
	private String clientSentence = "";
	// Message that is sent to client
	private String responseToClient;
	private ClientInfo clientInfo;

	public ClientThread(Socket socketToHandle, Server server)
	{
		this.socketToHandle = socketToHandle;
		this.server = server;
	}

	private void sendMessage(String msg)
	{
		responseToClient = msg;
		outToClient.println(responseToClient);
	}

	private void login(String clientSentence)
	{
		String[] token = clientSentence.split(":");
		clientInfo = new ClientInfo(token[1],token[2]);
	}

	public String getUserName()
	{
		return clientInfo.getClientAlias();
	}

	public String getPassword()
	{
		return clientInfo.getClientPassword();
	}

	private void sendClientUpdatedClientList()
	{
		String clients = "";
		for (int i = 0; i < server.getNumberOfClientThreads(); i++)
		{
			ClientThread tempClientThread = server.getClientThread(i);
			String clientName = tempClientThread.getUserName();
			if (!clientInfo.getClientAlias().equals(clientName))
			{
				clients += clientName + ",";
				tempClientThread.sendMessage("l:" + clients);
			}
		}
	}

	/**  * New thread for handling client interaction will start here.   */
	public void run(  )
	{
		/* Create a writing buffer to the socket */
		try {
			outToClient = new PrintStream( socketToHandle.getOutputStream(), true);
			/* Create a reading buffer to the socket */
			inFromClient = new BufferedReader(new InputStreamReader( socketToHandle.getInputStream()) );

			while ((clientSentence = inFromClient.readLine()) != null) {


				if (clientSentence.charAt(0) == 'u')
				{
					login(clientSentence);
					sendClientUpdatedClientList();
				}
				else
				{
					System.out.println("Client: " + clientSentence);
					if (clientSentence.equals("bye"))
						break;

					if (clientSentence.charAt(0) == 'p')
					{
						String[] token = clientSentence.split(":");
						for (int i = 0; i < server.getNumberOfClientThreads(); i++)
						{
							ClientThread temp = server.getClientThread(i);
							String recipientAlias = token[1];
							String message = token[2];
							if (recipientAlias.equals(temp.getUserName()))
								temp.sendMessage("Private message from " + clientInfo.getClientAlias() + " to " + recipientAlias + ": " + message);
						}
					}
					else if (clientSentence.charAt(0) == 'g')
					{
						String[] token = clientSentence.split(":");
						server.createGroup(token[1],clientInfo.getClientAlias());
					}
					else if (clientSentence.charAt(0) == 'j')
					{
						String[] token = clientSentence.split(":");
						server.joinGroup(clientInfo.getClientAlias(),token[1]);
					}
					else if (clientSentence.charAt(0) == 't')
					{
						String[] token = clientSentence.split(":");
						String nameOfGroup = token[1];
						for (int i = 0; i < server.getNumberOfGroupMembers(nameOfGroup); i++)
						{
							String memberName = server.getGroupMember(nameOfGroup,i);
							for (int j = 0; j < server.getNumberOfClientThreads(); j++)
							{
								ClientThread temp2 = server.getClientThread(j);
								String message = token[2];
								if (memberName.equals(temp2.getUserName()) && !clientInfo.getClientAlias().equals(temp2.getUserName()))
									temp2.sendMessage("Group message from " + clientInfo.getClientAlias() + " to " + nameOfGroup + " topic: " + message);
							}
						}
					}
					else
					{
						for (int i = 0; i < server.getNumberOfClientThreads(); i++)
						{
							ClientThread temp = server.getClientThread(i);
							if (!clientInfo.getClientAlias().equals(temp.getUserName()))
								temp.sendMessage("Broadcast msg: " + clientSentence);
						}
					}
				}
			}

			// Close all the handles
			/* Close input stream */
			inFromClient.close();
			/* Close output stream */
			outToClient.close();
			/* Close TCP connection with client on specific port */
			socketToHandle.close();
		} catch (IOException e) {
			System.err.println("Error handling I/O operations.");
		}

//        // Holds messages we send to client
//        String capitalizedSentence;
//        // Client's name
//        String peerName;
//
//        // Attach a println/readLine interface to the socket so we can read and write strings to the socket.
//        try {
//            /* Get the IP address from the client */
//            peerName = socketToHandle.getInetAddress().getHostAddress();
//            /* Create a writing stream to the socket */
//            outToClient = new PrintStream( socketToHandle.getOutputStream(), true );
//            /* Create a reading stream to the socket */
//            inFromClient = new BufferedReader( new InputStreamReader( socketToHandle.getInputStream() ) );
//        }
//        catch (IOException e) {
//            System.out.println("Error creating buffered handles.");
//            return;
//        }
//
//        System.out.println("Handling connection to client at " + peerName + " --");
//
//           /*
//				Main part of the Handler class.
//				Here we will have all the methods that will interact with the client input
//           */
//        while ( true )
//        {
//            try {
//                /* Read client's message through the socket's input buffer */
//                clientSentence = inFromClient.readLine();
//            }
//            catch (IOException e) {
//                System.out.println( socketToHandle.getInetAddress() + "-" + peerName + " broke the connection." );
//            }
//
//            if (clientSentence.charAt(0) == 'U')
//            {
//                String userNameStr = clientSentence.split(":")[1];
//                System.out.println("User " + userNameStr + " connected");
//                //server.addUser(new Client(userNameStr,null,null));
//            }
//            else
//            {
//                System.out.println( "Message Received: " + clientSentence );
//            }
//            /* Output to screen the message received by the client */
//
//            // Main part of the connection handler
//
//            // User wants to message another user
//            if( clientSentence.charAt(0) == 'm' )
//            {
//                // Logic to message a specific user
//                String subsentence[] = clientSentence.split(":");
//                String userName = subsentence[1];
//                String content = subsentence[2];
//
//                // clientSentence.substring(3, colonPosition - 1);
//                // Send the message to the user with the specfiic username
//                //String[] users = server.getAllUsers().split(",");
//                //System.out.println("Ody test --> username: " + userName + " content: " + content + " number of users: " + users.length);
////                for(String user : users)
////                {
////                    System.out.println("Inside for --> user: " + user);
////                    if(userName.equals(user))
////                    {
////                        System.out.println("Inside if --> user: " + user);
////                        outToClient.println(content);
////                        break;
////                    }
////                }
//            }
//
//            // Case to send a message to all the user of a specific group
//            else if( clientSentence.charAt(0) == 'b' )
//            {
//                // Logic to message a specific group
//
//                char sample = ':';
//                int colonPosition = 0;
//                for(int i = 10; i < clientSentence.length(); i++)
//                {
//                    if(clientSentence.charAt(i) == sample)
//                        colonPosition = i;
//                }
//
//                String groupName = clientSentence.substring(9, colonPosition - 1);
//                // Send the message to all the users in the specific group name
//            }
//
//            else if ( clientSentence.equals( "Users" ) )
//            {
//                // Logic to view all users
//
////                String list = server.getAllUsers();
////                int position = 0;
////                for(int i = 0; i < list.length(); i++)
////                {
////                    if(list.charAt(i) == ',')
////                    {
////                        outToClient.println(list.substring(position, i));
////                        position = i + 1;
////                    }
////                }
//            }
//
//            else if( clientSentence.equals( "Groups" ) )
//            {
//                // Logic to view all groups
////                String list = server.getAllGroups();
////                int position = 0;
////                for(int i = 0; i < list.length(); i++)
////                {
////                    if(list.charAt(i) == ',')
////                    {
////                        outToClient.println(list.substring(position, i));
////                        position = i + 1;
////                    }
////                }
//            }
//
//            else if( clientSentence.charAt(1) == 'j')
//            {
//                // Logic to join specific group
//                char sample = ':';
//                int colonPosition = 0;
//                for(int i = 0; i < clientSentence.length(); i++)
//                {
//                    if(clientSentence.charAt(i) == sample)
//                        colonPosition = i;
//                }
//
//                String groupName = clientSentence.substring(5, clientSentence.length());
////                String allGroups = server.getAllGroups();
////
////                boolean flag = false;
////                int position = 0;
////                for(int i = 0; i < allGroups.length(); i++)
////                {
////                    if(allGroups.charAt(i) == ',')
////                    {
////                        if(groupName.equals(allGroups.substring(position, allGroups.charAt(i) - 1)))
////                        {
////                            Group currentGroup = server.getGroup(groupName);
////                            currentGroup.addMember(user);
////                            flag = true;
////                        }
////                    }
////                }
//
////                if(!flag)
////                    outToClient.println( "Wrong group name mate" );
//            }
//
//            else if( clientSentence.charAt(1) == 'e' )
//            {
//                // Logic to exit specific group
//                char sample = ':';
//                int colonPosition = 0;
//                for(int i = 0; i < clientSentence.length(); i++)
//                {
//                    if(clientSentence.charAt(i) == sample)
//                        colonPosition = i;
//                }
//
//                String groupName = clientSentence.substring(5, clientSentence.length());
////                String allGroups = server.getAllGroups();
////
////                boolean flag = false;
////                int position = 0;
////                for(int i = 0; i < allGroups.length(); i++)
////                {
////                    if(allGroups.charAt(i) == ',')
////                    {
////                        if(groupName.equals(allGroups.substring(position, allGroups.charAt(i) - 1)))
////                        {
////                            Group currentGroup = server.getGroup(groupName);
////                            currentGroup.deleteMember(user);
////                            flag = true;
////                        }
////                    }
////                }
//
////                if(!flag)
////                    outToClient.println("Wrong group name mate");
//            }
//
//            // Logic to delete a specific group (if you are the creator of it)
//            else if( clientSentence.charAt(1) == 'd' )
//            {
//                char sample = ':';
//                int colonPosition = 0;
//                for(int i = 0; i < clientSentence.length(); i++)
//                {
//                    if(clientSentence.charAt(i) == sample)
//                        colonPosition = i;
//                }
//
//                String groupName = clientSentence.substring(5, clientSentence.length());
////                String[] allGroups = server.getAllGroups().split(",");
////
////                int position = 0;
////                System.out.println("Number of groups: " + allGroups.length);
////                for(int i = 0; i < allGroups.length; i++)
////                {
////                    if(groupName.equals(allGroups[i]))
////                    {
////                        Group currentGroup = server.getGroup(groupName);
////                        server.destory(currentGroup);
////                    }
////                }
//            }
//
//            /* If message is exit then terminate specific connection - exit the loop */
//            else if ( clientSentence.equals( "exit" ) )
//            {
//                System.out.println( "Closing connection with " + socketToHandle.getInetAddress() + "." );
//                System.out.println("Bye bye autist XD");
//                break;
//            }
//
//            else
//                System.out.println("Invalid command mate");
//        }
//
//        System.out.println("Closing " + peerName + " connection");
//
//        // Close all the handles
//        try {
//            /* Close input stream */
//            inFromClient.close();
//            /* Close output stream */
//            outToClient.close();
//            /* Close TCP connection with client on specific port */
//            socketToHandle.close();
//        }
//        catch ( IOException e ) {
//        }
	}  /* End run method */

} // end class