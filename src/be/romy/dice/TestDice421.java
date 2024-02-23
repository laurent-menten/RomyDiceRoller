package be.romy.dice;

import be.romy.dice.ui.DiceLabel;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class TestDice421
	extends JFrame
{
	private static final String frameTitle = "Romy's 421";

	private final JLabel messager;

	private Dice dice1 = new Dice( DiceType.D6 );
	private Dice dice2 = new Dice( DiceType.D6 );
	private Dice dice3 = new Dice( DiceType.D6 );

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public TestDice421()
	{
		setTitle( frameTitle );
		setResizable( false );

		setLayout( new BorderLayout() );

		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		addWindowListener( windowAdapter );

		// --------------------------------------------------------------------

		JPanel panel421 = new JPanel();
		panel421.setLayout( new FlowLayout() );

		DiceLabel diceLabel1 = new DiceLabel();
		diceLabel1.setDice( dice1 );
		diceLabel1.addMouseListener( mouseAdapter );
		panel421.add( diceLabel1 );

		DiceLabel diceLabel2 = new DiceLabel();
		diceLabel2.setDice( dice2 );
		diceLabel2.addMouseListener( mouseAdapter );
		panel421.add( diceLabel2 );

		DiceLabel diceLabel3 = new DiceLabel();
		diceLabel3.setDice( dice3 );
		diceLabel1.addMouseListener( mouseAdapter );
		panel421.add( diceLabel3 );

		add( panel421, BorderLayout.CENTER );

		// --------------------------------------------------------------------

		messager = new JLabel();
		messager.setHorizontalAlignment( JLabel.CENTER );

		Font font = messager.getFont();
		Font newFont = font.deriveFont( font.getSize() * 2f );
		messager.setFont( newFont );

		add( messager, BorderLayout.SOUTH );
	}

	private final MouseAdapter mouseAdapter = new MouseAdapter()
	{
		@Override
		public void mouseClicked( MouseEvent ev )
		{
			if( ev.getClickCount() == 2 )
			{
				int [] results = new int[ 3 ];

				results[0] = dice1.roll();
				results[1] = dice2.roll();
				results[2] = dice3.roll();

				Arrays.sort( results );
				if( (results[2] == 4) && (results[1] == 2) && (results[0] == 1) )
				{
					messager.setForeground( Color.RED );
					messager.setText( "You win !!!" );
				}
				else
				{
					messager.setForeground( Color.BLACK );
					messager.setText( "You loose" );
				}
			}
		}
	};

	private final WindowAdapter windowAdapter = new WindowAdapter()
	{
		@Override
		public void windowClosing( WindowEvent ev )
		{
			int rc = JOptionPane.showConfirmDialog(
				TestDice421.this,
				"Are you sure to quit ?",
				frameTitle,
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);

			if( rc == JOptionPane.OK_OPTION )
			{
				TestDice421.this.setVisible( false );
				TestDice421.this.dispose();

				System.exit( 0 );
			}
		}
	};

	// ========================================================================
	// = Entrypoint ===========================================================
	// ========================================================================

	public static void main( String[] args )
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{
				TestDice421 frame = new TestDice421();
				frame.pack();
				frame.setLocationRelativeTo( null );
				frame.setVisible( true );
			}
		} );
	}
}
