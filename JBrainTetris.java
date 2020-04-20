import java.awt.Dimension;

import javax.swing.*;



public class JBrainTetris extends JTetris {
	private JLabel adversaryLabel;
	private Brain.Move m=null;
	private Brain brain;
	private int currCount;
	private JCheckBox brainMove;
	private JSlider adversary;
	private JPanel little;
	
	//constructor.
	JBrainTetris(int pixels) {
		super(pixels);
		brain = new DefaultBrain();
		currCount = -10;
	}
	
	
	@Override
	public JComponent createControlPanel() {
		JComponent panel = super.createControlPanel();
		little = new JPanel(); 
		little.add(new JLabel("Adversary:")); 
		adversary = new JSlider(0, 100, 0); // min, max, current
		adversary.setPreferredSize(new Dimension(100,15)); 
		little.add(adversary);

		//little.add(adversaryLabel);
		 adversaryLabel = new JLabel("ok");

		little.add(adversaryLabel);
		panel.add(little);
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
	@Override
	public Piece pickNextPiece(){
		int r= random.nextInt(99);
		if(r< adversary.getValue()){
			adversaryLabel.setText("ok");
			Piece next = super.pickNextPiece();
			
			double badScore = 0;   //because nextm.score is a double.
			for(Piece p: pieces){
				board.undo();
				Brain.Move nextm = brain.bestMove(board, p, HEIGHT, null);
				if(nextm!=null){
					if(nextm.score> badScore){
						next = p;
						badScore = nextm.score;
					}
				}
			}
			return next;
		}
		adversaryLabel.setText("*ok*");
		Piece res;
		res = super.pickNextPiece();
		return res;
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