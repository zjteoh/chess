package chess;

import java.io.Serializable;

public class Data implements Serializable
{
	String message;
	int [] before;
	int [] after;
	
	public Data(String message,int[]before,int[]after)
	{
		this.message = message;
		this.before = before;
		this.after = after;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public int [] getBefore()
	{
		return before;
	}
	
	public int [] getAfter()
	{
		return after;
	}
}
