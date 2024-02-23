package be.romy.dice;

/*
 * Note: on aurait pu ne mettre que la base des valeurs unicode mais... la police de caractère ne
 * place pas toutes les valeurs l'une à la suite de l'autre dans l'ordre numérique, on a les valeurs
 * classée dans un ordre "alphabétiquement": 1 10 11 ... 2 20 21 ...
 */

public enum DiceType
{
	D2      ( 2, '\uf196',
			'\uf117', '\uf118' ),

	D4      ( 4, '\uf198',
			'\uf12d', '\uf12e', '\uf12f', '\uf130' ),

	D6      ( 6, '\uf193',
			'\uf131', '\uf132', '\uf133', '\uf134',
					'\uf135', '\uf136' ),

	D8      ( 8, '\uf19a',
			'\uf137', '\uf138', '\uf139', '\uf13a',
					'\uf13b', '\uf13c', '\uf13d', '\uf13e' ),

	D10     ( 10, '\uf194',
			'\uf101', '\uf103', '\uf104', '\uf105',
					'\uf106', '\uf107', '\uf108', '\uf109',
					'\uf10a', '\uf102' ),

	D12     ( 12, '\uf195',
			'\uf10b', '\uf10f', '\uf110', '\uf111',
					'\uf112', '\uf113', '\uf114', '\uf115',
					'\uf116', '\uf10c', '\uf10d', '\uf10e' ),

	D20     ( 20, '\uf197',
			'\uf119', '\uf124', '\uf126', '\uf127',
					'\uf128', '\uf129', '\uf12a', '\uf12b',
					'\uf12c', '\uf11a', '\uf11b', '\uf11c',
					'\uf11d', '\uf11e', '\uf11f', '\uf120',
					'\uf121', '\uf122', '\uf123', '\uf125' )
	;

	// ------------------------------------------------------------------------

	private final int faceCount;

	private final char noFaceUnicode;
	private final char [] facesUnicode;

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	/*private*/ DiceType( int faceCount, char noFaceUnicode, char ... facesUnicode )
	{
		this.faceCount = faceCount;
		this.noFaceUnicode = noFaceUnicode;
		this.facesUnicode = facesUnicode;
	}

	// ========================================================================
	// = Getters ==============================================================
	// ========================================================================

	public int getFaceCount()
	{
		return faceCount;
	}

	// ------------------------------------------------------------------------

	public char getNoFaceUnicode()
	{
		return noFaceUnicode;
	}

	public char getFaceUnicode( int face )
	{
		if( (face < 1) || (face > faceCount) )
		{
			throw new IllegalArgumentException( "Invalid face value " + face + " for a " + this );
		}

		return facesUnicode[ face-1 ];
	}

	public char getHighestFaceUnicode()
	{
		return facesUnicode[ faceCount - 1 ];
	}
}
