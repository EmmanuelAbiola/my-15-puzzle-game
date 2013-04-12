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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.app.AlertDialog;

public class GameView extends View {

	
	Context mContext;
	Canvas mCanvas;
	Drawable d;
	int x_pos=120,y_pos=240;
	int x_InitPos=120,y_InitPos=240;
	int x_InitPosLand=360,y_InitPosLand=50;
	gameField game15puzzle;
	BmpSettings myBmp[] = new BmpSettings[15];
	int _currentX;
	int _currentY;
	AnimationDrawable animation;
	
	//the R.drawable for all images  
	int[] mBmpArray = { R.drawable.num16blank,R.drawable.num1, R.drawable.num2,
            R.drawable.num3, R.drawable.num4, R.drawable.num5,
            R.drawable.num6, R.drawable.num7,
            R.drawable.num8, R.drawable.num9, R.drawable.num10,
            R.drawable.num11, R.drawable.num12,
            R.drawable.num13, R.drawable.num14,R.drawable.num15
	};
	
	//Constructor
	   public GameView(Context context) {
	      super(context);
	      mContext = context;
	      if(game15puzzle ==null){
	    	  game15puzzle = new gameField();
	      }
	      //load the bmps
		  for (int i = 0; i < 15; i++) {
			 myBmp[i] = new BmpSettings(mContext.getResources(),mBmpArray[i]);
		  }
	   }

	   @Override
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
			x = x / myBmp[0].getWidth();
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
			y = y / myBmp[0].getWidth();
			return y;
		}
	   
	   /**
		* The function that called after shake detection, create the random field 
		*/
		public void ShakeField()
		{
			game15puzzle.CreateRandomField();
		}
		
	   /**
		* The function that called upon tough screen event 
		* @param event - motion event
		*  
		*/	
	   @Override
	    public boolean onTouchEvent(MotionEvent event) {
			 
			TranslateAnimation mAnimation = new TranslateAnimation(0,0,0,200);
		    mAnimation.setDuration(500);
		    mAnimation.setFillAfter(true);
		    mAnimation.setRepeatCount(-1);
		    mAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
		    int move[] = new int[4];
		    int pos[] = new int[2];
		    int pos1[] = new int[2];
		    int i=0;
			String imgName;
		    
			animation = new AnimationDrawable();
		
		   //gameOverMsgOnScreen();
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if(event.getX() >= x_InitPos && event.getY() <= y_InitPos+(4*myBmp[0].getWidth())){
						if(game15puzzle.moveRect(ScreenToField_X((int)event.getX()),ScreenToField_Y((int) event.getY())) == true){
							_currentX = (int)event.getX();
						    _currentY = (int)event.getY();
								move = game15puzzle.getMoveRectArray();
								pos = game15puzzle.getFieldPosition(move[0]);
								pos1 = game15puzzle.getFieldPosition(move[1]);
								
								while(move[i]!=-1){
									imgName = "num" + move[i];
									if(move[i]==0)
										 imgName = "num16blank";
							       // int id = getResources().getIdentifier(imgName, "drawable", getPackageName());
							       // animation.addFrame(getResources().getDrawable(id), 1000);
							        i++;
							        if(move[i]==0)
										break;
								}
									/*if(pos[0]==pos1[0]){//x same
										Paint p = new Paint();
								         p.setAntiAlias(false);
								         p.setColor(Color.argb(255, 255, 255, 0));
										 mCanvas.drawRect(350, 40, 880, 570,p );
										
										
										
									}*/
								//if(v.getId() == image.getId()) {
							     //   image.setBackgroundDrawable(animation);
							    //    animation.setVisible(true, true);
							    //    animation.stop();
							    //    animation.start();
							   // }
									
									//AnimationDrawable animation = new AnimationDrawable();

							/*		TranslateAnimation _tAnim = new TranslateAnimation(-50, 0, 0, 0);
								    _tAnim.setInterpolator(new BounceInterpolator());
								    _tAnim.setDuration(1000);
								 
								    startAnimation(_tAnim);	
									
									
								*/
									
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