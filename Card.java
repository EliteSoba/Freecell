import javax.swing.*;
import java.awt.*;

public class Card {
	/*This is the class for the card.
	 * The important parts are the raw value and its conversion to suit and number,
	 * and the movability, which is an important quality in Freecell Solitaire
	 */
	public final static int BASE = 73;
	public final static int HEIGHT = 97;
	protected int value;//Raw Value from 0-51 where 0-12 is A-K for Diamonds, etc.
	protected int suit, num;//Suit and Value of each card
	protected String ID;
	private ImageIcon cardImage;
	protected boolean isMovable; //Determines whether the card is movable or not
	protected int x, y; //Coordinates to be drawn. Saved to card because each cards' coordinates matter.
	
	public Card() {
		//Default Constructor. Don't use this. It's useless.
		this(0);
	}
	
	public Card(int val) {
		//Constructor. Val should be between 0-51 for a valid card. If not, then no big deal, the card will just be useless.
		//Assigning of value and conversion to suit and number.
		value = val;
		suit = value / 13;
		num = value % 13;
		
		//For String ID conversion to get card ID
		switch (num) {
		case 0: ID = "a"; break;
		case 9: ID = "t"; break;
		case 10: ID = "j"; break;
		case 11: ID = "q"; break;
		case 12: ID = "k"; break;
		default: ID = (num + 1) + "";
		}
		switch (suit) {
		case 0: ID += "d"; break;
		case 1: ID += "c"; break;
		case 2: ID += "h"; break;
		case 3: ID += "s"; break;
		}
		
		//Gets the image using the magic of strings.
		cardImage = new ImageIcon("Cards/" + ID + ".gif");
		
		//Initializes specific parameters for Freecell
		isMovable = false;
		x = 0;
		y = 0;
	}
	
	//Getters and setters
	public int getVal() {
		return value;
	}
	
	public int getSuit() { //0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
		return suit;
	}
	
	public int getNum() {
		return num;
	}
	
	public String getID() {
		return ID;
	}
	
	public boolean getMovable() {
		return isMovable;
	}
	
	public void setMovable(boolean move) {
		isMovable = move;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int n) {
		x = n;
	}
	
	public void setY(int n) {
		y = n;
	}
	
	public void paint(JPanel j, Graphics2D g) {
		//Paints the card on the provided JFrame with the provided Graphics2D at its coordinates
		
		cardImage.paintIcon(j, g, x, y);
	}
	
	public boolean contains(int mx, int my) {
		//Returns true if the given coordinates are contained within the card
		if (mx >= x && mx <= x + BASE)
			if (my >= y&& my <= y + HEIGHT)
				return true;
		return false;
	}
}
