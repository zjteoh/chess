package chess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame
{
	public static void main (String [] args)
	{
		JFrame frame = new JFrame("Main");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Object[] possibilities = {"Server","Client"};
		String s = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "Start Server or Client?",
		                    "Main Panel",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities,
		                    "Server");

		//If a string was returned, say so.
		if (s.equals("Server"))
		{
			System.out.println("Starting chess server");
			try 
			{
				ServerSocket ss = new ServerSocket(4444);
				while(true)
				{
					System.out.println("Waiting for client connection");
					Socket socket = ss.accept();
					System.out.println("Client connected");
					Server server = new Server(socket,0);
					server.startServer();
					break;
				}
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (s.equals("Client"))
		{
			String ipAddress = (String)JOptionPane.showInputDialog("Please enter IP address. Port is defaulted at 4444");
			Client client = new Client(ipAddress,4444,1);
			client.startClient();		
			
		}
		
	}
}
