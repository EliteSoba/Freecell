import java.awt.*;

import javax.swing.*;

public class Application extends JFrame{
	
	Game ga;
	PlayerPane p;
	PlayerInfo PI;
	Player pl;
	JLabel moves;
	public Application() {
		//this.setLayout(manager)
		
		setSize(1200, 800);
		pl = new Player("None");
		ga = new Game(this);
		p = new PlayerPane(this);
		PI = new PlayerInfo(pl);
		moves = new JLabel("Moves: " + ga.getMoves());
		ga.setPreferredSize(new Dimension(803, 800));
		this.add(ga, BorderLayout.LINE_START);
		this.add(moves, BorderLayout.PAGE_START);
		this.add(p,BorderLayout.CENTER);
		this.add(PI, BorderLayout.LINE_END);
		
	}
	
	public void updateMoves(int i) {
		remove(moves);
		moves = new JLabel("Moves: " + ga.getMoves());
		this.add(moves, BorderLayout.PAGE_START);
		this.getRootPane().revalidate();
		repaint();
	}
	
	public void playerLoad(Player p) {
		PI.loadPlayer(p);
	}
	
	public void start(Player p) {
		ga.newGame(p);
		playerLoad(p);
		repaint();
	}
	
	public static void main(String[] args) {
		/*Game ga = new Game();
		Player p = new Player();
		*/
		Application ga = new Application();
		ga.setVisible(true);
		//ga.setResizable(false);
		//ga.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/**/
		
	}

}
