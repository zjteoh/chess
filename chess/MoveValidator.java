package chess;

import java.io.Serializable;

public class MoveValidator 
{
	String type ;
	Piece dragPiece;
	Piece [][] pieces;
	
	public MoveValidator(Piece dragPiece,Piece[][] pieces)
	{
		this.dragPiece = dragPiece;
		this.pieces = pieces;
	}

	
	public boolean testMove(int x, int y)
	{
		boolean valid = false;
		type = dragPiece.getType();
		switch(type)
		{
			case "p" : 
				{				
					if ((( y - dragPiece.getCol() == 1 )) && (dragPiece.getRow() - x == 0) && dragPiece.getColor() == 1)
					{
						valid = true;
					}
					else if ((( y - dragPiece.getCol() == -1 )) && (dragPiece.getRow() - x == 0) && dragPiece.getColor() == 0)
					{
						valid = true;
					}
					else if ((dragPiece.getCol() - y == 0) && (dragPiece.getRow() == x))
					{
						valid = true;
					}
				}
				break;
			case "n" :
				{
					if(          (       (x - dragPiece.getRow() == 1)||(x - dragPiece.getRow() == -1)          )&&((y - dragPiece.getCol() == 2)||(y - dragPiece.getCol() == -2))            )
						valid = true;
					else if (((x - dragPiece.getRow() == 2)||(x - dragPiece.getRow() == -2))&&((y - dragPiece.getCol() == 1||(y - dragPiece.getCol() == -1))))
							valid = true;
				}
				break;
			case "r" :
			{				
				if((x - dragPiece.getRow() == 0) )
				{					
					return moveStraightCol(x,y);
				}
				else if((y - dragPiece.getCol() == 0) )
				{					
					return moveStraightRow(x,y);
				}
				else
					return false;
			}
			case "b" :
			{
				if(Math.abs(dragPiece.getRow() - x) == Math.abs(dragPiece.getCol() - y))
					 moveDiagonal(x,y);
				else
					return false;
			}
			case "q" :
			{
				if((x - dragPiece.getRow() == 0) )
				{					
					return moveStraightCol(x,y);
				}
				else if((y - dragPiece.getCol() == 0) )
				{					
					return moveStraightRow(x,y);
				}
				else
				{

					return moveDiagonal(x,y);
				}
			}
			case "k" :
			{
				if(  ( Math.abs(dragPiece.getCol() - y) == 1 ) ||( Math.abs(dragPiece.getRow() - x) == 1 )  )
					return true;
				else return false;
			}
				
		}
		return valid;
	}
	
	public boolean moveDiagonal(int x,int y)
	{
		boolean valid = false;
				
		if((Math.abs(dragPiece.getRow()+dragPiece.getCol()) == Math.abs(x+y)))
		{
			int totalEmpty = Math.abs(dragPiece.getRow() - x);
			int totalEmptyLoop = totalEmpty;
			
			if(dragPiece.getRow() > x)
				for (int i = 1;i<=totalEmptyLoop;i++)
				{
					if(i==totalEmptyLoop) return true;
					
					if(pieces[dragPiece.getRow()-i][dragPiece.getCol()+i] == null)
						totalEmpty--;
				}
			else if (dragPiece.getRow() < x)
			{
				for (int i = 1;i<=totalEmptyLoop;i++)
				{
					if(i==totalEmptyLoop) return true;

					if(pieces[dragPiece.getRow()+i][dragPiece.getCol()-i] == null)
						totalEmpty--;
				}
			}
			
			if(totalEmpty == 0)
				valid = true;
			
			return valid;
		}
		else if (Math.abs(dragPiece.getRow()-dragPiece.getCol()) == Math.abs(x-y))
		{
			int totalEmpty = Math.abs(dragPiece.getRow() - x);
			int totalEmptyLoop = totalEmpty;
			
			if(dragPiece.getRow() > x)
				for (int i = 1;i<=totalEmptyLoop;i++)
				{
					if(i==totalEmptyLoop) return true;
					
					if(pieces[dragPiece.getRow()-i][dragPiece.getCol()-i] == null)
						totalEmpty--;
				}
			else if (dragPiece.getRow() < x)
			{
				for (int i = 1;i<=totalEmptyLoop;i++)
				{
					if(i==totalEmptyLoop) return true;

					if(pieces[dragPiece.getRow()+i][dragPiece.getCol()+i] == null)
						totalEmpty--;
				}
			}
			
			if(totalEmpty == 0)
				valid = true;
			
			return valid;
		}
		else
		{
			return false;
		}
	}
	
	public boolean moveStraightCol(int x,int y)
	{
		boolean valid = false;
		
		if((Math.abs(y - dragPiece.getCol()) != 1))
		{
			int totalEmpty = Math.abs(y - dragPiece.getCol())-1;
			
			if(dragPiece.getCol() < y)
				for(int i = dragPiece.getCol()+1;i<=y-1;i++)
				{
					if(pieces[x][i] == null)
						totalEmpty--;
					
				}
			
			if(dragPiece.getCol()>y)
				for(int i = y+1;i<=dragPiece.getCol()-1;i++)
				{
					if(pieces[x][i] == null)
						totalEmpty--;
					
				}
			
			if(totalEmpty == 0) valid = true;
		}
		else if (Math.abs(y - dragPiece.getCol()) == 1)
		{
			valid = true;
		}
		return valid;
	}
	
	
	public boolean moveStraightRow(int x,int y)
	{
		boolean valid = false;
		
		if((Math.abs(x - dragPiece.getRow()) != 1))
		{
			int totalEmpty = Math.abs(x - dragPiece.getRow())-1;
			
			if(dragPiece.getRow() < x)
				for(int i = dragPiece.getRow()+1;i<=x-1;i++)
				{
					if(pieces[i][y] == null)
						totalEmpty--;
					
				}
			if(dragPiece.getRow() > x)
				for(int i = x+1;i<=dragPiece.getRow()-1;i++)
				{
					if(pieces[i][y] == null)
						totalEmpty--;
					
				}
			
			if(totalEmpty == 0) valid = true;
		}
		else if (Math.abs(x - dragPiece.getRow()) == 1)
		{
			valid = true;
		}
		return valid;
	}
	
	public boolean pawnAttack(int x,int y)
	{
		boolean valid = false; 
		if(          (dragPiece.getCol()-y == 1) && (Math.abs(dragPiece.getRow() - x) == 1) && (dragPiece.getColor() == 0 )              )
			valid = true;
		else if ((dragPiece.getCol()-y == -1) && (Math.abs(dragPiece.getRow() - x) == 1)&& dragPiece.getColor() == 1)
			valid = true;
		
		return valid;	
	}



	
}
