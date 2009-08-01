package android.com.DotGame;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;



public class GameView extends View {

	//Instance Variables
	Dot[][] grid;
	ArrayList<Box> boxes = new ArrayList<Box>();
	ArrayList<Line> lines = new ArrayList<Line>();
	Bitmap normalDot;
	Bitmap pressedDot;
	Paint dot;
	Paint line;
	Paint box;
	Paint scoreText;
	int dotsPressed;
	Dot firstDot = new Dot(0,0,0,0);
	Dot secondDot = new Dot(0,0,0,0);
	int player;
	String player1, player2;
	int score1, score2;	
	
	public GameView(Context context, Dot[][] grid, ArrayList<Line> lines, ArrayList<Box> boxes, String player1, String player2) {
		super(context);
		dot = new Paint();
		//dot.setShadowLayer(2, 4, 4, Color.parseColor("#000000"));
		line = new Paint();
		line.setStrokeWidth(5);
		line.setShadowLayer(2, 4, 4, Color.parseColor("#000000"));
		box = new Paint();
		box.setShadowLayer(2, 4, 4, Color.parseColor("#000000"));
		scoreText = new Paint();
		scoreText.setTextAlign(Align.CENTER);
		scoreText.setColor(Color.parseColor("#FFFFFF"));
		scoreText.setTextSize(15);
				
		this.grid = grid;
		this.boxes = boxes;
		this.lines = lines;
		score1 = score2 = 0;
		this.player1 = player1;
		this.player2 = player2;
		normalDot = BitmapFactory.decodeResource(getResources(), R.drawable.dot_normal);
		pressedDot = BitmapFactory.decodeResource(getResources(), R.drawable.dot_pressed);
		dotsPressed = 0;
		player = 1;
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		
		canvas.drawText(player1+": "+score1+"     "+player2+": "+score2, game.getWidth()/2, (float)(game.getWidth()*.1), scoreText);
		
		for(Line e: lines){
			line.setColor(e.getColor());
			canvas.drawLine(e.getXStart(), e.getYStart(), e.getXEnd(), e.getYEnd(), line);
		}
		
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				if(!grid[i][j].isSelected()) {
					canvas.drawBitmap(normalDot, grid[i][j].getX_position()-10, grid[i][j].getY_position()-10, dot);
				}
				else {
					canvas.drawBitmap(pressedDot, grid[i][j].getX_position()-10, grid[i][j].getY_position()-10, dot);
				}
			}
		}		
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
	   	//Determine what we're touching
	   	if(event.getAction() == 0) { //0 is ACTION DOWN
	   		float x_touched = event.getX();
		   	float y_touched = event.getY();
		   	//Log.d("I've been touched", x_touched + ", " + y_touched);
		   	/*
		   	 * Note:  Check to see if its a node touching... 
		   	 * Because of how the network GUI is set up, node touches are more prevalent than link touches
		   	 * If the touch is < 25 px away from a node center, then we consider it a node touch
		   	 * else check for line touch (is within 20px of line)
		   	 * else, just a random space touch... later use this to launch a contextual menu
		   	 */
		   	//btw we dont need the accuracy of a float... so take it to an int
		   	check_touch((int)x_touched, (int)y_touched);
		}
	   	return true;
   }
	

   /*
    * Function to check to see what was touched. See explanation above for how she works.
    * 
    * Arguments: x and y of the point touched
    */
   boolean check_touch(int x, int y){
	   	//check for node
	   	for(int i = 0; i < grid.length; i++){
	   		for(int j = 0; j < grid[0].length; j++) {
	   		//check to see within 10px
		   		if(Math.abs(grid[i][j].getX_position() - x) < 10){
		   			//Check y coordinates against same node
		   			if(Math.abs(grid[i][j].getY_position() - y) < 10){
		   				//YOU PRESSED A NODE
		   				Log.d("Dot Pressed", "Dot: " + i + ", " + j);
		   				selectDot(i, j);
		   				return true;
		   			}
		   		}
	   		}
	   	}
	   	return false;
	}

   
   public void selectDot(int xPos, int yPos) {
   	   if(grid[xPos][yPos].isSelected()) {
		   grid[xPos][yPos].setSelected(false);
		   firstDot = new Dot(0,0,0,0);
		   dotsPressed--;
	   }
		   
   	   else if(!grid[xPos][yPos].isSelected() && dotsPressed < 2) {
		   grid[xPos][yPos].setSelected(true);
		   if(dotsPressed==0) {
			   firstDot = grid[xPos][yPos];
		   }
		   else {
			   secondDot = grid[xPos][yPos];
		   }
		   dotsPressed++;
		   if(dotsPressed == 2) {
			   checkLine();
		   }
	   }
	   Log.d("Selected:", ""+grid[xPos][yPos].isSelected());
	   invalidate();
	   
   }
   
   public void checkLine() {
	   if(((firstDot.getXArrayPos() == secondDot.getXArrayPos()) && Math.abs(firstDot.getYArrayPos()-secondDot.getYArrayPos()) == 1) ||
			   ((firstDot.getYArrayPos() == secondDot.getYArrayPos()) && Math.abs(firstDot.getXArrayPos()-secondDot.getXArrayPos()) == 1)) {
		   	
		   String possibleLineNum = ""+firstDot.getXArrayPos()+firstDot.getYArrayPos()+secondDot.getXArrayPos()+secondDot.getYArrayPos();
		   int addFlag = 1;
		   
		   if(lines.isEmpty()) {
			   lines.add(new Line(firstDot, secondDot, player));
		   }
		   else {
			   for(Line e: lines) {
				   //Log.d("Line Num: "+e.getLineNum(),"Inverse: "+e.inverseLineNum(e.getLineNum()));
			   			
			   		if(e.getLineNum().equalsIgnoreCase(possibleLineNum) ||
						   (e.inverseLineNum(e.getLineNum())).equalsIgnoreCase(possibleLineNum)) {
					   addFlag = 0;
			   		}
			   		else {
			   			//Do Nothing
			   		}
		   	   }	
		   //Log.d("Possible Line: ", possibleLineNum);
		   }
		   if(addFlag == 1) {
			   lines.add(new Line(firstDot, secondDot, player));
			   
			   if(player==1) { player = 2;}
			   else { player = 1;}
			   checkBox(new Line(firstDot, secondDot, player));
		   }
	   }
	   deselectDots();
   }
   
   public void checkBox(Line testLine) {
	   Log.d("Is Vertical:", ""+testLine.isVertical());
	   
	   String[] posLines = new String[6];
	   int[] addFlag = new int[6];
	   
	   if(testLine.isVertical()) {
		   posLines[0] = ""+(Integer.parseInt(testLine.getLineNum().substring(0,1))-1) + testLine.getLineNum().substring(1, 2) + (Integer.parseInt(testLine.getLineNum().substring(2,3))-1) + testLine.getLineNum().substring(3, 4);
		   posLines[1] = ""+(Integer.parseInt(testLine.getLineNum().substring(0,1))-1) + testLine.getLineNum().substring(1, 4);
		   posLines[2] = ""+testLine.getLineNum().substring(0,2) + (Integer.parseInt(testLine.getLineNum().substring(2,3))-1) + testLine.getLineNum().substring(3,4);
		   posLines[3] = ""+(Integer.parseInt(testLine.getLineNum().substring(0,1))+1) + testLine.getLineNum().substring(1, 2) + (Integer.parseInt(testLine.getLineNum().substring(2,3))+1) + testLine.getLineNum().substring(3, 4);
		   posLines[4] = ""+(Integer.parseInt(testLine.getLineNum().substring(0,1))+1) + testLine.getLineNum().substring(1, 4);
		   posLines[5] = ""+testLine.getLineNum().substring(0,2) + (Integer.parseInt(testLine.getLineNum().substring(2,3))+1) + testLine.getLineNum().substring(3,4);
		   
		   
		   for(int i = 0; i < 3; i++) {
			   for(Line e: lines) {
				   if(e.getLineNum().equalsIgnoreCase(posLines[i]) ||
						   (e.inverseLineNum(e.getLineNum())).equalsIgnoreCase(posLines[i])) {
					   addFlag[i] = 1;
			   		}
			   }
		   }
			   
		   if(addFlag[0]==1 && addFlag[1]==1 && addFlag[2]==1) {
			   boxes.add(new Box(Integer.parseInt(posLines[0].substring(0,1)), (Integer.parseInt(posLines[0].substring(1,2))+Integer.parseInt(posLines[0].substring(3,4)))/2, player));
		   }
		   if(addFlag[3]==1 && addFlag[4]==1 && addFlag[5]==1) {
			   boxes.add(new Box(Integer.parseInt(posLines[3].substring(0,1)), (Integer.parseInt(posLines[3].substring(1,2))+Integer.parseInt(posLines[3].substring(3,4)))/2, player));
		   }
	   }
	   
   }
   
   public void deselectDots() {
	   for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				grid[i][j].setSelected(false);
			}
		}
	   dotsPressed = 0;
   }
}