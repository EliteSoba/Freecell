import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Freecell {
	/*This and Game.java are the two components to the actual game.
	 * This one controls all the game mechanics and rules.
	 * In theory, you could make your own Game class to reinstate everything to your own desire. I wouldn't suggest it, though
	 * If you don't know how to play Freecell solitaire, it's kinda like Solitaire, except you have eight Stacks (literally, like the object, Stack)
	 * and you can see every card in each stack and you have 4 spaces up top where you can put any card you want.
	 * I can't really go much more in depth like this, so all you have to know is that it's very much like Solitaire, but it's harder to move cards
	 */
	private Deck deck; //This is the deck that is necessary for card games
	Game board; //This is the JPanel stage upon which stuff happens
	ArrayList<ArrayList<Card>> objectorientedprogrammingisfun; //This is all because you discourage the use of arrays, otherwise some of these would be arrays as they are statically sized anyways.
	ArrayList<Card> moving; //In theory, you could move multiple cards at a time, but the rules involving that are a bit complex
	ArrayList<Card> hold; //These are the 4 spaces up top where you can put anything.
	ArrayList<Card> keep; //These are the 4 spaces where you put the cards in numeric order, like in normal Solitaire, but you can't pull them out afterwards.
	int moves;
	int old;
	private boolean dealt;
	public Freecell(Game ga) {
		//Constructor. It requires a JFrame because that's what we were taught. It instantiates everything and that's all.
		deck = new Deck();
		deck.shuffle();
		board = ga;
		moves = 0;
		old = -2;
		objectorientedprogrammingisfun = new ArrayList<ArrayList<Card>>();
		moving = new ArrayList<Card>();
		hold = new ArrayList<Card>();
		for (int i = 0; i < 4; i++) {
			hold.add(null);
		}
		keep = new ArrayList<Card>();
		for (int i = 0; i < 4; i++) {
			keep.add(null);
		}
		//deal();
		dealt = false;
	}
	
	public void deal() {
		//Moves all the cards into the stacks and assigns them coordinates to be at.
		for (int i = 0; i < 8; i++) {
			objectorientedprogrammingisfun.add(new ArrayList<Card>());
		}
		
		for (int i = 0; i < 52; i++) {
			deck.getCard(i).setX(90 * (i%8) + 50);
			deck.getCard(i).setY(30* (i/8) + 200);
			
			objectorientedprogrammingisfun.get(i%8).add(deck.getCard(i));
		}
		dealt = true;
	}
	
	public Card getCard(int index) {
		//Same thing as in Deck class
		return deck.getCard(index);
	}
	
	/*public int stacks(int x, int y) {
		//Returns which up stack would contain the given point.
		//I know it was useful at some point, but it turns out I never use this method
		//I think it got overriden bygetStack;
		for (int i = 0; i < 4; i++) {
			if (new Rectangle(90 * i + 35, 70, Card.BASE, Card.HEIGHT).contains(x, y))
				return i;
		}
		return -1;
	}*/
	
	public static int getStack(int x, int y) {
		//Returns which ArrayList the current point is located on.
		if (y > 160)
			return (x - 50)/90;
		if (x < 400)
			return (x - 35)/90 + 10;
		return (x-65)/90 + 10;
	}
	
	public boolean chains(int target, Card c) {
		//Returns true if the card can be validly placed on the targeted stack
		Rectangle r = new Rectangle(c.getX(), c.getY(), Card.BASE, Card.HEIGHT);
		if (target < 10) { //This part deals with the 8 lower stacks
			if (target > 7)
				return false;
			if (objectorientedprogrammingisfun.get(target).size()==0)
				return true;
			Card rect = objectorientedprogrammingisfun.get(target).get(objectorientedprogrammingisfun.get(target).size()-1);
			return (new Rectangle(rect.getX(), rect.getY(), Card.BASE, Card.HEIGHT).intersects(r)) && (rect.getSuit() + c.getSuit()) % 2 == 1 && rect.getNum()-1 == c.getNum();
		}
		if (target < 14) //This part deals with the top 4 hold stacks
			return hold.get(target-10) == null;
		//This part deals with the last 4 stack stacks
		return (keep.get(target-14) == null && c.getNum() == 0) || (keep.get(target-14) != null && keep.get(target-14).getSuit() == c.getSuit() && keep.get(target-14).getVal()+1 == c.getVal());
	}
	
	public void move(int start, int end, int num) {
		moves++;
		//Moves a card from moving to a stack, or vice versa.
		if (end == -1) {
			old = start;
			if (start < 10){
				for (int i = 0; i < num; i++) {
					if (objectorientedprogrammingisfun.get(start).size()>0)
					moving.add(objectorientedprogrammingisfun.get(start).remove(objectorientedprogrammingisfun.get(start).size()-1-i));
				}
			}
			else if (start < 14){
				moving.add(hold.get(start-10));
				hold.set(start-10, null);
			}
		}
		else if (start == -1) {
			if (old == end)
				moves-=2;
			if (end < 10) {
				for (int i = 0; i < num; i++) {
					if (moving.size()>0)
					objectorientedprogrammingisfun.get(end).add(moving.remove(moving.size()-1-i));
				}
			}
			else if (end < 14){
				hold.set(end-10, moving.remove(0));
			}
			else {
				keep.set(end-14, moving.remove(0));
			}
		}
		else {
			for (int i = 0; i < num; i++) {
				if (objectorientedprogrammingisfun.get(start).size()>0)
				objectorientedprogrammingisfun.get(end).add(objectorientedprogrammingisfun.get(start).remove(objectorientedprogrammingisfun.get(start).size()-1-i));
			}
		}
	}
	
	public boolean checkWin() {
		//Checks to see if all cards have been placed in the stack stacks
		for (int i = 0; i < objectorientedprogrammingisfun.size(); i++) {
			if (!objectorientedprogrammingisfun.get(i).isEmpty())
				return false;
		}
		for (int i = 0; i < hold.size(); i++) {
			if (hold.get(i) != null)
				return false;
		}
		return moving.isEmpty();
	}
	
	public void paint(Graphics2D g) {
		//Does everything
		
		//Green background
		g.setColor(new Color(8, 107, 43));
		g.fillRect(0, 0, 900, 900);
		
		//Resets coordinates to snap to positions
		for (int i = 0; i < objectorientedprogrammingisfun.size(); i++) {
			for (int j = 0; j < objectorientedprogrammingisfun.get(i).size(); j++) {
				objectorientedprogrammingisfun.get(i).get(j).setMovable(false);
				objectorientedprogrammingisfun.get(i).get(j).setX(90 * i + 50);
				objectorientedprogrammingisfun.get(i).get(j).setY(30 * j + 200);
			}
		}
		
		//Sets the movability of the cards at the top of the stacks
		for (int i = 0; i < objectorientedprogrammingisfun.size(); i++) {
			if (objectorientedprogrammingisfun.get(i).size()>0)
				objectorientedprogrammingisfun.get(i).get(objectorientedprogrammingisfun.get(i).size()-1).setMovable(true);
		}
		
		//Draws the 4 top stack outlines
		g.setColor(new Color(169, 169, 169));
		for (int i = 0; i < 8; i++)
			g.drawRect(90 * i + (i/4)*30 + 35, 70, Card.BASE, Card.HEIGHT);
		
		//Paints all the lower 8 stacks
		for (int i = 0; i < objectorientedprogrammingisfun.size(); i++) {
			for (int j = 0; j < objectorientedprogrammingisfun.get(i).size(); j++) {
				objectorientedprogrammingisfun.get(i).get(j).paint(board, g);
			}
		}
		
		//Paints the cards in the top 4 stacks
		for (int i = 0; i < hold.size(); i++) {
			if (hold.get(i) != null) {
				hold.get(i).setX(90 * i + 35);
				hold.get(i).setY(70);
				hold.get(i).setMovable(true);
				hold.get(i).paint(board, g);
			}
		}
		
		//Paints the cards in the top 4 stack stacks
		for (int i = 0; i < keep.size(); i++) {
			if (keep.get(i) != null) {
				keep.get(i).setX(90 * (i+4) + 65);
				keep.get(i).setY(70);
				keep.get(i).setMovable(false);
				keep.get(i).paint(board, g);
			}
		}
		
		//Paints the card that is currently being moved
		for (int i = 0; i < moving.size(); i++) {
			moving.get(i).paint(board, g);
		}
		
		//Checks if you win and print that you did
		if (dealt && checkWin()) {
			g.setColor(Color.black);
			g.setFont(new Font("SansSerif",Font.BOLD, 24));
			g.drawString("Congratulations, you won in " + moves/2 + " moves!", 170, 350);
		}
	}
	
	public int getMoves() {
		return moves/2;
	}
	
	public boolean getDealt() {
		return dealt;
	}

}
