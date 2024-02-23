package be.romy.dice.ui;

import be.romy.dice.Dice;
import be.romy.dice.DiceEvent;
import be.romy.dice.DiceListener;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DiceLabel
	extends JLabel
	implements DiceListener
{
	private Dice dice;

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public DiceLabel()
	{
		setFont( Dice.getDiceFont().deriveFont( 120f ) );
		setText( "   " );

		addMouseListener( mouseAdapter );
	}

	private final MouseAdapter mouseAdapter = new MouseAdapter()
	{
		@Override
		public void mouseClicked( MouseEvent ev )
		{
			if( ev.getClickCount() == 2 )
			{
				if( dice!= null )
				{
					dice.roll();
				}
			}
		}
	};

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public void setDice( Dice dice )
	{
		if( this.dice != null )
		{
			this.dice.removeDiceListener( this );
		}

		this.dice = dice;
		this.dice.addDiceListener( this );

		setText( " "  +  this.dice.getType().getNoFaceUnicode() + " " );
		validate();
	}

	@Override
	public void onDiceRoll( DiceEvent ev )
	{
		setText( " " + dice.getFaceUnicode() + " " );
		validate();
	}
}
