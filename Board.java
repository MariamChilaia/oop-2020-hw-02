import java.util.Arrays;

// Board.java

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	private int[] widths;
	private int mHeight;
	private int tmHeight;
	private int[] heights;
	private boolean[][] tmp;
	private int[] tHeights;
	private int[] tWidths;
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		
		grid = new boolean[width][height];
		committed = true;
		mHeight=0;
		heights=new int[width];
		widths = new int[height];
		
		tmp = new boolean[width][height];
		tHeights=new int[width];
		tWidths= new int[height];
		commit();  //it has to be committed while created.
		// YOUR CODE HERE
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
		return mHeight; // YOUR CODE HERE
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		int gridMH=0; //max height of grid.
		if (DEBUG) {
			int[] gridHeight = new int[width]; //create a width length array
			int[] gridWidth = new int[height]; //same logic.
			for(int i = 0; i< grid.length; i++){
				for(int j = 0; j <grid[0].length; j++){
					if(grid[i][j]==true){
						gridWidth[j]++;
						if(j> gridHeight[i]-1){
							gridHeight[i]=j+1;
							if(gridHeight[i] > gridMH){
								gridMH = gridHeight[i];
							}
						}
					}
				}
			
			}

			assert(mHeight==gridMH);
			assert(Arrays.equals(heights,gridHeight ));
			assert(Arrays.equals(widths,gridWidth));
			// YOUR CODE HERE
		}
		
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int res = 0;
		for(int i = 0; i<piece.getWidth(); i++){
			int y = heights[x+i]-piece.getSkirt()[i];
			if(y>res && y>0){
				res=y;
			}
		}
		return res; 
		// YOUR CODE HERE
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x]; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y]; // YOUR CODE HERE
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		boolean res = false;
		res = (x<0||y<0||x>=width||y>=height||grid[x][y]);
		return res; // YOUR CODE HERE
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		committed=false;
		
		int result = PLACE_OK;
		for(int i =0; i< width; i++){
			System.arraycopy(grid[i],0,tmp[i],0,height);
		}
		copyArrays();
		tmHeight=mHeight;
		
		for(TPoint p : piece.getBody()){
			int currX = p.x + x;
			int currY = p.y+ y;
			if(currX>=width ||x<0||y<0 || currY>=height){
				result= PLACE_OUT_BOUNDS;
				break;
			}
			if(grid[currX][currY]) return PLACE_BAD;
			else{
				grid[currX][currY]=true;
				if(heights[currX]<currY+1){
					heights[currX]=currY;
					
					heights[currX]++;
				}
				widths[currY]++;
				if(heights[currX] > mHeight) mHeight = heights[currX];
				if(widths[currY]==width) result= PLACE_ROW_FILLED;
				
			}
		}
		// YOUR CODE HERE
		return result;
	}
	
	private void copyArrays(){
		System.arraycopy(heights, 0, tHeights, 0, width);
		System.arraycopy(widths, 0, tWidths, 0, height);
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		int rowsCleared = 0;
		int rowMin=0;
		// YOUR CODE HERE
		committed = false; 
		for(int i = 0; i< height; i++){
			if(widths[i]==width){
				rowsCleared++;
			} else if(rowsCleared>0){
				changeRow(i,rowMin);rowMin++;
				
			}else rowMin++;
		}
		
		
		while(rowMin < height){
			widths[rowMin]=0;
			for(int i = 0; i<width; i++){
				grid[i][rowMin]= false;
				heights[i]=heights[i]-1;
			}
			rowMin++;
			mHeight--;
		}
		//vibe check;
		sanityCheck();
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// YOUR CODE HERE
		if(committed==false){
			committed = true;
			boolean[][] tm = grid;
			grid = tmp;
			tmp=tm;
			int[] tmpW = heights;
			heights = tHeights;
			tHeights = tmpW;
			int[] tmpH = widths;
			widths=tWidths;
			tWidths = tmpH;
			mHeight= tmHeight;
		}
	}
	
	
	private void changeRow(int from, int to){
		widths[to]=widths[from];
		for(int i =0; i<width; i++){
			grid[i][to]= grid[i][from];
		}
	}
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		if(committed==false) committed=true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


