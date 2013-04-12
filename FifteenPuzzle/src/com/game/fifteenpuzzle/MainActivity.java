package com.game.fifteenpuzzle;


import com.game.fifteenpuzzle.BmpSettings;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;

import android.widget.ImageView;

public class MainActivity extends Activity{//,SensorEventListener{

	//private Numbers num;
	private SensorManager sensorManager;
	
	static float x10,y10,z10;
	static int num=0; 
	boolean InitFlag=true;
	static float initGyroX,initGyroY,initGyroZ;
	
	private Numbers[] numbers = new Numbers[16]; // array that holds the numbers
	BmpSettings myBmp[] = new BmpSettings[15];
	Context mContext;
	Drawable d;
	int x_pos=120,y_pos=240;
	int x_InitPos=120,y_InitPos=240;
	int x_InitPosLand=360,y_InitPosLand=50;
	
	gameField game15puzzle;
	Display display = null;
	
	//default resolution of the screen
	public static int defaultRes_X = 480;
	public static int defaultRes_Y = 600;
	
	//current resolution of the screen
	public static int currentWidth_X;
	public static int currentHeight_Y;
	
	
	public Bitmap imgNum;
	ImageView image;
	AnimationDrawable animation; 
	
	
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener; 
	  
	int[] mBmpArray = { R.drawable.num16blank,R.drawable.num1, R.drawable.num2,
            R.drawable.num3, R.drawable.num4, R.drawable.num5,
            R.drawable.num6, R.drawable.num7,
            R.drawable.num8, R.drawable.num9, R.drawable.num10,
            R.drawable.num11, R.drawable.num12,
            R.drawable.num13, R.drawable.num14,R.drawable.num15
	};
	

	@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final GameView gv = new GameView(this);
		
		mContext = getApplicationContext();
		setDefaultScreen(defaultRes_X,defaultRes_Y);
		
		//load the bmps
		for (int i = 0; i < 15; i++) {
			myBmp[i] = new BmpSettings(mContext.getResources(),mBmpArray[i]);
			
			
		setContentView(gv);
		 mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		 mSensorListener = new ShakeEventListener();   
	}
	mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

		      public void onShake() {
		    	  gv.ShakeField();
		    	  gv.postInvalidate();
		      }
		    });
}
	
	
	 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//set default screen resolution
	public static void setDefaultScreen(int x, int y) {
		defaultRes_X = x;
		defaultRes_Y = y;
	}
		//return the current resolution of the screen on Y axis
	public int getDisplayWidth_X() {
		//display.getSize(size); 
		return display.getWidth();
	}

	//return the current resolution of the screen on Y axis
	public int getDisplayHeight_Y() {
		//display.getSize(size); 
		return display.getHeight();
	}
	
	public int ScreenToField_X(int x) {
		if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){ 
			x = x - x_InitPos;
		}
		else
			x = x - x_InitPosLand;
		x = x / myBmp[0].getWidth();
		return x;
	}

	public int ScreenToField_Y(int y) {
		if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){ 
			y = y - y_InitPos;
		}
		else
			y = y - y_InitPosLand;
		y = y / myBmp[0].getWidth();
		return y;
	}
	
	//the dialog message by winning the game
	/*public void gameOverMsgOnScreen() {
		 AlertDialog.Builder ad = new AlertDialog.Builder(this);
		 ad.setTitle("15 Puzzle Game");
		 ad.setMessage("Congatulations !!!");
		 ad.setPositiveButton("Play again", new DialogInterface.OnClickListener() {

		  public void onClick(DialogInterface arg0, int arg1) {
			  // start over the activity when the OK button is clicked
			  finish();
			  Intent intent = new Intent(mContext, MainActivity.class);
	            startActivity(intent); //The method startActivity(Intent)
		  
		  }});
		 ad.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
		       
		  public void onClick(DialogInterface arg0, int arg1) {
			  // close app when the quit button is clicked
			  finish();
		  }});
		 ad.show();
	}*/

	
	/*@Override
    public boolean onTouch(View v, MotionEvent event) {
		 
		TranslateAnimation mAnimation = new TranslateAnimation(0,0,0,200);
	    mAnimation.setDuration(500);
	    mAnimation.setFillAfter(true);
	    mAnimation.setRepeatCount(-1);
	    mAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
	    int move[] = new int[4];
	    int pos[] = new int[2];
	    
	    animation = new AnimationDrawable();
	
	   //gameOverMsgOnScreen();
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if(event.getX() >= x_InitPos && event.getY() <= y_InitPos+(4*myBmp[0].getWidth())){
					if(game15puzzle.moveRect(ScreenToField_X((int)event.getX()),ScreenToField_Y((int) event.getY())) == true){
							move = game15puzzle.getMoveRectArray();
							pos = game15puzzle.getFieldPosition(move[0]);
							
							int i=0;
							String imgName;
							while(move[i]!=-1){
								imgName = "num" + move[i];
								if(move[i]==0)
									 imgName = "num16blank";
						        int id = getResources().getIdentifier(imgName, "drawable", getPackageName());
						        animation.addFrame(getResources().getDrawable(id), 1000);
						        i++;
						        if(move[i]==0)
									break;
							}
							
							if(v.getId() == image.getId()) {
						        image.setBackgroundDrawable(animation);
						        animation.setVisible(true, true);
						        animation.stop();
						        animation.start();
						    }
					        v.invalidate();
					}
							if(game15puzzle.IfGameOver()==true)
							{
								gameOverMsgOnScreen();
							}
				}
			}
			return true;
		}*/

	
	/*
     * This method will be called whenever the accuracy of the sensor has
     * changed.
     */
   // @Override
/*    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // left empty
    }*/

    /*
     * This method will be called whenever there are sensor events
     */
    //@Override
    /*public void onSensorChanged(SensorEvent event) {
    	float x,y,z;
    	
    	x=event.values[0];
        y=event.values[1];
        z=event.values[2];
        
        x10=x10+x;
        y10=y10+y;
        z10=z10+z;
        num = num+1;
    	
        if(InitFlag == true && num == 10){
    		initGyroX = (int)x10;
    		initGyroY = (int)y10;
    		initGyroZ = (int)z10;
    		InitFlag = false;
    	}
        if (num == 10){
        	Log.d(getClass().getName(), String.format("value x = %d", (int)x10));
        	Log.d(getClass().getName(), String.format("value y = %d", (int)y10));
        	Log.d(getClass().getName(), String.format("value z = %d", (int)z10));
        	num=0;
        	
        	if(InitFlag == false){
        		if((int)x10<initGyroX)
        		{
        			//slide down once 
        			int emptyX = game15puzzle.getEmptyX();
        			int emptyY = game15puzzle.getEmptyY();
        			game15puzzle.moveRect(emptyX,emptyY-1);
      
        			
        		}
        		if((int)x10>initGyroX)
        		{
        			//slide down once 
        		}       			
        	}
        	
        	
        	
        	
        }
        		
        
    }*/

    /*
     * It is good practice to unregister the sensor when not in use
     */
    //@Override
    /*protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }*/
	@Override
	  protected void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(mSensorListener,
	        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    mSensorManager.unregisterListener(mSensorListener);
	    super.onStop();
	  }
}//end of class