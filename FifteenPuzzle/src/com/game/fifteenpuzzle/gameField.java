package com.game.fifteenpuzzle;



public class gameField {
	
	int field[][] = new int[4][4];	
	protected int x, y; 


//constructor
public gameField() {
	CreateNormalField();
	CreateRandomField();
}

/*Create Normal Field, the numbers in order 1-15*/	
private void CreateNormalField()
{
	for (int i =0; i<15; i++)
	{
		int num =i+1;
		field[i%4][i/4]= num;
	}
}

/*Create Mixed Field, the numbers not in order */
public void CreateRandomField()
{
	int x,y;//position
	for (int i = 0 ; i<100; i++)
	{
		x = (int)(Math.random()*4);
		y = (int)(Math.random()*4);
		this.moveRect(x,y);//create random field by moving the rect, so if will be always a solution
	}
}
public int getFieldNumber(int x, int y) {

	return field[x][y];
}

public int getEmptyY() {

	int emptyX = -1, emptyY = -1;//empty field coordinates
	
	//look for an empty field position
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.field[i][j] == 0) {
					emptyX = i;
					emptyY = j;
				}
			}
		}
	return emptyY;
}

public int getEmptyX() {

	int emptyX = -1, emptyY = -1;//empty field coordinates
	
	//look for an empty field position
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.field[i][j] == 0) {
					emptyX = i;
					emptyY = j;
				}
			}
		}
	return emptyX;
}

/*according to the number position on the field move it*/
public boolean moveRect(int x, int y) {

	int emptyX = -1, emptyY = -1;//empty field coordinates
	boolean result = false;// result of movement : can move true, can't false
	
	//look for an empty field position
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			if (this.field[i][j] == 0) {
				emptyX = i;
				emptyY = j;
			}
		}
	}
	
	// check if we can move
	if ( x == emptyX || y == emptyY) {//same column or same raw
		if (!(x == emptyX && y == emptyY)) { //the given cell is the empty cell, can't move
			if (x == emptyX) {//same raw with empty field
				if (emptyY > y) {//from the right of the empty cell 
					for (int i = emptyY; i > y; i--) {//the number from the left of empty cell
						field[x][i] = field[x][i - 1];//swap
					}
				} 
				else 
				{
					for (int i = emptyY+1; i<= y; i++) {
						field[x][i-1] = field[x][i];//swap
					}
				}
			}
			if (y == emptyY) {//same column as empty y
				if (emptyX > x) {//the number from the right of empty cell
					for (int i = emptyX; i > x; i--) {//the number from the left of empty cell
						field[i][y] = field[i - 1][y];//swap
					}
				} 
				else 
				{
					for (int i = emptyX+1; i <= x; i++) {
						field[i-1][y] = field[i][y]; //swap
					}
				}
			}
			
			field[x][y] = 0;// the cell that moved replaced with empty cell position should be an empty cell
			result = true; //we could move the number
		}
		else // the empty cell can't move
		{
			result = false;//we could not move the number
		}
	}
	//return the result
	return result;
}	

//check the game is over
public boolean IfGameOver()
{	
	int gameOverField[][] = new int[4][4];	
	int num=0;
	for (int i =0; i<15; i++)
	{
		num =i+1;
		gameOverField[i%4][i/4]= num;
	}
	//check if the normal field is the same as the game field
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			if(field[i][j] != gameOverField[i][j])
			{
				return false;
			}
		}
	}
	return true;
}

}//end class gameFileld