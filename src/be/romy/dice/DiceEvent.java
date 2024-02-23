package be.romy.dice;

import java.util.EventObject;

public class DiceEvent
	extends EventObject
{
	public DiceEvent( Dice dice )
	{
		super( dice );
	}
}
