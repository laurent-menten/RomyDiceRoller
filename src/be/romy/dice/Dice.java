package be.romy.dice;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Dice
{
	private static final Font diceFont;
	static
	{
		try( InputStream is = Dice.class.getResourceAsStream( "font/dicefont.ttf" ) )
		{
			if( is == null )
			{
				throw new RuntimeException( "Font resource not found." );
			}

			diceFont = Font.createFont( Font.TRUETYPE_FONT, is );
		}
		catch( IOException | FontFormatException ex )
		{
			throw new RuntimeException( ex );
		}
	}

	// ------------------------------------------------------------------------

	private final List<DiceListener> listeners
		= new ArrayList<>();

	// ------------------------------------------------------------------------

	private final DiceType type;
	private final SecureRandom random;

	private int face;

	// ========================================================================
	// = Constructors =========================================================
	// ========================================================================


	public Dice()
	{
		this( DiceType.D6 );
	}

	public Dice( DiceType type )
	{
		this.type = type;
		this.face = type.getFaceCount();

		this.random = new SecureRandom();
	}

	// ========================================================================

	public synchronized void addDiceListener( DiceListener listener )
	{
		if( !listeners.contains( listener ) )
		{
			listeners.add( listener );
		}
	}

	public synchronized void removeDiceListener( DiceListener listener )
	{
		if( listeners.contains( listener ) )
		{
			listeners.remove( listener );
		}
	}

	protected void fireDiceEvent( DiceEvent ev )
	{
		DiceListener [] threadSafeListenerslist;
		synchronized( this )
		{
			threadSafeListenerslist = listeners.toArray( new DiceListener[0] );
		}

		for( DiceListener listener : threadSafeListenerslist )
		{
			listener.onDiceRoll( ev );
		}
	}

	// ========================================================================

	public static Font getDiceFont()
	{
		return diceFont;
	}

	// ------------------------------------------------------------------------

	public DiceType getType()
	{
		return type;
	}

	public int getFace()
	{
		return face;
	}

	public char getFaceUnicode()
	{
		return type.getFaceUnicode( face );
	}

	// ========================================================================

	public int roll()
	{
		face = random.nextInt( 1, type.getFaceCount() + 1 ) ;

		DiceEvent ev = new DiceEvent( this );
		fireDiceEvent( ev );

		return face;
	}
}
