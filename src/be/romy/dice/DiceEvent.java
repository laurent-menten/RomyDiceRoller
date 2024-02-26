package be.romy.dice;

import java.util.EventObject;

public class DiceEvent
	extends EventObject
{
	private final int oldFace;
	private final int newFace;

	public DiceEvent( Dice dice, int oldFace, int newFace )
	{
		super( dice );

		this.oldFace = oldFace;
		this.newFace = newFace;
	}

	public int getOldFace()
	{
		return oldFace;
	}

	public int getNewFace()
	{
		return newFace;
	}
}
