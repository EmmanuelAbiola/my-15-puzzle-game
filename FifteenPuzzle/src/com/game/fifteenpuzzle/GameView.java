package com.game.fifteenpuzzle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.lang.Thread;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
//import android.widget.ImageView;
//import android.app.AlertDialog;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	
	Context mContext;
	Canvas mCanvas;
	int x_pos=120,y_pos=240;
	int x_InitPos=120,y_InitPos=240;
	int x_InitPosLand=360,y_InitPosLand=50;
	gameField game15puzzle;
	BmpSettings myBmp[] = new BmpSettings[15];
	SurfaceHolder mSurfaceHolder;
	GameViewThread mGVThread;
	public int movedFieldsArray[] = new int[]{-1,-1,-1,-1};
	Bitmap bmpNumbers[] = new Bitmap[17];
	int mBmpWidth;
	
	//Constructor
	   public GameView(Context context,Bitmap[] bmpNumbers) {
	      super(context);
	      mContext = context;
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
	    	mGVThread = new GameViewThread(holder,this,bmpNumbers);
	    	mGVThread.setRunning(true);
	    	mGVThread.start();
	    }
	   
	   @Override
	    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	        // TODO Auto-generated method stub
	    }

	 

	    @Override
	    public void surfaceDestroyed(SurfaceHolder holder) {
	        // TODO Auto-generated method stub
	    	boolean retry = true;
	    	mGVThread.setRunning(false);
	        while (retry) {
	            try {
	            	mGVThread.join();
	                retry = false;
	            } catch (InterruptedException e) {
	            	Log.v("Exception Occured", e.getMessage());
	            }
	        }
	        }
	  
	    
	  /* @Override
	     protected void onDraw(Canvas canvas) {
	         super.onDraw(canvas);
	         mCanvas = canvas;
	         Paint p = new Paint();
	         p.setAntiAlias(false);
	         p.setColor(Color.argb(255,100, 100, 255));
	         p.setTextSize(52);
	         
	         Bitmap mBgBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.bgnew);
	         canvas.drawBitmap(mBgBitmap, 0, 0, null);
	         canvas.drawText("Shake to Start a New Game", 50, 900, p);
	         
	         if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
	         {
	         	x_pos = x_InitPos;
	             y_pos = y_InitPos;
	             canvas.drawRect(110, 230, 640, 760, p);	             
	         } 
	         else 
	             {
	         	 x_pos = x_InitPosLand;
	             y_pos = y_InitPosLand;
	             canvas.drawRect(350, 40, 880, 570, p);    
	         }	
	         
	         //drawing all field
	         for (int i = 0; i < 4; i++) {
	 			for (int j = 0; j < 4; j++) {
	 				d = getResources().getDrawable(mBmpArray[game15puzzle.getFieldNumber(i, j)]);
	 				d.setBounds(x_pos,y_pos ,x_pos+myBmp[0].getWidth()+10, y_pos+myBmp[0].getWidth()+10);
	 				y_pos=y_pos+myBmp[0].getWidth()+10;
	 				d.draw(canvas);	 					
	 			}//inner loop
	 			x_pos=x_pos+myBmp[0].getWidth()+10;
	 			if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){ 
	 				y_pos=y_InitPos;
	            }
	 			else
	 				y_pos=y_InitPosLand;
	 				d.draw(canvas);
	         }//outer for 	        
	     }//onDraw
*/
	   
	   /**
		 * The dialog that appears at the end of the game
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
				  Intent intent = new Intent(mContext, MainActivity.class);
				  ((MainActivity) getContext()).startActivity(intent); //The method startActivity(Intent)
			  
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
			x = x / bmpNumbers[0].getWidth();
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
			y = y / bmpNumbers[0].getWidth();
			return y;
		}
	   
	   /**
		* The function that called after shake detection, create the random field 
		*/
		public void ShakeField()
		{
			game15puzzle.CreateRandomField();
		}
		
		public void MoveField(int x,int y)
		{
			game15puzzle.moveRect(x,y);
			movedFieldsArray = game15puzzle.getMoveRectArray();	
			invalidate();
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
			 
			int i=0;
			
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if(event.getX() >= x_InitPos && event.getY() <= y_InitPos+(4*bmpNumbers[0].getWidth())){
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