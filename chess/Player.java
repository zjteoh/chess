package chess;


public abstract class Player 
{
    public abstract void setNums(int [] before , int [] after);
    
    public abstract void sendMoves(int [] before, int [] after);
    
    public abstract void sendMessage(String message);

}
