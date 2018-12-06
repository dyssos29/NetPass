/*
 * Filename: TCPEchoClient.java
 * Description: An echo client using connection-oriented delivery system (TCP).
 *              Sends character messages to a server which are echoed capitalized.
 *              Error handling and exceptions are implemented!
 * Operation: java TCPEchoClient [hostname] [port]
 *
 */

import java.io.*; 
import java.net.*; 


public class MuTCPEchoClient
{
	public void connect(String host, int port)
	{
		/* Our socket end */
		Socket clientSocket;
		/* For writing to socket */
		PrintStream outToServer;
		// For reading from socket */
		BufferedReader inFromServer;          
		/* For reading from user */
		BufferedReader inFromUser;                  
		/* Hold user input */
		String sentence = null, modifiedSentence = null;
		/* Hold user alias (nickname)*/
		String alias;

		/*Initialize scanner for username input*/
		//Scanner userNameInput;
        
		System.out.println("-- Client connecting to host/port " + host + "/" + port + " --");

			/* Connect to the server at the specified host/port */
            try {
                clientSocket = new Socket( host, port );
				/* Create a buffer to hold the user's input */
				inFromUser = new BufferedReader( new InputStreamReader( System.in ) ); 
				/* Create a writing buffer to the socket */
				outToServer = new PrintStream( clientSocket.getOutputStream(), true); 
				/* Create a reading buffer to the socket */
				inFromServer = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()) ); 
            } catch (UnknownHostException e) {
				System.out.println("Can not locate host/port " + host + "/" + port);
				return;
            } catch (IOException e) {
				System.out.println("Could not establish connection to: " + host + "/" + port);
				return;
            }

            System.out.println("<-- Connection established  -->");

            // Input the username
			//userNameInput = new Scanner(System.in);

			// Parse username in a string
			//alias = userNameInput.nextLine();

			//Forge packet to send to server 
			//U. means that the message is the user alias
			//String packet = "U. " + alias;

			//Send packet to server with alias
			//outToServer.println(packet);

            try
            {
                /* Continue forever until user types 'exit' */
				/* Types sentences to the server which are returned capitalized */
				while ( true ) {
					System.out.println( "Type message to send to server: ");
					/* Get user's input */
					sentence = inFromUser.readLine();
					/* Send the message to server */	        
					outToServer.println( sentence );
			
					/* Stop infinite loop if user wants to stop getting echos by typing exit */
					if ( sentence.equals( "exit" ) )
						break;
			
					/* Read the server's response */
					modifiedSentence = inFromServer.readLine(); 
					/* Display echoed message from server */
					System.out.println("Server returned: " + modifiedSentence); 
				}
				
                // Close all of our connections.
                outToServer.close();
                inFromServer.close();
                clientSocket.close();

                //SWITCH METHODS HERE (The menu at the server)
            } catch (IOException e) {
				System.out.println("I/O to socket failed: " + host);
            }
	}  /* End Connect Method */

	public static void main( String[] argv )
	{
		/* Holds the server's name */
		String server;
		/* Holds the server's port number  */
		int port;
			
		/* The first argument is the server's name */
		server = argv[0];
		/* The second argument the port that the server accepts connections */
		port = Integer.parseInt(argv[1]);
			
		/* Create a new instance of the client */
		MuTCPEchoClient myclient = new MuTCPEchoClient();

		/* Make a connection. It should not return until the client exits */
		myclient.connect(server, port);

		System.out.println("<-- Client has exited -->");
     } /* End main method */

} // MuTCPEchoClient

/*
 * Example:
 *   java TCPEchoClient machinename 4567
 * Output:
 *	 Connected to: machinename/IPaddress on port 4567
 *	 Type a message to send to server:
 *   Hallo server
 *   Server returned: HALLO SERVER
 */

/*** EXTRA INFORMATION ***/
/*
BufferedReader: it supports input buffering. It provides the readLine() method for reading an entire line at a
                time from a stream.
InputStreamReader: reads a stream. It is used to convert between byte streams and character streams. It provides
				   a bridge between byte-oriented and character-oriented input streams.
PrintStream: Prints to an output stream.
*/