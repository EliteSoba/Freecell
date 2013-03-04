import java.io.*;
public class Player implements Serializable{
	private String name;
	private int time;
	private int wins;
	private int losses;
	public Player() {
		name = "";
		time = -1;
		wins = 0;
		losses = 0;
	}
	
	public Player(String n) {
		name = n;
		time = -1;
		wins = 0;
		losses = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public void setTime(int t) {
		time = t;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setWins(int w) {
		wins = w;
	}
	
	public void setLosses(int l) {
		losses = l;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public void win() {
		wins++;
	}
	
	public void lose() {
		losses++;
	}

}
