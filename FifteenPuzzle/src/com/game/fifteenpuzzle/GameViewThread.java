package com.game.fifteenpuzzle;

import java.util.ArrayList;


import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread{
	
	private SurfaceHolder holder;
	private GameView mGameView;
    private boolean mRun = true;
    int i = 0,mx=0,my=0;
    Drawable d;
	int x_pos=120,y_pos=240;
	int x_InitPos=120,y_InitPos=240;
	int x_InitPosLand=360,y_InitPosLand=50;
	gameField game15puzzle;
	BmpSettings myBmp[] = new BmpSettings[15];
	int _currentX;
	int _currentY;
	AnimationDrawable animation;
	SurfaceHolder mSurfaceHolder;
	GameViewThread mGVThread;
	boolean init = true; 
	
	int movedFields[] = new int[]{-1,-1,-1,-1};
	int prevXpos[] = new int[16];
	int prevYpos[] = new int[16];
	static int movingX[] = new int[16];
	static int movingY[] = new int[16];
	static int positionEmpty=-1;
	
    
	int[] mBitmapsArray = { com.game.fifteenpuzzle.R.drawable.num16blank,com.game.fifteenpuzzle.R.drawable.num1, com.game.fifteenpuzzle.R.drawable.num2,
			com.game.fifteenpuzzle.R.drawable.num3, com.game.fifteenpuzzle.R.drawable.num4, com.game.fifteenpuzzle.R.drawable.num5,
			com.game.fifteenpuzzle.R.drawable.num6, com.game.fifteenpuzzle.R.drawable.num7,
			com.game.fifteenpuzzle.R.drawable.num8, com.game.fifteenpuzzle.R.drawable.num9, com.game.fifteenpuzzle.R.drawable.num10,
			com.game.fifteenpuzzle.R.drawable.num11, com.game.fifteenpuzzle.R.drawable.num12,
			com.game.fifteenpuzzle.R.drawable.num13, com.game.fifteenpuzzle.R.drawable.num14,com.game.fifteenpuzzle.R.drawable.num15
	};    
    
	//constructor
	public GameViewThread(SurfaceHolder holder,GameView GameView) {
        this.holder = holder;
        mGameView = GameView;
       
    }

    
    @Override
    public void run() {
        while(mRun) {
            Canvas canvas = null;
            movedFields = mGameView.getMovedFieldsArray();
            try {
                canvas = holder.lockCanvas(null);
                 synchronized (holder) {
                    // draw
                	 	 doDraw(canvas);
                }
            }
            finally {
                    if (canvas != null) {
                            holder.unlockCanvasAndPost(canvas);
                        }
            }
        }
    }

    public void setRunning(boolean b) {
        mRun = b;
    }

    /**
	 * getFinalXPos - Returns the final x-position on the field
	 * @param i - x index
	 * @return - x final position
	 */
    public int getFinalXPos(int i){
    	
    	int pos=0;
    	if(i == 0 )
			pos = x_pos;
		if(i == 1 )
			pos = x_pos+130;
		if(i == 2 )
			pos = x_pos+260;
		if(i == 3 )
			pos = x_pos+390;
		return pos;
	}
    
    /**
	 * getFinalYPos - Returns the final y-position on the field
	 * @param j - y index
	 * @return - y final position
	 */
    public int getFinalYPos(int j){
    	int pos=0;
    	if(j == 0 )
			pos = y_pos;
		if(j == 1 )
			pos = y_pos+130;
		if(j == 2 )
			pos = y_pos+260;
		if(j == 3 )
			pos = y_pos+390;
     return pos;
    }
    
    /**
	 * saveXYPos - saves the position of the x,y fields
	 * @param i - x index
	 * @param j - y index
	 */
    public void saveXYPos(int number,int i,int j){
    	prevXpos[number] = i;
    	prevYpos[number] = j;
    }
    
    /**
	 * getPrevXPos - returns previous X position
	 * @param number - the number
	 */
    public int getPrevXPos(int number){
    	return prevXpos[number];
    }
    
    /**
	 * getPrevYPos - returns previous Y position
	 * @param number - the number
	 */
    public int getPrevYPos(int number){
    	return prevYpos[number];
    }
    
   /* public int[] animationMovement(int number,int i,int j,Bitmap b,Canvas c,Paint p){
    	
    	
    	movedFields = mGameView.getMovedFieldsArray();
    	int movedNum,currentYpos=0,currentXpos=0;
    	int prevXPos,prevYPos;
    	int fieldPosition[] = new int[2];
    	
    	 if (movedFields[0]!=-1)
    	 {	
    		if(number == movedFields[0] || number == movedFields[1]
    				 || number == movedFields[2] || number == movedFields[3]){
    			
    			prevXPos =getPrevXPos(number);
    			prevYPos =getPrevYPos(number);
    			
    			if(prevXPos != i) //change on y axis
    			{
    				if(movingX[number]<getFinalXPos(j) && movingX[number]!=0){//the number need to be up
    					movingX[number] = movingX[number]+65;
    					currentXpos=movingX[number];
        			}else if(movingX[number]>getFinalXPos(j) && movingX[number]!=-1){//the number need to be down
        				movingX[number] = movingX[number]-65;
        				currentXpos=movingX[number];
        			}
        			else{//equal or greater then currentPos
        				currentXpos = getFinalXPos(i);
        				currentYpos = getFinalYPos(j);
        			}
    			}
    			else{
	    			if(movingY[number]<getFinalYPos(j) && movingY[number]!=0){//the number need to be up
	    				movingY[number] = movingY[number]+65;
	    				currentYpos=movingY[number];
	    				currentXpos = getFinalXPos(i);
	    			}else if(movingY[number]>getFinalYPos(j) && movingY[number]!=-1){//the number need to be down
	    				movingY[number] = movingY[number]-65;
	    				currentYpos=movingY[number];
	    				currentXpos = getFinalXPos(i);
	    			}
	    			else{//equal or greater then currentPos
	    				currentYpos = getFinalYPos(j);
	    				currentXpos = getFinalXPos(i);
	    			}
    			}
    		}//if moved numbers
    	  }//if moved ==-1
    	//}//else
    	fieldPosition[0]=currentXpos;
    	fieldPosition[1]=currentYpos;
    	
    	return fieldPosition; 
    }*/
    

    
/**
* animationMovement - creates the visual effect of the movement
* @param number - the number
* @param i - x grid position
* @param j - y grid position
*/   
public void animationMovement(int number,int i,int j,Bitmap b,Canvas c,Paint p){
    	
    	
    	movedFields = mGameView.getMovedFieldsArray();
    	int currentYpos=0,currentXpos=0;
    	int prevXPos,prevYPos;
    	int fieldPosition[] = new int[2];
   
         	
    	
    	 if (movedFields[0]!=-1)
    	 {	
    		if(number == movedFields[0] || number == movedFields[1]
    				 || number == movedFields[2] || number == movedFields[3]){
    			
    			prevXPos =getPrevXPos(number);
    			prevYPos =getPrevYPos(number);
    			
    			if(prevXPos != i) //change on y axis
    			{
    				if(movingX[number] < getFinalXPos(i) && movingX[number]!=0){//the number 
    					movingX[number] = movingX[number]+65;
    					currentXpos=movingX[number];
    					currentYpos = getFinalYPos(j);
        			}else if(movingX[number]>getFinalXPos(i) && movingX[number]!=-1){//the number going down
        				movingX[number] = movingX[number]-65;
        				currentXpos=movingX[number];
        				currentYpos = getFinalYPos(j);
        			}
        			else{//equal or greater then currentPos
        				currentXpos = getFinalXPos(i);
        				currentYpos = getFinalYPos(j);
        			}
    				
    			}
    			else //changes on y axis
    			{
	    			if(movingY[number] < getFinalYPos(j) && movingY[number] != 0){//the number going  down
	    				if(movingY[number]<getFinalYPos(j) && movingY[number]!=0){//the number need to be up
		    				movingY[number] = movingY[number]+65;
		    				currentYpos=movingY[number];
		    				currentXpos = getFinalXPos(i);
		    			}else if(movingY[number]>getFinalYPos(j) && movingY[number]!=-1){//the number need to be down
		    				movingY[number] = movingY[number]-65;
		    				currentYpos=movingY[number];
		    				currentXpos = getFinalXPos(i);
		    			}
		    			else{//equal or greater then currentPos
		    				currentYpos = getFinalYPos(j);
		    				currentXpos = getFinalXPos(i);
		    			}
	    				
	    				/*	if(movedFields[2]!=-1){
	    					
	    					if(number == movedFields[0]){
	    						movingY[number] = movingY[number]+65;
	    	    				currentYpos=movingY[number];
	    	    				currentXpos = getFinalXPos(i);
	    	    			}
	    					else if(number == movedFields[1] && (movingY[movedFields[0]] == getFinalYPos(3))){
	    						movingY[number] = movingY[number]+65;
	    	    				currentYpos=movingY[number];
	    	    				currentXpos = getFinalXPos(i);
	    	    			}
	    					else if(number == movedFields[2] && (movingY[movedFields[1]] == getFinalYPos(2))){
	    	    				movingY[number] = movingY[number]+65;
	    	    				currentYpos=movingY[number];
	    	    				currentXpos = getFinalXPos(i);
	    					}
	    					else if(number == 0){ //number == 0
	    						movingY[number] = movingY[number]+65;
	    	    				currentYpos=movingY[number];
	    	    				currentXpos = getFinalXPos(i);
	    					}
	    					else { 
	    						currentYpos=movingY[number];
	    	    				currentXpos = getFinalXPos(i);
	    					}
	    					
	    				}*///if more then two numbers to move 	
	    		//	}//if position< finalPosition
    			//}else if(movingY[number]>getFinalYPos(j) && movingY[number]!=-1){//the number need to be down
    				/*if(movedFields[2]!=-1){
    					
    					if(number == movedFields[0]){
    						movingY[number] = movingY[number]-65;
    	    				currentYpos=movingY[number];
    	    				currentXpos = getFinalXPos(i);
    	    			}
    					else if(number == movedFields[1] && (movingY[movedFields[0]] == getFinalYPos(0))){
    						movingY[number] = movingY[number]-65;
    	    				currentYpos=movingY[number];
    	    				currentXpos = getFinalXPos(i);
    	    			}
    					else if(number == movedFields[2] && (movingY[movedFields[1]] == getFinalYPos(1))){
    	    				movingY[number] = movingY[number]-65;
    	    				currentYpos=movingY[number];
    	    				currentXpos = getFinalXPos(i);
    					}
    					else if(number == 0){ //number == 0
    						movingY[number] = movingY[number]-65;
    	    				currentYpos=movingY[number];
    	    				currentXpos = getFinalXPos(i);
    					}
    					else { 
    						currentYpos=movingY[number];
    	    				currentXpos = getFinalXPos(i);
    					}
    					
    				}//if more then two numbers to move*/
    			
    			
    			//}
    			//else{//equal or greater then currentPos
    			//	currentYpos = getFinalYPos(j);
    			//	currentXpos = getFinalXPos(i);
    			//}
	    			
	    			
	    			
	    			
    			}//else y axis
    		}//if moved numbers
    	  }//if moved ==-1
    	}//else
    	 
    	fieldPosition[0]=currentXpos;
    	fieldPosition[1]=currentYpos;
    	saveXYPos(mGameView.game15puzzle.getFieldNumber(i, j),i,j);
    	
    	if(fieldPosition[0]!=0)
    	{
    		c.drawBitmap(b, fieldPosition[0],fieldPosition[1],p);
			movingX[mGameView.game15puzzle.getFieldNumber(i, j)] = fieldPosition[0];
			movingY[mGameView.game15puzzle.getFieldNumber(i, j)] = fieldPosition[1];	
		}
		else
		{
			c.drawBitmap(b, getFinalXPos(i),getFinalYPos(j),p);
			movingX[mGameView.game15puzzle.getFieldNumber(i, j)] = getFinalXPos(i);
			movingY[mGameView.game15puzzle.getFieldNumber(i, j)] = getFinalYPos(j);
		}
}
/**
* doDraw - draw the canvas
* @param canvas - the current canvas
*/   
public void doDraw(Canvas canvas){
    	
        Paint p = new Paint();
        p.setAntiAlias(false);
        p.setColor(Color.argb(255,100, 100, 255));
        p.setTextSize(52);
        Bitmap numberBitmap;
        //int movedFields[] = new int[4];
        //int positionField[] = new int[2];
        
        Bitmap mBgBitmap= BitmapFactory.decodeResource(mGameView.getResources(), R.drawable.bgnew);
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        canvas.drawText("Shake to Start a New Game", 50, 900, p);
        
        if(mGameView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
        {
        	x_pos = x_InitPos;
            y_pos = y_InitPos;
            //canvas.drawRect(110, 120, 640, 760, p);	             
        } 
        else 
            {
        	 x_pos = x_InitPosLand;
            y_pos = y_InitPosLand;
           // canvas.drawRect(350, 40, 880, 570, p);    
        }	
        
    	movedFields = mGameView.getMovedFieldsArray();
    	
        for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				
				switch(mGameView.game15puzzle.getFieldNumber(i, j)){
				
				case 1:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
				break;
				case 2:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
				break;
				case 3:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
				break;
				case 4:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
				break;
				case 5:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
				break;
				case 6:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);

					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
				break;
				case 7:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);

					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
				break;
				case 8:
					//numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
					
					break;
				case 9:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);

					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
					break;
				case 10:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);

					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
					break;
				case 11:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);

					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
					break;
				case 12:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
					break;
				case 13:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);

					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
					break;
				case 14:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);

					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
					break;
				case 15:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
					//canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
					break;
				case 0:
					numberBitmap = BitmapFactory.decodeResource(mGameView.getResources(),mBitmapsArray[mGameView.game15puzzle.getFieldNumber(i, j)]);
					//saveXYPos(mGameView.game15puzzle.getFieldNumber(i, j),3,3);	
	    			//canvas.drawBitmap(numberBitmap, getXPos(i),getYPos(j),p);
					/*positionField = animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
					if(positionField[0]!=0){
						canvas.drawBitmap(numberBitmap, positionField[0],positionField[1],p);
						movingX[mGameView.game15puzzle.getFieldNumber(i, j)] = positionField[0];
		    			movingY[mGameView.game15puzzle.getFieldNumber(i, j)] = positionField[1];	
					}
					else{
						saveXYPos(mGameView.game15puzzle.getFieldNumber(i, j),i,j);
						canvas.drawBitmap(numberBitmap, getFinalXPos(i),getFinalYPos(j),p);
						movingX[mGameView.game15puzzle.getFieldNumber(i, j)] = getFinalXPos(i);
		    			movingY[mGameView.game15puzzle.getFieldNumber(i, j)] = getFinalYPos(j);*/
					animationMovement(mGameView.game15puzzle.getFieldNumber(i, j),i,j,numberBitmap,canvas,p);
					
					
					//}
					break;
					
				}
				
			}
		}
        
    }
   }
