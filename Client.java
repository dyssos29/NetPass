import java.io.*;
import java.net.*;

public class Client extends Thread
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
	private ChatGui gui;


	public Client(String host, String port, String alias, String password)
	{
		this.host = host;
		this.port = Integer.parseInt(port);
		this.alias = alias;
		this.password = password;
	}

	@Override
	public void run() {
		connect();
		messageHandler();
	}

	public void setGui(ChatGui gui)
	{
		this.gui = gui;
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
	}  /* End Connect Method */

	private void messageHandler()
	{
		try
		{
			outToServer.println("user:" + alias + ":" + password);

			System.out.println("Netpass says hello to: " + alias);

			/* Continue forever until user types 'bye' */
			while ( true )
			{
				startMessageReader();

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
	}

	private void startMessageReader()
	{
		Thread messageThread = new Thread(){
			@Override
			public void run() {
				try {
					while ((responseFromServer = inFromServer.readLine()) != null)
					{
						if (responseFromServer.charAt(0) == 'l')
						{
							String[] clients = responseFromServer.split(":")[1].split(",");
							gui.updateClients(clients);
						}
						System.out.println(responseFromServer);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		messageThread.start();
	}

	public void sendPrivateMessage(String recipient, String message)
	{
		userSentence = "p:" + recipient + ":" + message;
		outToServer.println(userSentence);
	}

	public static void main( String[] args )
	{
		Client client = new Client(args[0],args[1],args[2],"dyssos");
		client.start();
	}
}