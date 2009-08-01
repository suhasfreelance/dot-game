package android.com.DotGame;

import android.graphics.Color;



public class Box {
	
	//Variables
	int x_position;
	int y_position;
	int boxWidth;
	int boxHeight;
	int boxColor;
	
	public Box(int xPos, int yPos, int player) {
		this.x_position = xPos;
		this.y_position = yPos;
		
		if(player==1) {
			boxColor = Color.parseColor(game.getPlayer1Color());
		}
		else if(player==2) {
			boxColor = Color.parseColor(game.getPlayer2Color());
		}
	}
	
	public int getXPos() {
		return x_position;
	}
	
	public int getYPos() {
		return y_position;
	}
	
	public int getColor() {
		return boxColor;
	} 
}