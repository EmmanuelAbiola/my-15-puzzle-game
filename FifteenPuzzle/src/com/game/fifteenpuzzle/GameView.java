package com.game.fifteenpuzzle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	
	Context mContext;
	Canvas mCanvas;
	int x_pos=120,y_pos=240;
	int x_InitPos=90,y_InitPos=240;
	int x_InitPosLand=360,y_InitPosLand=50;
	gameField game15puzzle;
	//BmpSettings myBmp[] = new BmpSettings[15];
	SurfaceHolder mSurfaceHolder;
	GameViewThread mGVThread;
	public int movedFieldsArray[] = new int[]{-1,-1,-1,-1};
	Bitmap bmpNumbers[] = new Bitmap[17];
	Bitmap bmpPicture;
	int mBmpWidth;
	int mBmpHight;
	private int BMP_WIDTH = 140;
    private int BMP_HIGHT = 140;
    private int BMP_LAND_WIDTH = 160;
    private int BMP_PORT_HIGHT = 160;
	
	//Constructor
	   public GameView(Context context,Bitmap[] bmpNumbers) {
	      super(context);
	      mContext = context;
	      if(bmpNumbers[0].getWidth() > bmpNumbers[0].getHeight()){// landscape
			  mBmpHight = BMP_HIGHT;
	    	  mBmpWidth = BMP_LAND_WIDTH;
			}
	      else if (bmpNumbers[0].getWidth() < bmpNumbers[0].getHeight()){// portrait
			    mBmpHight = BMP_PORT_HIGHT;
		    	mBmpWidth = BMP_WIDTH;
		  }
	      else{//equal  
	    	  mBmpHight = BMP_HIGHT;
		      mBmpWidth = BMP_WIDTH;
	      }
	    	  
	      if(game15puzzle ==null){
	    	  game15puzzle = new gameField();
	      }
		  mSurfaceHolder =getHolder();
		  mSurfaceHolder.addCallback(this);
		  this.bmpNumbers = bmpNumbers;
	   }
	 
	   @Override
	    public void surfaceCreated(SurfaceHolder holder) {
	        // TODO Auto-generated method stub
	    	mGVThread = new GameViewThread(holder,this,bmpNumbers);//create the drawing thread class
	    	mGVThread.setRunning(true);//set the running variable to true
	    	mGVThread.start();//start the thread
	    }

	    @Override
	    public void surfaceDestroyed(SurfaceHolder holder) {
	        // TODO Auto-generated method stub
	    	boolean retry = true;
	    	mGVThread.setRunning(false);
	        while (retry) {
	            try {
	            	mGVThread.join();// wait till all thread are finished their work
	                retry = false;
	            } catch (InterruptedException e) {
	            	Log.v("Exception Occured", e.getMessage());
	            }
	        }
	        }
	  
	    @Override
	    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	        // TODO Auto-generated method stub
	    }
	   /**
		 * The dialog that appears at the end of the game
		 * Congratulation !!!
		 *  
		 */
		public void gameOverMsgOnScreen() {
			 AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
			 ad.setTitle("15 Puzzle Game");
			 ad.setMessage("Congatulations !!!");
			 ad.setPositiveButton("Play again", new DialogInterface.OnClickListener() {

			  public void onClick(DialogInterface arg0, int arg1) {
				  // start over the activity when the OK button is clicked
				  ((MainActivity) getContext()).finish();
			  }});
			 ad.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
			       
			  public void onClick(DialogInterface arg0, int arg1) {
				  // close app when the quit button is clicked
				  ((MainActivity) getContext()).finish();
				  
				  
			  }});
			 ad.show();
		}
	   

	/**
	   * The function that convert the x coordinates to field coordinates
	   * @param x - screen(pixels) coordinates
	   * @return - x field coordinate 
	   */
	   public int ScreenToField_X(int x) {
			if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){ 
				x = x - x_InitPos;
			}
			else
				x = x - x_InitPosLand;
			x = x / mBmpWidth;
			return x;
		}
	   /**
		* The function that convert the y coordinates to field coordinates
		* @param y - screen(pixels) coordinates
		* @return - y field coordinate 
		*/
		public int ScreenToField_Y(int y) {
			if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){ 
				y = y - y_InitPos;
			}
			else
				y = y - y_InitPosLand;
			y = y / mBmpHight;
			return y;
		}
	   
	   /**
		* The function that called after shake detection, create the random field 
		*/
		public void ShakeField()
		{
			game15puzzle.CreateRandomField();
			invalidate();
		}
		
		public void MoveField(int x,int y)
		{
			game15puzzle.moveRect(x,y);
			movedFieldsArray = game15puzzle.getMoveRectArray();	
			invalidate();
			if(game15puzzle.IfGameOver()==true)
			{
				gameOverMsgOnScreen();
			}
		}
		
		public int[] getMovedFieldsArray() {
			   return movedFieldsArray;
		}
		
		/**
		* The function that returns an empty cell position
		*/
		public int[] getEmptyPosition()
		{
			int emptyPos[] = new int[2];
			emptyPos[0] = game15puzzle.getEmptyX();
			emptyPos[1] = game15puzzle.getEmptyY();
			return emptyPos;
		}
		
		
	   /**
		* The function that called upon tough screen event 
		* @param event - motion event
		*  
		*/	
	   @Override
	    public boolean onTouchEvent(MotionEvent event) {
			 
			
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if((event.getX() >= x_InitPos && event.getX() <= x_InitPos+(4*mBmpWidth)) &&
							(event.getY() >= y_InitPos && event.getY() <= y_InitPos+(4*mBmpHight))){
						if(game15puzzle.moveRect(ScreenToField_X((int)event.getX()),ScreenToField_Y((int) event.getY())) == true){
						    movedFieldsArray = game15puzzle.getMoveRectArray();						
							invalidate();
						}
						if(game15puzzle.IfGameOver()==true)
						{
							gameOverMsgOnScreen();
						}
					}
				}
				return true;
			}
	   
	}