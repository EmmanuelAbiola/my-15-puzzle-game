package com.game.fifteenpuzzle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread{
	
	private SurfaceHolder holder;
	private GameView mGameView;
    private boolean mRun = true;
    private int FinalPosX_equal[] = new int[]{90,140,280,420};
    private int FinalPosY_equal[] = new int[]{240,140,280,420};
    
    private int FinalPosY_landscape[] = new int[]{240,140,280,420};
    private int FinalPosX_landscape[] = new int[]{40,160,320,480};
    
    private int FinalPosY_portrait[] = new int[]{180,160,320,480};
    private int FinalPosX_portrait[] = new int[]{80,140,280,420};
    
    private int FinalPosY[] = new int[4];
    private int FinalPosX[] = new int[4];
    
    final static private int NUM_ROWS     = 4;
    final static private int NUM_COLS     = 4;
	
    private int movedFields[] = new int[]{-1,-1,-1,-1};
    private int prevXpos[] = new int[16];
    private int prevYpos[] = new int[16];
    private int movingPosX[] = new int[16];
    private int movingPosY[] = new int[16];
    private Bitmap bmpNumbers[] = new Bitmap[17];
	private int currentXpos[] = new int[16];
	private int currentYpos[] = new int[16];
	private int BG_IDX = 16; 
	Paint p = new Paint();
	
	
	//constructor
	public GameViewThread(SurfaceHolder holder,GameView GameView,Bitmap[] bmpNumbers) {
        this.holder = holder;
        mGameView = GameView;
        this.bmpNumbers = bmpNumbers;
        
        //position in different modes on the canvas
        if(bmpNumbers[0].getWidth() > bmpNumbers[0].getHeight()){// landscape
        	FinalPosY = FinalPosY_landscape;
        	FinalPosX = FinalPosX_landscape;
		}
        else if(bmpNumbers[0].getWidth() < bmpNumbers[0].getHeight()){ // portrait
			FinalPosY = FinalPosY_portrait;
        	FinalPosX = FinalPosX_portrait;	
		}
        else{ // equal
        	FinalPosX = FinalPosX_equal;
        	FinalPosY = FinalPosY_equal;
        }
        	
        
    }

    @Override
    public void run() {
        while(mRun) {
            Canvas canvas = null;
            movedFields = mGameView.getMovedFieldsArray();
            try {
                 canvas = holder.lockCanvas(null);
                 SetXYtoDraw();
                 synchronized (holder) {
                	 if(canvas != null){
                		 doDraw(canvas);
                	 }
                }
            }
            finally {
                    if (canvas != null) {
                            holder.unlockCanvasAndPost(canvas);
                        }
                    
            }
        }
    }

    // set running variable
    public void setRunning(boolean b) {
        mRun = b;
    }

   /**
   * The function moved the tiles in different speed
   * @param number -  the tile number
   * @param direction (left ,right, top, down)
   * @param axis - x or y
   */ 
   public void speedMovementXY(int number,String direction,String axis){
	   
	   int speed[] = new int[]{5,10,15};
	   int currentPos[] = new int[16];
	   int movingPos[] = new int[16];
	   
	   //the correct array setup
	   if(axis== "x"){
		   currentPos = currentXpos;
		   movingPos = movingPosX;
	   }
	   else{
		   currentPos = currentYpos;
		   movingPos = movingPosY;
	   }
	   //direction on the x/y axis (+/-) value of speed)
	   if(direction == "left" || direction == "down"){
		
		 for(int i = 0;i<speed.length;i++){
			speed[i]= (-1)*speed[i];
		}   
	   }
	   
	   //movement with different speeds
	   if (movedFields[1]!=-1 && movedFields[2]==0 && number == 0)
		   currentPos[number]=movingPos[number]+speed[1];
		 else if(movedFields[2]!=-1 && number == 0)
			 currentPos[number]=movingPos[number]+speed[2];
		 else	
			 currentPos[number]=movingPos[number]+speed[0];
	   
   }
    
   /**
    * The function moved the tiles in different speed
    * @param number -  the tile number
    * @param x_field  - the x place of tile
    * @param y_field - the y place of tile
    * @param axis - x or y
    */   
   public void setCorrectPlaceXY(int number,int x_field,int y_field,String axis){
	   
	   int currentPos[] = new int[16];
	   int movingPos[] = new int[16];
	   
	   //the correct array setup
	   if(axis.equals("x")){
		   currentPos = currentXpos;
		   movingPos = movingPosX;
	   }
	   else{
		   currentPos = currentYpos;
		   movingPos = movingPosY;
	   }
	   
	    if(axis.equals("x")){
	    	currentYpos[number]=getFinalYPos(y_field);
			setXYCurrentPos(number,currentXpos[number],getFinalYPos(y_field));
		}
		else{
			currentXpos[number]=getFinalXPos(x_field);
			setXYCurrentPos(number,getFinalXPos(x_field),currentPos[number]);
		}
	    movingPos[number] = currentPos[number];
	    movingPos[number] = currentPos[number];
		
		if(axis.equals("x"))
			saveXYPos(number,prevXpos[number],y_field);
		else
			saveXYPos(number,x_field,prevYpos[number]);
			
   }
    
    /**
     * XaxisAnimation - animate over the x axis
     * @param number - the tile number
     * @param x_field - the position on x
     * @param y_field - the position on y
     */   
     public void XaxisAnimation(int number,int x_field,int y_field){//right
    	 
    	 if (prevXpos[number] < x_field && movingPosX[number] < getFinalXPos(x_field)){ //x-axis ,current position on the field < final position
				
    		 	speedMovementXY(number,"right","x");
    		 	setCorrectPlaceXY(number,x_field,y_field,"x");
    		}
			else if (prevXpos[number] > x_field && movingPosX[number] > getFinalXPos(x_field)){//left
				
				speedMovementXY(number,"left","x");
				setCorrectPlaceXY(number,x_field,y_field,"x");
			}
			else{
				setXYCurrentPos(number,getFinalXPos(x_field),getFinalYPos(y_field));
				movingPosX[number] = getFinalXPos(x_field);
				movingPosY[number] = getFinalYPos(y_field);
				saveXYPos(number,x_field,y_field);
			} 
     }
     
     
     /**
      * YaxisAnimation - animate over the y axis
      *@param number - the tile number
      *@param x_field - the position on x
      *@param y_field - the position on y
      */   
      public void YaxisAnimation(int number,int x_field,int y_field){
    	  
    	  if (prevYpos[number] < y_field && movingPosY[number] < getFinalYPos(y_field)){ //y-axis ,current position on the field < final position
				
    		  	speedMovementXY(number,"up","y");
    		  	setCorrectPlaceXY(number,x_field,y_field,"y");
    		}
			else if (prevYpos[number] > y_field && movingPosY[number] > getFinalYPos(y_field)){
				
				speedMovementXY(number,"down","y");
				setCorrectPlaceXY(number,x_field,y_field,"y");
			}
			else{
				setXYCurrentPos(number,getFinalXPos(x_field),getFinalYPos(y_field));
				movingPosX[number] = getFinalXPos(x_field);
				movingPosY[number] = getFinalYPos(y_field);
				saveXYPos(number,x_field,y_field);
			}
		}
      
    

    /**
    * SetXYtoDraw - set the position before drawing
    */   
    public void SetXYtoDraw(){
 
    	int number;
    	movedFields = mGameView.getMovedFieldsArray();
    	
        for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				
				number = mGameView.game15puzzle.getFieldNumber(i, j);
				
				if (movedFields[0]!=-1) // field touched
		    	{	
					if(prevXpos[number] != i)//x axis	
						XaxisAnimation(number,i,j);
					else //y axis	
						YaxisAnimation(number,i,j);
		    	}
				else{
					
					setXYCurrentPos(number,getFinalXPos(i),getFinalYPos(j));
					movingPosX[number] = getFinalXPos(i);
					movingPosY[number] = getFinalYPos(j);
					saveXYPos(number,i,j);
			
		    	 }			
			}
		}	
    }
 
	 /**
	 * setXYCurrentPos - set the position of the x,y fields
	 * @param i - 		x index
	 * @param j - 		y index
	 * @param number -  the number
	 */
	public void setXYCurrentPos(int number,int x,int y){
	    currentXpos[number] = x;
	    currentYpos[number] = y;
	}
	       
	/**
	* getXCurrentPos -returns current X position 
	* @param number - the number
	*/
	public int getXCurrentPos(int number){
	   	return currentXpos[number];
	}
	          
	/**
	* getXCurrentPos -returns current Y position 
	* @param number - the number
	*/
	public int getYCurrentPos(int number){
		return currentYpos[number];
	}
    /**
     * doDraw - draw the canvas
     * @param canvas - the current canvas
     */   
     public void doDraw(Canvas canvas){
         	
             
             p.setAntiAlias(false);
             p.setColor(Color.argb(255,100, 100, 255));
             p.setTextSize(52);
             int x,y;
             
             //canvas.drawColor(Color.LTGRAY);
             
             canvas.drawBitmap(bmpNumbers[BG_IDX], 0, 0, null);
             canvas.drawText("Shake to Start a New Game", 50, 900, p);
             
           
             
         	 for (int i = 0; i < 16; i++) {
         		 x=getXCurrentPos(i);
				 y=getYCurrentPos(i);
				 canvas.drawBitmap(bmpNumbers[i], x,y,p);
             }
         	 
         }
     
    
    
    /**
	 * getFinalXPos - Returns the final x-position on the field
	 * @param i - x index
	 * @return - x final position
	 */
    public int getFinalXPos(int i){
    	
    	int pos=0;
    	if(i == 0 )
			pos = FinalPosX[0];
		if(i == 1 )
			pos = FinalPosX[0]+FinalPosX[1];
		if(i == 2 )
			pos = FinalPosX[0]+FinalPosX[2];
		if(i == 3 )
			pos = FinalPosX[0]+FinalPosX[3];
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
			pos = FinalPosY[0];
		if(j == 1 )
			pos = FinalPosY[0]+FinalPosY[1];
		if(j == 2 )
			pos = FinalPosY[0]+FinalPosY[2];
		if(j == 3 )
			pos = FinalPosY[0]+FinalPosY[3];
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
}
