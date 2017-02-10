package chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

public class MouseListeners implements MouseListener , MouseMotionListener
{
	final int X_DEFAULT = 6; final int Y_DEFAULT = 6; final int X_OFFSET = 50; final int Y_OFFSET = 50;
	
	Piece [][] pieces;
	int x,y;
	int defaultCoorX, defaultCoorY;
	Chess chess;
	Piece dragPiece;
	MoveValidator validator;
	Player player;
	
	public MouseListeners(Piece[][] pieces,Chess chess,Player player)
	{
		this.pieces = pieces;
		this.chess = chess;
		this.player = player;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent event) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	synchronized public void mousePressed(MouseEvent event) {
			int coorX = event.getX();
			int coorY = event.getY();
					
			for (int i = 0; i<8 ;i++)
				for (int j = 0;j<8 ;j++)
				{
					if(pieces[i][j] != null ) //&& pieces[i][j].getColor() == chess.getColor()
						if(coorX >= (i*50 +6) && coorX < (i*50 +6)+X_OFFSET && coorY >= (j*50 +6) && coorY < (j*50 +6)+Y_OFFSET)
							{
								x=i ; y=j;
								defaultCoorX = (i*50 +6);
								defaultCoorY = (j*50 +6);
								dragPiece = pieces[i][j];
							}		
				}
	}

	@Override
	synchronized public void mouseReleased(MouseEvent event) 
	{
		dragPiece.setX(defaultCoorX);
		dragPiece.setY(defaultCoorY);
		
		boolean valid;
		validator = new MoveValidator(dragPiece,pieces);
		
		int releasedX = 0 , releasedY = 0;
		
		int coorX = event.getX();
		int coorY = event.getY();		
		
		int ratioX = coorX/50;
		int ratioY = coorY/50;

		int choosenX = ratioX*50 + 6;
		int choosenY = ratioY*50 + 6;
				
		Piece choosenPiece = null;
		
		for (int i = 0; i<8 ;i++)
			for (int j = 0;j<8 ;j++)
			{
				if(choosenX == (i*50 +6) && choosenY == (j*50 +6))
				{
					releasedX = i; releasedY = j;
					if(pieces[i][j] != null)
					{
						choosenPiece = pieces[i][j];
						break;
					}
				}
			}
		if((dragPiece.getColor() == chess.getColor()) )
		{
			if(choosenPiece == null)
			{		
				valid = validator.testMove(releasedX,releasedY);	
					if(valid)
					{						
						int [] before = {x, y};
						int [] after = {releasedX, releasedY};
	
						dragPiece.setRow(releasedX);
						dragPiece.setCol(releasedY);
						pieces[releasedX][releasedY] = dragPiece;
						dragPiece.setX(choosenX);
						dragPiece.setY(choosenY);
						pieces[x][y] = null;

	
						player.setNums(before, after);
						chess.setColor(4);

						
					}
					
				}
			else if (choosenPiece!=null)
			{
				if (choosenPiece.getColor() == dragPiece.getColor())
				{
					//dragPiece.setX(defaultCoorX);
					//dragPiece.setY(defaultCoorY);
				}
				else if (choosenPiece.getColor() != dragPiece.getColor())
				{	
					if(dragPiece.getType().equals("p"))
						valid = validator.pawnAttack(releasedX,releasedY);
					else
						valid = validator.testMove(releasedX,releasedY);
						
					if(valid)
					{
						int [] before = {x, y};
						int [] after = {releasedX, releasedY};
						
						dragPiece.setRow(releasedX);
						dragPiece.setCol(releasedY);
						pieces[releasedX][releasedY] = dragPiece;
						dragPiece.setX(choosenX);
						dragPiece.setY(choosenY);	
						pieces[x][y] = null;

						
						/*pieces[releasedX][releasedY] = new Piece(pieces[x][y].getImage(), releasedX*50 +6,releasedY*50+6, chess.getColor(), pieces[x][y].getType(), releasedX, releasedY);
						
						pieces[x][y] =  new Piece (x*50+6,y*50+6,"x",x,y,3);*/
						
						player.setNums(before, after);	
						chess.setColor(4);

					}
				}
			}
		}

	 	//dragPiece = null;
		chess.repaint();
	}


	@Override
	public void mouseDragged(MouseEvent event) 
	{
		if(dragPiece != null)
		{
			dragPiece.setX(event.getX()-X_OFFSET/2);
			dragPiece.setY(event.getY()-Y_OFFSET/2);
		 	chess.repaint();
		}

	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
