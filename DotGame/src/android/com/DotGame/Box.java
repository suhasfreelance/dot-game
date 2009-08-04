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
		int max_width = game.getWidth();
		int max_height = game.getHeight();
				
		x_position = (int)((((max_width - max_width*.025)/(menu.getXDim()+1))*(xPos+1))+max_width*.0125);
        y_position = (int)((((max_height - max_height*.2)/(menu.getYDim()+1))*(yPos+1))+max_height*.05);
		
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