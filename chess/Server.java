package chess;


import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Server extends Player
{	
    private Socket socket;
    int []before ;
    int []after ;
    int bRow ;
    int bCol ;
    int aRow ;
    int aCol ;
    Chess chess;
    ObjectOutputStream os;
    ObjectInputStream is;
    int color;
	private Scanner input;

     
    public Server(Socket socket,int color) 
    {
        this.socket = socket;
        this.color = color;
    }
	
    public void setNums(int [] before , int [] after)
    {
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
				/*os.writeObject(before);
	    		os.writeObject(after);*/

			} catch (IOException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    
    	
    }
    
    
    public void startServer()
    {
    	chess = new Chess(this,0);
    	try 
    	{
			os = new ObjectOutputStream(socket.getOutputStream());
	    	is = new ObjectInputStream(socket.getInputStream());

	        Thread thread2 = new Thread(new MoveListener());
	        thread2.start();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	
    }

    
    class MoveListener implements Runnable
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
		/*this.message = message;
		System.out.println(this.message);*/
		
    	Data data = new Data(msg,null,null);
    	try {
			os.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

