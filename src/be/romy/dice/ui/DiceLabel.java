package be.romy.dice.ui;

import be.romy.dice.Dice;
import be.romy.dice.DiceEvent;
import be.romy.dice.DiceListener;
import be.romy.dice.DiceType;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
		this( 240f );
	}

	public DiceLabel( float initalFontSize )
	{
		setHorizontalAlignment( JLabel.CENTER );

		addComponentListener( componentAdapter );

		setFont( Dice.getDiceFont().deriveFont( initalFontSize ) );
	}

	// ------------------------------------------------------------------------

	// Note: ne marche pas avec certain Layout qui ne redimentionnent pas par
	// défault les composants internes... trouver une solution...

	private final ComponentAdapter componentAdapter = new ComponentAdapter()
	{
		@Override
		public void componentResized( ComponentEvent e )
		{
			float size = (float) Math.min( getWidth(), getHeight() );

			setFont( Dice.getDiceFont().deriveFont( size ) );
		}
	};

	// ========================================================================
	// = Setter ===============================================================
	// ========================================================================

	public void setDice( Dice dice )
	{
		if( this.dice != null )
		{
			this.dice.removeDiceListener( this );
		}

		this.dice = dice;
		this.dice.addDiceListener( this );

		setText( Character.toString(  this.dice.getType().getNoFaceUnicode() ) );
	}

	// ========================================================================
	// = Display result =======================================================
	// ========================================================================

	@Override
	public void onDiceRoll( DiceEvent ev )
	{
		Thread t = new DiceAnim( dice.getType(), dice.getFace() );
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
					DiceLabel.this.setText( Character.toString( tempDice.getFaceUnicode() ) )
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
				setText( Character.toString( type.getFaceUnicode( result ) ) )
			);
		}
	}

/*
    // pour debug, bords et diagonales en rouge.

	@Override
	public void paint( Graphics g )
	{
		int w = getWidth() - 1;
		int h = getHeight() - 1;

		g.setColor( Color.RED );
		g.drawRect( 0, 0, w, h );
		g.drawLine( 0, 0, w, h );
		g.drawLine( 0, h, w, 0 );

		super.paint( g );
	}
*/
}
