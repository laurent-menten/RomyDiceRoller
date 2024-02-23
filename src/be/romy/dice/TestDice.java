package be.romy.dice;

import be.romy.dice.ui.DiceLabel;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestDice
	extends JFrame
{
	private static final String frameTitle = "Romy's dice roller";

	private Dice dice;

	private final DiceLabel diceLabel;
	private final JComboBox<DiceType> diceTypeComboBox;

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public TestDice()
	{
		setTitle( frameTitle );

		setLayout( new BorderLayout() );

		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		addWindowListener( windowAdapter );

		// --------------------------------------------------------------------

		JLabel diceTypeLabel = new JLabel( "Dice type: " );

		diceTypeComboBox = new JComboBox<>( DiceType.values() );
		diceTypeComboBox.addActionListener( diceTypeComboBoxActionListener );

		JPanel diceTypePanel = new JPanel();
		diceTypePanel.add( diceTypeLabel );
		diceTypePanel.add( diceTypeComboBox );

		add( diceTypePanel, BorderLayout.NORTH );

		// --------------------------------------------------------------------

		diceLabel = new DiceLabel();
		diceLabel.addMouseListener( mouseAdapter );
		add( diceLabel, BorderLayout.CENTER );

		// --------------------------------------------------------------------

		diceTypeComboBox.setSelectedItem( DiceType.D6 );
	}

	private final MouseAdapter mouseAdapter = new MouseAdapter()
	{
		@Override
		public void mouseClicked( MouseEvent ev )
		{
			if( ev.getClickCount() == 2 )
			{
				dice.roll();
			}
		}
	};

	private final ActionListener diceTypeComboBoxActionListener = new ActionListener()
	{
		@Override
		public void actionPerformed( ActionEvent ev )
		{
			DiceType type = (DiceType) diceTypeComboBox.getSelectedItem();
			if( type != null )
			{
				dice = new Dice( type );
				diceLabel.setDice( dice );
			}
		}
	};

	private final WindowAdapter windowAdapter = new WindowAdapter()
	{
		@Override
		public void windowClosing( WindowEvent ev )
		{
			int rc = JOptionPane.showConfirmDialog(
				TestDice.this,
				"Are you sure to quit ?",
				frameTitle,
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);

			if( rc == JOptionPane.OK_OPTION )
			{
				TestDice.this.setVisible( false );
				TestDice.this.dispose();

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
				TestDice frame = new TestDice();
				frame.pack();
				frame.setLocationRelativeTo( null );
				frame.setVisible( true );
			}
		} );
	}
}
