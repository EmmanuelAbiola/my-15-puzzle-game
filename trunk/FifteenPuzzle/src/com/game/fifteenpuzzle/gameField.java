package com.game.fifteenpuzzle;



public class gameField {
	
	int field[][] = new int[4][4];	
	protected int x, y; 
	public int moveRectArray[] = new int[4];

//constructor
public gameField() {
	CreateNormalField();
	//CreateRandomField();
}
public void cleanMoveRectArray()
{
	for(int i=0;i< moveRectArray.length;i++)
		moveRectArray[i] = -1;
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
/**
 * The function that Create Mixed Field, the numbers not in order
 */
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
/**
 * The function that the number on the field by x,y
 * @param x - field coordinates
 * @param y - Field coordinate
 * @return - number 
 */
public int getFieldNumber(int x, int y) {

	return field[x][y];
}

/**
 * The function that gives the field position
 * @return - x field coordinate 
 */
public int[] getFieldPosition(int Num) {

	 int position[] = new int[2];
	 int index=0;
	 for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if(field[i][j] == Num){
					position[index++]=i;
					position[index]=j;
				}
			}
	 }
	return position;
}

/**
 * The function returns the empty cell on y axis 
 */
public int getEmptyY() {

	int emptyY = -1;//empty field coordinates
	
	//look for an empty field position
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.field[i][j] == 0) {
					emptyY = j;
				}
			}
		}
	return emptyY;
}
/**
 * The function returns the empty cell on x axis 
 */
public int getEmptyX() {

	int emptyX = -1;//empty field coordinates
	
	//look for an empty field position
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.field[i][j] == 0) {
					emptyX = i;
					
				}
			}
		}
	return emptyX;
}

/*according to the number position on the field move it*/
/**
 * The function move the tile in needed position if it is possible
 * @param x
 * @param y
 * @return true/false 
 */
public boolean moveRect(int x, int y) {

	int emptyX = -1, emptyY = -1;//empty field coordinates
	boolean result = false;// result of movement : can move true, can't false
	int move=0;
	cleanMoveRectArray();
	
	if(x >= 4 || y >=4)
		return false;
	
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
						moveRectArray[move++]=field[x][i];
					}
				} 
				else 
				{
					for (int i = emptyY+1; i<= y; i++) {
						field[x][i-1] = field[x][i];//swap
						moveRectArray[move++]=field[x][i-1];
					}
				}
			}
			if (y == emptyY) {//same column as empty y
				if (emptyX > x) {//the number from the right of empty cell
					for (int i = emptyX; i > x; i--) {//the number from the left of empty cell
						field[i][y] = field[i - 1][y];//swap
						moveRectArray[move++]=field[i][y];
					}
				} 
				else 
				{
					for (int i = emptyX+1; i <= x; i++) {
						field[i-1][y] = field[i][y]; //swap
						moveRectArray[move++]=field[i-1][y];
					}
				}
			}
			
			field[x][y] = 0;// the cell that moved replaced with empty cell position should be an empty cell
			moveRectArray[move++]=field[x][y];
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
/**
 * The function return the moved tiles array
 * @return  moveRectArray
 */
public int[] getMoveRectArray() {
	   return moveRectArray;
}

//check the game is over
/**
 * The function checks if the game is over
 * @return  true/false
 */
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