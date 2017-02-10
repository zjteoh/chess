package chess;

import java.awt.Image;
import java.io.Serializable;
import java.net.URL;

import javax.swing.ImageIcon;

public class Piece
{
	private Image img;
	int x,y,color,row,col;
	String type ;
	
	public Piece(Image img,int x,int y,int color,String type,int row,int col)
	{
		this.img = img; this.x = x; this.y = y; this.color = color; this.type = type; this.row = row; this.col = col;
	}
	
	public Piece (int x,int y,String type,int row,int col,int color)
	{
		this.img = null;
		this.x  = x;
		this.y = y;
		this.type = type;
		this.row = row;
		this.col = col;
		this.color = color;
	}
	
	public Image getImage()
	{
		return img;
	}
	
	public void setImage(Image img)
	{
		this.img = img;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public void setCol(int col)
	{
		this.col = col;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
}
