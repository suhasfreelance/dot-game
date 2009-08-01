package android.com.DotGame;



public class Dot {
	
	//Variables
	private int x_position;
	private int y_position;
	private int x_arrayPos;
	private int y_arrayPos;
	private boolean selected;
	
	public Dot(int xPos, int yPos, int xTotal, int yTotal) {
		
		int max_width = game.getWidth();
		int max_height = game.getHeight();
        
		x_arrayPos = xPos;
		y_arrayPos = yPos;
		
        x_position = (int)((((max_width - max_width*.025)/(xTotal+1))*(xPos+1))+max_width*.0125);
        y_position = (int)((((max_height - max_height*.2)/(yTotal+1))*(yPos+1))+max_height*.05);  
        
        selected = false;
	}
	
	public int getXArrayPos() {
		return x_arrayPos;
	}
	
	public int getYArrayPos() {
		return y_arrayPos;
	}
	
	public int getX_position() {
		return x_position;
	}

	public void setX_position(int x_position) {
		this.x_position = x_position;
	}

	public int getY_position() {
		return y_position;
	}

	public void setY_position(int y_position) {
		this.y_position = y_position;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean state) {
		selected = state;
	}
}