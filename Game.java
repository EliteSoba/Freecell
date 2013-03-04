import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Game extends JPanel implements MouseListener, MouseMotionListener, ActionListener, Serializable{
	/*This and Freecell.java are the two components to the actual game.
	 * This one controls all the interaction with the players to make the game playable.
	 * It implements all the everything that you need plus more.
	 */
	private Freecell fr; //This is to coordinate with Freecell
	private int selected; //This is the card that you are moving
	BufferedImage b; //Buffering to make the experience more smooth and less flickery
	Graphics2D backg; //see above
	int mx, my; //These are important coordinates for the moving card
	int startstack, startnum; //These are important for moving
	Player p;
	Application app;
	public Game(Application a) {
		//Constructor. Makes a Freecell. Instantiates everything.
		fr = new Freecell(this);
		selected = -1;
		app = a;
		addMouseListener(this);
		addMouseMotionListener(this);
		setSize(803, 800);
		b = new BufferedImage(803, 800, BufferedImage.TYPE_INT_RGB);
		backg = b.createGraphics();
		mx = 0;
		my = 0;
		p = new Player("Filler");
		startstack = 0;
		startnum = 0;
		new Timer(25, this).start();
	}
	
	public void newGame(Player pl) {
		if (fr.getDealt()) {
			if (fr.checkWin()) {
				p.win();
				if (fr.getMoves() < p.getTime() || p.getTime() == -1)
					p.setTime(fr.getMoves());
			}
			else
				p.lose();
		}
		fr = null;
		p = pl;
		fr = new Freecell(this);
		fr.deal();
	}
	
	public void savePlayer() {
		try {
			FileOutputStream f = new FileOutputStream("player.sav");
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(p);
			o.close();
		} catch (Exception e) {
			System.err.println("Unknown error: Player data could not be saved");
		}
	}
	
	public void loadPlayer() {
		try {
			FileInputStream f = new FileInputStream("player.sav");
			ObjectInputStream o = new ObjectInputStream(f);
			p = (Player)o.readObject();
			o.close();
		} catch (Exception e) {
			System.err.println("Unknown error: Player data could not be loaded");
		}
	}
	
	public void paint (Graphics g) {
		//Paints everything by telling Freecell to do so.
		setSize(803, 800);
		fr.paint(backg);
		if (selected != -1)
			fr.getCard(selected).paint(this, backg);
		g.drawImage(b, 0, 0, this);
	}

	//@Override
	public void mouseClicked(MouseEvent arg0) {}

	//@Override
	public void mouseEntered(MouseEvent arg0) {}

	//@Override
	public void mouseExited(MouseEvent arg0) {}

	//@Override
	public void mousePressed(MouseEvent arg0) {
		//For when you click on a card. Determines which card is being moved.
		mx = arg0.getX();
		my = arg0.getY();
		startstack = Freecell.getStack(mx, my);
		for (int i = 0; i < 52; i++) {
			if (fr.getCard(i).getMovable() && fr.getCard(i).contains(mx, my)) {
				selected = i;
				
			}
		}
		mx -= fr.getCard(selected).getX();
		my -= fr.getCard(selected).getY();
		
		if (selected != -1){
			fr.move(startstack, -1, 1);
		}
	}

	//@Override
	public void mouseReleased(MouseEvent arg0) {
		//Determines where the selected card goes when the mouse is released
		if (selected != -1){
			int endstack = Freecell.getStack(arg0.getX(), arg0.getY());
			//int fx = fr.getCard(selected).getX();
			//int fy = fr.getCard(selected).getY();
			/*if (fr.stacks(fx, fy) != -1) {
				fr.stack(fr.stacks(fx, fy)); //I found where stacks was being used.
				return;
			}*/
			if (fr.chains(endstack, fr.getCard(selected)))
					fr.move(-1, endstack, 1);
			else
				fr.move(-1, startstack, 1);
		}
		
		selected = -1;
		
		app.updateMoves(fr.getMoves());
	}

	//@Override
	public void actionPerformed(ActionEvent e) {
		//Seems pretty self-explanatory here.
		repaint();
	}

	//@Override
	public void mouseDragged(MouseEvent arg0) {
		//Moves the moving card
		if (selected != -1){
			fr.getCard(selected).setX(arg0.getX()-mx);
			fr.getCard(selected).setY(arg0.getY()-my);
		}
	}

	//@Override
	public void mouseMoved(MouseEvent arg0) {}
	
	public int getMoves() {
		return fr.getMoves();
	}
	
	
}
