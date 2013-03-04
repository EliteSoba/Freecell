import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
 
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class PlayerPane extends JPanel implements Serializable, ActionListener{
	
	JButton start, end;
	ArrayList<Player> players;
	JList ps;
	int curSel;
	Application app;
	JTextField name;
	JButton confirm;
	
	public PlayerPane(Application a) {
		players = new ArrayList<Player>();
		loadPlayers();
		curSel = 0;
		app = a;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		start = new JButton("Start");
		end = new JButton("Quit");
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		end.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.addActionListener(this);
		end.addActionListener(this);
		this.add(start);
		//this.add(new JLabel("Filler"));
		ps = new JList(getNames());
		this.add(end);
		this.add(ps);
		name = new JTextField(1);
		name.setMaximumSize(new Dimension(150, 30));
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		confirm = new JButton("Confirm");
		confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
		confirm.addActionListener(this);
	}
	
	private void loadPlayers() {
		try {
			FileInputStream f = new FileInputStream("player.txt");
			ObjectInputStream o = new ObjectInputStream(f);
			players = (ArrayList<Player>)o.readObject();
			o.close();
		} catch (Exception e) {
			System.err.println("Unknown error: Player data could not be loaded");
		}
	}
	
	public void savePlayers() {
		try {
			FileOutputStream f = new FileOutputStream("player.txt");
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(players);
			o.close();
		} catch (Exception e) {
			System.err.println("Unknown error: Player data could not be saved");
		}
	}
	
	public String[] getNames() {
		String names[] = new String[players.size()+1];
		for (int i = 0; i < players.size(); i++)
			names[i] = players.get(i).getName();
		names[players.size()] = "New Player";
		return names;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == end) {
			app.start(players.get(curSel));
			//players.get(curSel).lose();
			savePlayers();
			System.exit(1);
		}
		
		if (arg0.getSource() == start) {
			if (ps.getMinSelectionIndex() != -1) {
				if (ps.getMinSelectionIndex() != players.size()) {
					curSel = ps.getMinSelectionIndex();
					app.start(players.get(curSel));
				}
				else {
					this.add(name);
					this.add(confirm);
					this.getRootPane().revalidate();
				}
					
			}
		}
		if (arg0.getSource() == confirm) {
			if (confirmName(name.getText())) {
				Player newp = new Player(name.getText());
				players.add(newp);
			}
			remove(name);
			remove(confirm);
			
			remove(ps);
			ps = new JList(getNames());
			add(ps);
			this.getRootPane().revalidate();
			repaint();
		}
		
		
		
	}
	
	private boolean confirmName(String s) {
		for (int i = 0; i < players.size(); i++)
			if (players.get(i).getName().equals(s))
				return false;
		return true;
	}
	
	public Player getPlayer(int index) {
		
		return players.get(index);
	}
	
}
