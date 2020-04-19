import junit.framework.TestCase;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated,l1,l2,stick,square;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		l1 = new Piece(Piece.L1_STR);
		b.place(pyr1, 0, 0);
	}
	
	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	// Makre  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.
	public void testClearRows1(){
		//testing default pyramid clearing.
		b.clearRows();
		assertEquals(0,b.getColumnHeight(0));
		assertEquals(1,b.getMaxHeight());
		assertEquals(1,b.getColumnHeight(1));
		assertEquals(1,b.getRowWidth(0));
		assertEquals(0,b.getRowWidth(1));
		b.commit();
		//fill in with pyr4
		b.place(pyr4, 0, 0);
		b.clearRows();
		assertEquals(2, b.getColumnHeight(1));
        assertEquals(3, b.getMaxHeight());
        assertEquals(1, b.getRowWidth(2));
        assertEquals(0,b.getColumnHeight(2));
		
        Piece[] pieces = Piece.getPieces();
        l1 = pieces[Piece.L1];
        
		l1=l1.fastRotation();
		l1 = l1.fastRotation();
        b.commit();
        b.place(l1,1, b.dropHeight(l1,1));
        b.clearRows();
        assertEmpty();
	}
	//helper method to check if the board is empty.
	private void assertEmpty(){
		assertEquals(0, b.getColumnHeight(1));
        assertEquals(0, b.getMaxHeight());
        assertEquals(0, b.getRowWidth(0));
	}
	public void testClearRows2(){
		Piece[] p= Piece.getPieces();
        stick = p[Piece.STICK];
        square=p[Piece.SQUARE];
        b.commit();
		b.place(stick,2,0);
		b.clearRows();
		b.commit();
		b.place(square, 0, 0);
		b.commit();
		assertEquals(2,b.getColumnHeight(0));
		b.clearRows();
		assertEquals(2,b.getMaxHeight());
		b.commit();
		assertEquals(2,b.getColumnHeight(0));
		
	}

	public void testClearRows3(){
		Piece[] pieces = Piece.getPieces();
        l1 = pieces[Piece.L1];
        l1=l1.fastRotation();
        l1=l1.fastRotation();
        stick=pieces[Piece.STICK];
        b.commit();
		b.place(l1,1,0);
		b.clearRows();
		b.commit();
		b.place(stick, 2, 0);
		b.commit();
		//assertEquals(2,b.getColumnHeight(0));
		b.clearRows();
		assertEquals(2,b.getMaxHeight());
		b.commit();
		assertEquals(0,b.getColumnHeight(0));
		
	}
	public void testUndo(){
		b.undo();
		assertEquals(0, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(1));
	}
	
	public void testUndo1(){
		b.undo();
		assertEquals(0, b.getMaxHeight());
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(0,b.getColumnHeight(1));
		assertEquals(0,b.getColumnHeight(2));
	}
	//creating a 2x4 board for this one and placing two sticks.
	public void testUndo3(){
		b = new Board(2,4);
		b.commit();
		Piece[] p= Piece.getPieces();
        stick = p[Piece.STICK];
		b.place(stick, 0, 0);
		b.commit();
		b.place(stick, 1, 0);
		b.commit();
		b.clearRows();
		b.commit();
		assertEquals(0,b.getColumnHeight(0));
		assertEquals(0,b.getColumnHeight(1));
	}
	public void testDropHeight1(){
		b= new Board(3,6);
		b.commit();
		b.place(s, 0, 0);
		b.commit();
		stick = new Piece(Piece.STICK_STR);
		assertEquals(1, b.dropHeight(stick, 0));
	}
	public void testDropHeight2(){
		assertEquals(2,b.dropHeight(new Piece(Piece.STICK_STR), 1));
		assertEquals(2,b.getColumnHeight(1));
	}
	public void testDropHeight3(){
		b.undo();
		b.commit();
		sRotated = s.computeNextRotation();
		b.place(sRotated, 0, 0);
		b.commit();
		stick = new Piece(Piece.STICK_STR);
		assertEquals(3,b.dropHeight(stick, 0));
	}
	
}