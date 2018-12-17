import java.io.*;
import java.net.*;

public class Client
{
	/* Our socket end */
	private Socket clientSocket;
	/* For writing to socket */
	private PrintStream outToServer;
	// For reading from socket */
	private BufferedReader inFromServer;
	/* For reading from user */
	private BufferedReader inFromUser;
	private String userSentence, responseFromServer;
	private String host;
	private int port;
	private String alias;
	private String password;


	public Client(String host, String port, String alias, String password)
	{
		this.host = host;
		this.port = Integer.parseInt(port);
		this.alias = alias;
		this.password = password;
	}

	public void connect()
	{
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
			System.err.println("Don't know about host: hostname");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: hostname");
		}

		System.out.println("<-- Connection established  -->");

		try
		{
			outToServer.println("user:" + alias + ":" + password);

			System.out.println("Netpass says hello to: " + alias);
			System.out.println("message:username:message --> message a specific user");
			System.out.println("view users --> view all the users in the server");
			System.out.println("view groups --> view all the groups in the server");
			System.out.println("join group --> join a group");
			System.out.println("exit group --> exit from a group");
			System.out.println("delete group --> delete a group (only works if you are the creator of the group)");
			System.out.println("bye --> exit from the server");

			/* Continue forever until user types 'bye' */
			while ( true )
			{
				startMessageHandler();

				userSentence = inFromUser.readLine();

				if (!userSentence.equals("bye"))
					outToServer.println(userSentence);
				else
				{
					outToServer.println(userSentence);
					break;
				}
			}

			System.out.println("bye bye");

			// Close all of our connections.
			outToServer.close();
			inFromServer.close();
			clientSocket.close();

		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
	}  /* End Connect Method */

	private void startMessageHandler()
	{
		Thread messageThread = new Thread(){
			@Override
			public void run() {
				try {
					while ((responseFromServer = inFromServer.readLine()) != null)
						System.out.println(responseFromServer);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		messageThread.start();
	}

	public static void main( String[] args )
	{
		Client client = new Client(args[0],args[1],args[2],"dyssos");
		client.connect();
	}
}