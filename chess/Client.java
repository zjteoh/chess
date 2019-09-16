package chess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Client extends Player
{
    private String serverIPAddress;
    private int serverPort;
    int []before = null ;
    int []after = null;
    int bRow ;
    int bCol ;
    int aRow ;
    int aCol ;
    Chess chess;
    int color;
	Socket socket;
	ObjectInputStream is;
	ObjectOutputStream os;
	boolean moveTurn;
	String message;
   
    
    public Client(String ipAddress, int port,int color) 
    {
        serverIPAddress = ipAddress;
        serverPort = port; 
        this.color = color;
    } 
    
    public void setNums(int [] before , int [] after)
    {
    	moveTurn = true;
    	this.before = before;
    	this.after = after;
    	sendMoves(before,after);
    }
    
    public void sendMoves(int [] before, int [] after)
    {

    		try 
    		{
    			Data data = new Data(null,before,after);
    			os.writeObject(data);

			} catch (IOException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    
    	
    }
    
    public void startClient()
    {
    	chess = new Chess(this,1);
    	try 
    	{
			socket = new Socket(serverIPAddress,serverPort);
			System.out.println("Client started \n__________________");
	        is = new ObjectInputStream(socket.getInputStream());
	        os = new ObjectOutputStream(socket.getOutputStream());		
	        new MoveListener().start();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    class MoveListener
    {
    	@Override
    	public void run()
    	{
    		while(true)
    		{
    			try
    			{				
    				Data data = (Data)is.readObject();
    				if(data.getMessage() != null)
    				{
    					System.out.println(data.getMessage());
    					appendTextArea(data.getMessage());
    				}
    				else

    				{
	    				before = data.getBefore();
	    				after = data.getAfter();
	    				bRow = before[0];
	    				bCol = before[1];
	    				aRow = after[0];
	    				aCol = after[1];
	    				
	    				
	    				boolean turn =chess.setOpponentMoves(bRow, bCol, aRow, aCol);
	    				chess.setColor(color);
	    				JOptionPane.showMessageDialog(chess, "Your Turn");
    				}
    			}
    			catch(IOException e)
    			{
    				
    			} 
    			catch (ClassNotFoundException e) 
    			{

				}
    		}
    	}
    }
    public void appendTextArea(String message)
    {
    	chess.appendTextArea(message);
    }
    
	@Override
	public void sendMessage(String msg) 
	{
    	Data data = new Data(msg,null,null);
    	try {
			os.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
