import java.io.*; 
import java.net.*; 
import java.util.*;

public class Client
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
		String sentence = null, modifiedSentence = null, username = null;

		
		String userSentence;

		String ipAddress;
        
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
            
            System.out.println("Enter your username please: ");

          	try
            {
            	username = inFromUser.readLine();
            }
            catch(IOException e)
            {
            	e.printStackTrace();
            }

            ipAddress = clientSocket.getInetAddress().getHostAddress();
            
            outToServer.println("User:" + username);
            try
            {	boolean flag = false;
            	Scanner input = new Scanner(System.in);
                /* Continue forever until user types 'exit' */
				/* Types sentences to the server which are returned capitalized */
				while ( true ) 
				{
					System.out.println("Netpass says hello to: " + username);
					System.out.println("message:username:message --> message a specific user");
					System.out.println("view users --> view all the users in the server");
					System.out.println("view groups --> view all the groups in the server");
					System.out.println("join group --> join a group");
					System.out.println("exit group --> exit from a group");
					System.out.println("delete group --> delete a group (only works if you are the creator of the group)");
					System.out.println("bye --> exit from the server");
					
					userSentence = input.nextLine();
					
					switch(userSentence)
					{	
						case "message":
							sentence = inFromUser.readLine();
							outToServer.println(sentence);
							break;

						case "view users":
							sentence = inFromUser.readLine();
							outToServer.println(sentence);
							break;

						case "view groups":
							sentence = inFromUser.readLine();
							outToServer.println(sentence);
							break;

						case "join group":
							sentence = inFromUser.readLine();
							outToServer.println(sentence);
							break;

						case "message group":
							sentence = inFromUser.readLine();
							outToServer.println(sentence);
							break;
							
						case "exit group":
							sentence = inFromUser.readLine();
							outToServer.println(sentence);
							break;

						case "delete group":
							sentence = inFromUser.readLine();
							outToServer.println(sentence);
							break;
						case "bye":
							sentence = inFromUser.readLine();
							outToServer.println(sentence);
							flag = true;
							break;

						default:
							break;
					}

					System.out.println("Answer from server: " + inFromServer.readLine());

					if(flag)
						break;
				}
				
				System.out.println("bye bye");
				input.close();
                // Close all of our connections.
                outToServer.close();
                inFromServer.close();
                clientSocket.close();

            } catch (IOException e) {
				System.out.println("I/O to socket failed: " + host);
            }
	}  /* End Connect Method */

	public static void main( String[] args )
	{
		int port;
		port = Integer.parseInt(args[1]);
		Client client = new Client();
		client.connect(args[0], port);
     }
}