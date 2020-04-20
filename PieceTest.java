
import junit.framework.TestCase;

import java.util.*;
/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	
	// every last rotation that is the same as the 
	//first initial piece is indexed as 5. 
	private Piece pyr1, pyr2, pyr3, pyr4, pyr5;
	private Piece s, sRotated, s5;  //s5=s
	private Piece s2, s2Rotated, s25;  //s25=s2
	private Piece l1, l12, l13, l14,l15; //l15=l1
	private Piece l2, l22,l23,l24,l25; //l25=l2
	private Piece square, squareRotated;
	private Piece stick, stickRotated, stick5;// stick5=stick but visually

	protected void setUp() throws Exception {
		super.setUp();
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		pyr5 = pyr4.computeNextRotation();
		//
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		s5=sRotated.computeNextRotation();
		//
		s2= new Piece(Piece.S2_STR);
		s2Rotated=s2.computeNextRotation();
		s25=s2Rotated.computeNextRotation();
		
		// to read better: l1=L.1 = first L, L.1.2 = first L once rotated...
		l1=new Piece(Piece.L1_STR);
		l12 = l1.computeNextRotation();
		l13= l12.computeNextRotation();
		l14=l13.computeNextRotation();
		l15=l14.computeNextRotation();
		
		// same for l2 (mirrored L1)
		l2= new Piece(Piece.L2_STR);
		l22 = l2.computeNextRotation();
		l23= l22.computeNextRotation();
		l24=l23.computeNextRotation();
		l25=l24.computeNextRotation();
	
		//square tests
		square = new Piece(Piece.SQUARE_STR);
		squareRotated= square.computeNextRotation();
		
		//stick tests
		stick = new Piece(Piece.STICK_STR);
		stickRotated= stick.computeNextRotation();
		stick5=stickRotated.computeNextRotation();
	}
	
	// Here are some sample tests to get you started
	
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		assertEquals(3,pyr5.getWidth());
		assertEquals(2,pyr5.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
		
		assertEquals(4,stickRotated.getWidth());
		assertEquals(1, stickRotated.getHeight());
		
		assertEquals(1, stick5.getWidth());
		assertEquals(4,stick5.getHeight());
		
		//TESTS L1.
		
		assertEquals(2, l1.getWidth());
		assertEquals(3,l1.getHeight());
		
		assertEquals(3,l12.getWidth());
		assertEquals(2,l12.getHeight());
		
		assertEquals(2, l13.getWidth());
		assertEquals(3,l13.getHeight());
		
		assertEquals(3,l14.getWidth());
		assertEquals(2,l14.getHeight());
		
		assertEquals(2, l15.getWidth());
		assertEquals(3,l15.getHeight());
		
		//same for L2.
		
		assertEquals(2, l2.getWidth());
		assertEquals(3,l2.getHeight());
		
		assertEquals(3,l22.getWidth());
		assertEquals(2,l22.getHeight());
		
		assertEquals(2, l23.getWidth());
		assertEquals(3,l23.getHeight());
		
		assertEquals(3,l24.getWidth());
		assertEquals(2,l24.getHeight());
		
		assertEquals(2, l25.getWidth());
		assertEquals(3,l25.getHeight());
		
		//square test
		 assertEquals(2, square.getWidth());
	     assertEquals(2, square.getHeight());
	     
	     assertEquals(2,squareRotated.getWidth());
	     assertEquals(2,squareRotated.getHeight());
	     
	     //s test
	     assertEquals(3, s.getWidth());
	     assertEquals(2,s.getHeight());
	     
	     assertEquals(2,sRotated.getWidth());
	     assertEquals(3,sRotated.getHeight());
	     
	     assertEquals(3, s5.getWidth());
	     assertEquals(2,s5.getHeight());
	     
	     //s2 tests
	     assertEquals(3, s2.getWidth());
	     assertEquals(2,s2.getHeight());
	     
	     assertEquals(2,s2Rotated.getWidth());
	     assertEquals(3,s2Rotated.getHeight());
	     
	     assertEquals(3, s25.getWidth());
	     assertEquals(2,s25.getHeight());
		
	}
	
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
		//added tests
		//Continue s tests:
		assertTrue(Arrays.equals(new int[] {0, 1}, s2Rotated.getSkirt()));
		
		// test square.
		assertTrue(Arrays.equals(new int[]{0,0},square.getSkirt()));
		assertTrue(Arrays.equals(new int[]{0,0},squareRotated.getSkirt()));

		//stick tests.
		assertTrue(Arrays.equals(new int[]{0,0,0,0},stickRotated.getSkirt()));
		assertTrue(Arrays.equals(new int[]{0},stick5.getSkirt()));

		//L tests
		assertTrue(Arrays.equals(new int[]{0,0},l1.getSkirt()));
		assertTrue(Arrays.equals(new int[]{0,0,0},l12.getSkirt()));
		assertTrue(Arrays.equals(new int[]{0,1,1},l14.getSkirt()));
		assertTrue(Arrays.equals(new int[]{0,0},l15.getSkirt()));
		

	}
	
	//tests fast rotation
	public void testFR(){
		Piece[] p = Piece.getPieces();
		assertTrue(p[Piece.L1].fastRotation().equals(l12));
		assertTrue(p[Piece.L2].fastRotation().equals(l22));
		assertTrue(p[Piece.S1].fastRotation().equals(sRotated));
		assertTrue(p[Piece.S2].fastRotation().equals(s2Rotated));
		assertTrue(p[Piece.SQUARE].fastRotation().equals(squareRotated));
		
		
		
	}
	//test fast rotation
	public void testFR1(){
		Piece[] p = Piece.getPieces();
		assertTrue(p[Piece.SQUARE].equals(square));
		assertTrue(p[Piece.L1].equals(l1));
		assertTrue(p[Piece.L2].equals(l2));
		assertTrue(p[Piece.PYRAMID].equals(pyr1));
		assertTrue(p[Piece.S1].equals(s));
		assertTrue(p[Piece.S2].equals(s2));
	}
	public void testFR2(){
		Piece[] p = Piece.getPieces();
		assertTrue(p[Piece.SQUARE].equals(square));
		assertTrue(p[Piece.L1].equals(l1));
		assertTrue(p[Piece.L2].equals(l2));
		assertTrue(p[Piece.PYRAMID].equals(pyr1));
		assertTrue(p[Piece.S1].equals(s));
		assertTrue(p[Piece.S2].equals(s2));
	}
	
	
	public void testCycle(){
		
		assertFalse(stick.equals(stick5));
		assertTrue(l1.equals(l15));
		assertTrue(l2.equals(l25));
		assertTrue(pyr1.equals(pyr5));
		assertFalse(s.equals(sRotated));
	}
	
	
}
