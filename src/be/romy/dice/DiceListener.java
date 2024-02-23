package be.romy.dice;

import java.util.EventListener;

public interface DiceListener
	extends EventListener
{
	void onDiceRoll( DiceEvent ev );
}
