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

	Bitmap bmpNumbers[] = new Bitmap[17];
	Context mContext;
	gameField game15puzzle;
	
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener; 
	  
	
	int[] mBitmapsArray = { com.game.fifteenpuzzle.R.drawable.num16blank,com.game.fifteenpuzzle.R.drawable.num1, com.game.fifteenpuzzle.R.drawable.num2,
			com.game.fifteenpuzzle.R.drawable.num3, com.game.fifteenpuzzle.R.drawable.num4, com.game.fifteenpuzzle.R.drawable.num5,
			com.game.fifteenpuzzle.R.drawable.num6, com.game.fifteenpuzzle.R.drawable.num7,
			com.game.fifteenpuzzle.R.drawable.num8, com.game.fifteenpuzzle.R.drawable.num9, com.game.fifteenpuzzle.R.drawable.num10,
			com.game.fifteenpuzzle.R.drawable.num11, com.game.fifteenpuzzle.R.drawable.num12,
			com.game.fifteenpuzzle.R.drawable.num13, com.game.fifteenpuzzle.R.drawable.num14,com.game.fifteenpuzzle.R.drawable.num15,com.game.fifteenpuzzle.R.drawable.bgnew
	};  

	
	@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		
		//load the bmps
		for (int j = 0; j < bmpNumbers.length; j++) {	
			bmpNumbers[j] = BitmapFactory.decodeResource(mContext.getResources(),mBitmapsArray[j]);	
		}
		
		final GameView gv = new GameView(this,bmpNumbers);
		setContentView(gv);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorListener = new ShakeEventListener();   
	 	mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
			public void onShake() {
				gv.ShakeField();
			    gv.postInvalidate();
			}
		});
	 	mSensorListener.setOnTiltListener(new ShakeEventListener.OnTiltListener() {
			
			public void onTiltX(int modeX) {
				int emptyPos[] = new int[2];
				Log.d("MOVE", String.format(" modeX = %d", (int)modeX));
				emptyPos = gv.getEmptyPosition();
				if(modeX == -1){
					if(emptyPos[0]-1 != -1){
						gv.MoveField(emptyPos[0]-1,emptyPos[1]);
					}
				}
    			if(modeX == 1)
    				gv.MoveField(emptyPos[0]+1,emptyPos[1]);
				
			}
			public void onTiltY(int modeY) {
				int emptyPos[] = new int[2];
				Log.d("MOVE", String.format(" modeY = %d", (int)modeY));
				emptyPos = gv.getEmptyPosition();
				  
				if(modeY == -1)
    				gv.MoveField(emptyPos[0],emptyPos[1]+1);
    			if(modeY == 1){
    				if(emptyPos[1]-1 != -1){
    					gv.MoveField(emptyPos[0],emptyPos[1]-1);
    				}
    			}
			}
			
		});

	 	
	 	
	 	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
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