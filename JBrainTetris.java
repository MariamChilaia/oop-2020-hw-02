import javax.swing.*;



public class JBrainTetris extends JTetris {

	private Brain.Move m=null;
	private Brain brain;
	private int currCount;
	private JCheckBox brainMove;
	
	//constructor.
	JBrainTetris(int pixels) {
		super(pixels);
		brain = new DefaultBrain();
		currCount = -10;
	}
	
	
	@Override
	public JComponent createControlPanel() {
		JComponent panel = super.createControlPanel();
		panel.add(new JLabel("Brain:"));
		//hand out code.
		brainMove = new JCheckBox("Brain active");
		panel.add(brainMove);
		return panel;
	}
	
	@Override
	public void tick(int verb) {
		if (brainMove.isSelected() && verb == DOWN ) {
			if(count!=currCount) {
				currCount = count;
				
				if (currentPiece!=null) {
					board.undo();
					int height = board.getHeight();
					m = brain.bestMove( board, currentPiece, height, m);
				}
			}
			if (m != null) {  //here we have three options, either rotate, turn left or right.
				if (!m.piece.equals(currentPiece))
					super.tick(ROTATE);
				if (currentX < m.x) 
					super.tick(RIGHT);
				if (currentX > m.x) 
					super.tick(LEFT);
				
				
			}
		} 
		
		super.tick(verb);
	}
	
	//how the main looks with exceptions.
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { 
			
		}
		
		JBrainTetris tetris = new JBrainTetris(16);
		JFrame frame = JBrainTetris.createFrame(tetris);
		
		frame.setVisible(true);
	}

}