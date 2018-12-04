/*
 * Filename: TCPEchoServer.java
 * Description: An echo server using connection-oriented delivery system (TCP).
 *              Receives character messages from clients which are capitalized
 *              and sent back. No error handling and exceptions are implemented.
 * Operation: java TCPEchoServer [port]
 *
 */

import java.io.*;
import java.net.*;

public class MuTCPEchoServer
{
    /* This is the port on which the server is running */
    private int serverPort;
	
	/* Constructor Method */
	public MuTCPEchoServer( int port )
	{
		serverPort = port;
	}  /* End Contrucotr Method */

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
		new Thread(new ConnectionHandler(clientConnectionSocket)).start();
	}  /* End handleClient method */

	public static void main( String argv[] ) throws Exception
	{ 
		/* The port the server is listening on */
		int port;
		
			/* Parse the port which is passed to program as an arguement */
			port = Integer.parseInt( argv[0] );
	        /* Create a new instance of the echo server */
	        MuTCPEchoServer myServer = new MuTCPEchoServer( port );
			/* Listen for connections. It can not return until the server is shut down. */
			myServer.listen();
			/* Display message of server shutting down */
			System.out.println("<-- Server exiting -->");
	}   /* End main method */

}   /* End class MuTCPEchoServer */



/*
 * Example:
 *   java TCPEchoServer 4567
 * Output:
 *	 Server waiting at port 4567
 *	 Accepted connection from: machineName/IPaddress
 *   Message received: Hallo server
 */