package chess;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chess extends JPanel
{
	final int X_DEFAULT = 6; final int Y_DEFAULT = 6; final int X_OFFSET = 50; final int Y_OFFSET = 50;
	
	private Image background;
	private Piece[][] pieces = new Piece[8][8];
	Player player;
	int color;
	String title = "Chess";
	JFrame f;
	private JTextField textField;
	private JTextArea textArea;


	
	public Chess(Player player,int color)
	{
		this.color = color;
		this.player = player;
		URL urlBackgroundImg = getClass().getResource("/images/board.png");
		this.background = new ImageIcon(urlBackgroundImg).getImage();
		
		// create frame and set visible
		f = new JFrame();
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(424, 358, 256, 48);
		add(textField);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(424, 0, 256, 347);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(424, 0, 256, 347);
		add(scrollPane);
		f.setResizable(false);
		f.setSize(690, 438);
		
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(this);
		f.setResizable(false);
		f.setSize(690, 438);
				
		//add pieces
		for (int i = 0;i<8;i++)
		{
			addPieceToPieces("wp",X_OFFSET*i+X_DEFAULT,Y_OFFSET*6+Y_DEFAULT,i,6);
			addPieceToPieces("bp",X_OFFSET*i+X_DEFAULT,Y_OFFSET*1+Y_DEFAULT,i,1);	
		}
		
		for (int i = 0;i<8;i++)
			for(int j = 2; j<6;j++)
			{
				addPieceToPieces(null,X_OFFSET*i+X_DEFAULT,Y_OFFSET*j+Y_DEFAULT,i,j);
			}
		
		//add pieces
		addPieceToPieces("br",X_OFFSET*0+X_DEFAULT,Y_OFFSET*0+Y_DEFAULT,0,0);
		addPieceToPieces("bn",X_OFFSET*1+X_DEFAULT,Y_OFFSET*0+Y_DEFAULT,1,0);
		addPieceToPieces("bb",X_OFFSET*2+X_DEFAULT,Y_OFFSET*0+Y_DEFAULT,2,0);
		addPieceToPieces("bk",X_OFFSET*3+X_DEFAULT,Y_OFFSET*0+Y_DEFAULT,3,0);
		addPieceToPieces("bq",X_OFFSET*4+X_DEFAULT,Y_OFFSET*0+Y_DEFAULT,4,0);
		addPieceToPieces("bb",X_OFFSET*5+X_DEFAULT,Y_OFFSET*0+Y_DEFAULT,5,0);
		addPieceToPieces("bn",X_OFFSET*6+X_DEFAULT,Y_OFFSET*0+Y_DEFAULT,6,0);
		addPieceToPieces("br",X_OFFSET*7+X_DEFAULT,Y_OFFSET*0+Y_DEFAULT,7,0);
		
		addPieceToPieces("wr",X_OFFSET*0+X_DEFAULT,Y_OFFSET*7+Y_DEFAULT,0,7);
		addPieceToPieces("wn",X_OFFSET*1+X_DEFAULT,Y_OFFSET*7+Y_DEFAULT,1,7);
		addPieceToPieces("wb",X_OFFSET*2+X_DEFAULT,Y_OFFSET*7+Y_DEFAULT,2,7);
		addPieceToPieces("wq",X_OFFSET*3+X_DEFAULT,Y_OFFSET*7+Y_DEFAULT,3,7);
		addPieceToPieces("wk",X_OFFSET*4+X_DEFAULT,Y_OFFSET*7+Y_DEFAULT,4,7);
		addPieceToPieces("wb",X_OFFSET*5+X_DEFAULT,Y_OFFSET*7+Y_DEFAULT,5,7);
		addPieceToPieces("wn",X_OFFSET*6+X_DEFAULT,Y_OFFSET*7+Y_DEFAULT,6,7);
		addPieceToPieces("wr",X_OFFSET*7+X_DEFAULT,Y_OFFSET*7+Y_DEFAULT,7,7);	
	
	
	
		MouseListeners listener = new MouseListeners(this.pieces,this,player);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
		textField.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		       String msg = textField.getText();
		       textArea.append(textField.getText() + "\n");
		       player.sendMessage(msg);
		       textField.setText("");		    
		    }
		});
	}
	
	public void appendTextArea(String message)
	{
		textArea.append(message+"\n");
	}
	
	private void addPieceToPieces(String filename,int x,int y,int row,int col)
	{		
		if(filename != null){
			URL urlPieceImg = getClass().getResource("/images/"+filename+".png");
			Image img = new ImageIcon(urlPieceImg).getImage();
			int type;
			if(filename.substring(0,1).equals("w")) {type = 0;}
			else {type = 1;}
			pieces[row][col] = new Piece(img,x,y,type,filename.substring(1,2),row,col);
		}		
	}
	
	@Override
	synchronized protected void paintComponent(Graphics g)
	{
		g.drawImage(this.background, 0, 0, null);
		
		for (int i =0;i<8;i++)
			for (int j=0;j<8;j++)
			{
				if (pieces[i][j] != null)
					g.drawImage(pieces[i][j].getImage(), pieces[i][j].getX(), pieces[i][j].getY(), null);
			}
	}
	
	
	public Piece[][] getPieces()
	{
		return pieces;
	}
	
	public void setPieces(Piece [][] pieces)
	{
		this.pieces = pieces;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public JFrame getFrame()
	{
		return f;
	}
	
	
	synchronized public boolean setOpponentMoves(int bRow,int bCol, int aRow, int aCol)
	{
		
		if((pieces[bRow][bCol] == pieces[aRow][aCol])) return false;
		else if(pieces[bRow][bCol] == null) return false;
		else
		{	
			pieces[aRow][aCol] = new Piece(pieces[bRow][bCol].getImage(), aRow*50 +6,aCol*50+6,  pieces[bRow][bCol].getColor(), pieces[bRow][bCol].getType(), aRow, aCol);
			pieces[bRow][bCol] =  null;
			repaint();
			return true;
		}

	}
}
