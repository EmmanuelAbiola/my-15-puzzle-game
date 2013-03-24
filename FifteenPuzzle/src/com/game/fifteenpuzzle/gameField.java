package com.game.fifteenpuzzle;

public class gameField {
	int field[][] = new int[4][4];	



/*Create Normal Field, the numbers in order 1-15*/	
private void CreateNormalField()
{
	for (int i =0; i<15; i++)
	{
		field[i%4][i/4]=i+1;
	}
}

/*Create Mixed Field, the numbers not in order */
private void CreateMixedField()
{
	int x,y;//position
	for (int i = 0 ; i<100; i++)
	{
		x = (int)(Math.random()*4);
		y = (int)(Math.random()*4);
		this.moveNumber(x,y);
	}
}

//constructor
public gameField() {
	CreateNormalField();
}

/*according to the number position on the field move it*/
public boolean moveNumber(int x, int y) {
	
	//empty field coordinates
	int emptyX=-1,emptyY=-1;
	boolean result = false;//if there is possibility to move
	
	//look for an empty field position
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			if (this.field[i][j] == 0) {
				emptyX = i;
				emptyY = j;
			}
		}
	}
	// check if we cam move
			if (emptyX == x || emptyY == y) { //same column or same raw
				if (!(emptyX == x && emptyY == y)) { //the given cell is the empty cell, can't move
					if (emptyX == x) {// same row
						if (emptyY < y) { //from the right of the empty cell 
							for (int i = emptyY+1; i<= emptyY; i++) {
								field[x][i-1] = field[x][i];//sw
							}
						} else {
							for (int i = emptyY; i > y; i--) {
								field[x][i] = field[x][i - 1];
							}
						}
					}
					if (emptyY == y) { //same column
						if (emptyX < x) { //the number from the right of empty cell
							for (int i = emptyX+1; i <= x; i++) {
								field[i-1][y] = field[i][y];
							}
						} else {
							for (int i = emptyX; i > x; i--) {
								field[i][y] = field[i - 1][y];
							}
						}
					}
					field[x][y] = 0; // the cell that moved replaced with empty cell position
					
					result = true; //we could move the number
					
				}// if !(emptyX == x && emptyY == y)
				
				else // the empty cell can't move
				{
					result = false;
				}
			}
	//the result ==> true - can move false==>can't move		
	return result;
		
}	
	
	






}//end class gameFileld