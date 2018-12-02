/*
	Filename: Grouphandler.java 
	Description: A thread server that will be able to handle clients in a group as a thread.
	The connection method that will be used is TCP (Transfer Connection Protocol)
*/

	/*
 * Filename: TCPEchoServer.java
 * Description: An echo server using connection-oriented delivery system (TCP).
 *              Receives character messages from clients which are capitalized
 *              and sent back. No error handling and exceptions are implemented.
 * Operation: java TCPEchoServer [port]
 *
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class GroupHandler implements Runnable
{
	protected ArrayList<Socket> membersList = new ArrayList<Socket>;

	public Grouphandler(ArrayList<Socket> group)
	{
		this.membersList = group;
	}

	public void run(  )
	{

	}
}