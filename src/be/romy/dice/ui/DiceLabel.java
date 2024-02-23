package be.romy.dice.ui;

import be.romy.dice.Dice;
import be.romy.dice.DiceEvent;
import be.romy.dice.DiceListener;
import be.romy.dice.DiceType;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

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
	}

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
	}

	// ========================================================================
	// = Display result =======================================================
	// ========================================================================

	@Override
	public void onDiceRoll( DiceEvent ev )
	{
		Thread t = new Thread( new DiceAnim( dice.getType(), dice.getFace() ) );
		t.start();
	}

	private class DiceAnim
		extends Thread
	{
		private final DiceType type;
		private final int result;

		public DiceAnim( DiceType type, int result )
		{
			this.type = type;
			this.result = result;
		}

		@Override
		public void run()
		{
			Dice tempDice = new Dice( type );

			for( int i = 0 ; i < 10 ; i++ )
			{
				tempDice.roll();
				SwingUtilities.invokeLater( () ->
					DiceLabel.this.setText( " " + tempDice.getFaceUnicode() + " " )
				);

				try
				{
					Thread.sleep( 50 );
				}
				catch( InterruptedException e )
				{
					// do nothing
				}
			}

			SwingUtilities.invokeLater( () ->
				setText( " " + type.getFaceUnicode( result ) + " " )
			);
		}
	}
}
