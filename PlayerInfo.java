import java.awt.*;
import javax.swing.*;

public class PlayerInfo extends JPanel{
	JLabel name, wins, losses, moves;
	public PlayerInfo(Player p) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		name = new JLabel("Name: " + p.getName());
		wins = new JLabel("Wins: " + p.getWins());
		losses = new JLabel("Losses: " + p.getLosses());
		if (p.getTime() != -1)
			moves = new JLabel("Fewest Moves: " + p.getTime());
		else
			moves = new JLabel("Fewest Moves: Invalid");
			
		add(name);
		add(wins);
		add(losses);
		add(moves);
	}
	
	public void loadPlayer(Player p) {
		removeAll();
		name = new JLabel("Name: " + p.getName());
		wins = new JLabel("Wins: " + p.getWins());
		losses = new JLabel("Losses: " + p.getLosses());
		if (p.getTime() != -1)
			moves = new JLabel("Fewest Moves: " + p.getTime());
		else
			moves = new JLabel("Fewest Moves: Invalid");
		add(name);
		add(wins);
		add(losses);
		add(moves);
		this.getRootPane().revalidate();
	}
	
}
