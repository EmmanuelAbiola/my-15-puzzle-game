package com.game.fifteenpuzzle;




import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread{
	
	private SurfaceHolder holder;
	private GameView mGameView;
    private boolean mRun = true;
    int x_pos=90,y_pos=240;
	int x_InitPos=90,y_InitPos=240;
	int x_InitPosLand=360,y_InitPosLand=50;
	int FinalPosX[] = new int[]{90,140,280,420};
	int FinalPosY[] = new int[]{240,140,280,420};
	final static private int NUM_ROWS     = 4;
    final static private int NUM_COLS     = 4;
     
	
	
	GameViewThread mGVThread;
	
	int movedFields[] = new int[]{-1,-1,-1,-1};
	int prevXpos[] = new int[16];
	int prevYpos[] = new int[16];
	int movingPosX[] = new int[16];
	int movingPosY[] = new int[16];
	
	Bitmap bmpNumbers[] = new Bitmap[17];
	Paint p = new Paint();
	
	int currentXpos[] = new int[16];
	int currentYpos[] = new int[16];
    int bg = 16; 
	
	//constructor
	public GameViewThread(SurfaceHolder holder,GameView GameView,Bitmap[] bmpNumbers) {
        this.holder = holder;
        mGameView = GameView;
        this.bmpNumbers = bmpNumbers;
       
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

    public void setRunning(boolean b) {
        mRun = b;
    }

   
    
    
    /**
     * XaxisAnimation - animate over the x axis
     */   
     public void XaxisAnimation(int number,int x_field,int y_field){
    	 
    	 if (prevXpos[number] < x_field && movingPosX[number] < getFinalXPos(x_field)){ //x-axis ,current position on the field < final position
				
    		 if (movedFields[1]!=-1 && movedFields[2]==0 && number == 0)
    			 currentXpos[number]=movingPosX[number]+10;
    		 else if(movedFields[2]!=-1 && number == 0)
    			 currentXpos[number]=movingPosX[number]+15;
    		 else	
    			 currentXpos[number]=movingPosX[number]+5;
    		 
    		 
    		 
    			currentYpos[number]=getFinalYPos(y_field);
				setXYCurrentPos(number,currentXpos[number],getFinalYPos(y_field));
				movingPosX[number] = currentXpos[number];
				movingPosY[number] = currentYpos[number];
				saveXYPos(number,prevXpos[number],y_field);
			}
			else if (prevXpos[number] > x_field && movingPosX[number] > getFinalXPos(x_field)){
				
				if (movedFields[1]!=-1 && movedFields[2]==0 && number == 0)
	    			 currentXpos[number]=movingPosX[number]-10;
	    		 else if(movedFields[2]!=-1 && number == 0)
	    			 currentXpos[number]=movingPosX[number]-15;
	    		 else	
	    			 currentXpos[number]=movingPosX[number]-5;
	    		
				
				currentYpos[number]=getFinalYPos(y_field);
				setXYCurrentPos(number,currentXpos[number],getFinalYPos(y_field));
				movingPosX[number] = currentXpos[number];
				movingPosY[number] = currentYpos[number];
				saveXYPos(number,prevXpos[number],y_field);
			}
			else{
				setXYCurrentPos(number,getFinalXPos(x_field),getFinalYPos(y_field));
				movingPosX[number] = getFinalXPos(x_field);
				movingPosY[number] = getFinalYPos(y_field);
				saveXYPos(number,x_field,y_field);
			} 
     }
     
     
     /**
      * YaxisAnimation - animate over the x axis
      */   
      public void YaxisAnimation(int number,int x_field,int y_field){
    	  
    	  if (prevYpos[number] < y_field && movingPosY[number] < getFinalYPos(y_field)){ //y-axis ,current position on the field < final position
				
    		     if (movedFields[1]!=-1 && movedFields[2]==0 && number == 0)
	    			 currentYpos[number]=movingPosY[number]+10;
	    		 else if(movedFields[2]!=-1 && number == 0)
	    			 currentYpos[number]=movingPosY[number]+15;
	    		 else	
	    			 currentYpos[number]=movingPosY[number]+5;
				
    		  
    		  	currentXpos[number]=getFinalXPos(x_field);
				setXYCurrentPos(number,getFinalXPos(x_field),currentYpos[number]);
				movingPosX[number] = currentXpos[number];
				movingPosY[number] = currentYpos[number];
				saveXYPos(number,x_field,prevYpos[number]);
			}
			else if (prevYpos[number] > y_field && movingPosY[number] > getFinalYPos(y_field)){
				
				if (movedFields[1]!=-1 && movedFields[2]==0 && number == 0)
	    			 currentYpos[number]=movingPosY[number]-10;
	    		 else if(movedFields[2]!=-1 && number == 0)
	    			 currentYpos[number]=movingPosY[number]-15;
	    		 else	
	    			 currentYpos[number]=movingPosY[number]-5;
				
				
				currentXpos[number]=getFinalXPos(x_field);
				setXYCurrentPos(number,getFinalXPos(x_field),currentYpos[number]);
				movingPosX[number] = currentXpos[number];
				movingPosY[number] = currentYpos[number];
				saveXYPos(number,x_field,prevYpos[number]);
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
             
            // Bitmap mBgBitmap= BitmapFactory.decodeResource(mGameView.getResources(), R.drawable.bgnew);
             canvas.drawBitmap(bmpNumbers[bg], 0, 0, null);
             //canvas.drawRect(0, 0, 800, 1000, p); 
             canvas.drawText("Shake to Start a New Game", 50, 900, p);
             
             if(mGameView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            	 FinalPosX[0] = x_InitPos;
            	 FinalPosY[0] = y_InitPos;
             } 
             else {
            	 FinalPosX[0] = x_InitPosLand;
            	 FinalPosY[0] = y_InitPosLand;
             }	
             
         	 for (int i = 0; i < NUM_ROWS * NUM_COLS; i++) {
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
