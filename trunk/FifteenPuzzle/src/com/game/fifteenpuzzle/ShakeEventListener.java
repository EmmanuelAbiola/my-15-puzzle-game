package com.game.fifteenpuzzle;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;


/**
 * Listener that detects shake gesture occur.
 */
public class ShakeEventListener implements SensorEventListener {
  
  private float mLastX = 0;
  private float mLastY = 0;
  private float mLastZ = 0;
  static  int modeX = 0; 
  static  int modeY = 0; 
  static  int afterShakeCounter = 0; 
  private boolean mInit = true;
  
  //OnShakeListener that is called when shake is detected.
  private OnShakeListener mShakeListener;
  private OnTiltListener mTiltListener;

  
  
  private final float SHAKE_NOISE = (float) 10.0;
  
  /**
   * Interface for shake.
   * onShake called when shake gesture is detected
   */
  public interface OnShakeListener 
  {
    void onShake();
  }
 
  /**
   * Interface for tilt.
   * onTilt called when tilt gesture is detected
   */ 
  public interface OnTiltListener 
  {
    void onTiltX(int modeX);
    void onTiltY(int modeY);    
  }

  public void setOnShakeListener(OnShakeListener listener) {
    mShakeListener = listener;
  }

  public void setOnTiltListener(OnTiltListener listener) {
	    mTiltListener = listener;
	  }
  @Override
  public void onSensorChanged(SensorEvent event) {
  
	// get sensor data
    float x = event.values[0];
    float y = event.values[1];
    float z = event.values[2];
    
    
    // hysteresis filter  
    // the value on x,y is b/w 1-3 do nothing
    // the value on x,y is -1-1 back to stable position
    // the value on x,y is >3 or <-3 move right/lest ,top/down
    
    // X AXIS
    
    if(x>=3 && modeX == 0){//left
    	modeX = 1;
    	Log.d("move left", String.format(" x = %d modeX = %d", (int)x,(int)modeX));
    	mTiltListener.onTiltX(modeX);
    }
    if(x<=-3 && modeX == 0){//right
    	modeX = -1;
    	Log.d("move right", String.format(" x = %d modeX = %d", (int)x,(int)modeX));
    	mTiltListener.onTiltX(modeX); 
    }
    if(x<=1 && modeX == 1)
    	modeX = 0;
    if(x>=-1 && modeX == -1)
    	modeX = 0;
    
    // Y AXIS
    
    if(y>=3 && modeY == 0){//left
    	modeY = 1;
    	Log.d("move left", String.format(" y = %d modeY = %d", (int)y,(int)modeY));
    	mTiltListener.onTiltY(modeY);
    }
    if(y<=-3 && modeY == 0){//right
    	modeY = -1;
    	Log.d("move left", String.format(" y = %d modeY = %d", (int)y,(int)modeY));
    	mTiltListener.onTiltY(modeY); 
    }
    if(y<=1 && modeY == 1)
    	modeY = 0;
    if(y>=-1 && modeY == -1)
    	modeY = 0;
   
    
    
    //get the difference between the measurements
    float deltaX = Math.abs(mLastX - x);
	float deltaY = Math.abs(mLastY - y);
	float deltaZ = Math.abs(mLastZ - z);
	
	//the noise should be a big value so the affect would be as a shaker
	if (deltaX < SHAKE_NOISE) 
		deltaX = (float)0.0;
	if (deltaY < SHAKE_NOISE) 
		deltaY = (float)0.0;
	if (deltaZ < SHAKE_NOISE) 
		deltaZ = (float)0.0;
	
	//set the values
	mLastX = x;
	mLastY = y;
	mLastZ = z;
	
	// only when the difference b/w x,y is really big than call shake
	if (deltaX > deltaY || deltaY > deltaX) {
		 mShakeListener.onShake();
	} 
	    
  }

  
  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

}