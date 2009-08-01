package android.com.DotGame;

import android.graphics.Color;



public class Line {
	
	//Variables
	Dot dotOne;
	Dot dotTwo;
	int xStartPosition;
	int yStartPosition;
	int xEndPosition;
	int yEndPosition;
	int lineColor;
	String lineNum;
	
	public Line(Dot dot1, Dot dot2, int player) {
		
		dotOne = dot1;
		dotTwo = dot2;
		
		lineNum = (""+dot1.getXArrayPos()+dot1.getYArrayPos()+dot2.getXArrayPos()+dot2.getYArrayPos());
		
		this.xStartPosition = dot1.getX_position();
		this.yStartPosition = dot1.getY_position();
		this.xEndPosition = dot2.getX_position();
		this.yEndPosition = dot2.getY_position();
		
		if(player==1) {
			lineColor = Color.parseColor(game.getPlayer1Color());
		}
		else if(player==2) {
			lineColor = Color.parseColor(game.getPlayer2Color());
		}
	}
	
	public int getXStart() {
		return xStartPosition;
	}
	
	public int getYStart() {
		return yStartPosition;
	}
	
	public int getXEnd() {
		return xEndPosition;
	}
	
	public int getYEnd() {
		return yEndPosition;
	}

	public int getColor() {
		return lineColor;
	}
	
	public String getLineNum() {
		return lineNum;
	}
	
	public String inverseLineNum(String lnNum) {
		String temp1 = lnNum.substring(2,4);
		String temp2 = lnNum.substring(0,2);
		return temp1+temp2;
	}
	
	public boolean isVertical() {
		if(lineNum.substring(0,1).equalsIgnoreCase(lineNum.substring(2,3))) {
			return true;
		}
		return false;
	}
}