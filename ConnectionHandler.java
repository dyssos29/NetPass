//import java.io.*;
//import java.net.*;
//import java.util.*;
//
//public class ConnectionHandler implements Runnable
//{
//	private ArrayList<User> y;
//	private  ArrayList<Group> x;
//	// Socket for our endpoint
//	protected Socket echoSocket;
//
//	// Put array list of group type and the user type in the parameter of the constructor
//	public ConnectionHandler(Socket aSocketToHandle, ArrayList<Group> x, ArrayList<User> y)
//	{
//		this.echoSocket = aSocketToHandle;
//		// initialize them here as well
//		this.x = new ArrayList<Group>();
//		this.y = new ArrayList<User>();
//	}
//
//	/**  * New thread for handling client interaction will start here.   */
//    public void run(  )
//        {
//			// Holds messages we get from client
//			String clientSentence = "";
//			// Holds messages we send to client
//			String capitalizedSentence;
//			// Input object
//			BufferedReader inFromClient;
//		    // Output object
//			PrintStream outToClient;
//			// Client's name
//
//
//			/* CHATTING I/O objects */
//			BufferedReader chatFromClient;
//
//			PrintStream chatToClient;
//
//			Socket chattingSocket;
//
//			String chatFromUser;
//
//			String peerName;
//
//			// To temporarily hold a user's nickname so that it can be added inside a group
//			String temp;
//
//            // Attach a println/readLine interface to the socket so we can read and write strings to the socket.
//            try {
//                /* Get the IP address from the client */
//                peerName = echoSocket.getInetAddress().getHostAddress();
//				/* Create a writing stream to the socket */
//				outToClient = new PrintStream( echoSocket.getOutputStream(), true );
//				/* Create a reading stream to the socket */
//				inFromClient = new BufferedReader( new InputStreamReader( echoSocket.getInputStream() ) );
//            }
//            catch (IOException e) {
//                System.out.println("Error creating buffered handles.");
//                return;
//            }
//
//           System.out.println("Handling connection to client at " + peerName + " --");
//
//            while ( true )
//            {
//				try {
//					/* Read client's message through the socket's input buffer */
//					clientSentence = inFromClient.readLine();
//				}
//				catch (IOException e) {
//					System.out.println( echoSocket.getInetAddress() + "-" + peerName + " broke the connection." );
//					break;
//				}
//
//				/* Output to screen the message received by the client */
//				System.out.println( "Message Received: " + clientSentence );
//
//				System.out.println("WELCOME TO THE SERVER");
//				System.out.println("First you need to enter a username");
//				System.out.println("To view groups, enter G");
//				System.out.println("To view users for private messaging, enter P");
//				System.out.println("To open a private chat with a user enter the username of the user");
//				System.out.println("To exit from a private messaging session enter 'Exit'");
//				System.out.println("To exit from the application session enter 'exit'");
//
//				/* Protocol Logic Section */
//				if(clientSentence.charAt(0) == 'U')
//				{
//					// String userName, String IPAddress, ArrayList<String> memberGroups
//					temp = clientSentence.substring(3, clientSentence.length);
//					y.add(new User(temp));
//				}
//
//				// To add more logic for the protocol system
//				else if(clientSentence.charAt(0) == 'G')
//				{
//					String name;
//					for(int i = 0; i < x.size(); i++)
//						name = x.get(i).getGroupName();
//					System.out.println(name);
//				}
//
//				else if(clientSentence.charAt(0) == 'P')
//				{
//					String name;
//					for(int i = 0; i < y.size; i++)
//						name = y.get(i).getUserName();
//					System.out.println(name);
//				}
//
//				/* 	Logic to join/exit private chat
//						Open a new window with the user to chat
//						Establish a connection between the 2 users
//				*/
//				else if(isUser(clientSentence))
//				{
//						while(!clientSentence.equals("Exit"))
//						{
//
//							// Initialize port to connect to client
//							//chattingSocket = new Socket(y.getUserName().getIPAddress(), y.getUserName.getPort());
//
//							// Input from client
//							try {
//								//chatFromClient = new BufferedReader( new InputStreamReader(chattingSocket.getInputStream()) );
//
//
//							chatFromUser = chatFromClient.readLine();
//							System.out.println(chatFromUser);
//
//							//Output to client
//							chatToClient = new PrintStream( chattingSocket.getOutputStream(), true);
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//
//						// Close all connections
////						chatFromClient.close();
////						chatToClient.close();
////						chattingSocket.close();
//				}
//
//				/* Logic to join group chat
//
//				*/
//				else if(isGroup(clientSentence))
//				{
//					//x.addMembers()
//				}
//
//				/* Logic to send message to group chat
//
//				*/
//
//
//
//				/* If message is exit then terminate specific connection - exit the loop */
//				if ( clientSentence.equals( "exit" ) ) {
//					System.out.println( "Closing connection with " + echoSocket.getInetAddress() + "." );
//					break;
//				}
//
//				/* Capitalize the received message */
//				capitalizedSentence = clientSentence.toUpperCase();
//				/* Send it back through socket's output buffer */
//				outToClient.println( capitalizedSentence );
//            }
//
//            System.out.println("Closing " + peerName + " connection");
//
//            // Close all the handles
//            try {
//				/* Close input stream */
//				inFromClient.close();
//				/* Close output stream */
//				outToClient.close();
//				/* Close TCP connection with client on specific port */
//				echoSocket.close();
//            }
//            catch (IOException e) {
//            }
//	}  /* End run method */
//
//	public boolean isUser(String userName)
//	{
//		String nameCheck;
//		for(int i = 0; i < y.size(); i++)
//		{
//			nameCheck = y.get(i).getUserName();
//			if(userName.equals(nameCheck))
//				return true;
//		}
//		return false;
//	}
//
//	public boolean isGroup(String groupName)
//	{
//		String nameCheck;
//		for(int i = 0; i < x.size(); i++)
//		{
//			nameCheck = x.get(i).getGroupName();
//			if(groupName.equals(nameCheck))
//				return true;
//		}
//		return false;
//	}
//
//} // end class