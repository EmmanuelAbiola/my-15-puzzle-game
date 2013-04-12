package com.game.fifteenpuzzle;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


/**
 * Listener that detects shake gesture occur.
 */
public class ShakeEventListener implements SensorEventListener {
  
  private float mLastX = 0;
  private float mLastY = 0;
  private float mLastZ = 0;

  //OnShakeListener that is called when shake is detected.
  private OnShakeListener mShakeListener;

  
  
  private final float NOISE = (float) 10.0;
  
  /**
   * Interface for shake.
   * onShake called when shake gesture is detected
   */
  public interface OnShakeListener 
  {
    void onShake();
  }

  public void setOnShakeListener(OnShakeListener listener) {
    mShakeListener = listener;
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
  
	// get sensor data
    float x = event.values[0];
    float y = event.values[1];
    float z = event.values[2];

    //get the difference between the measurements
    float deltaX = Math.abs(mLastX - x);
	float deltaY = Math.abs(mLastY - y);
	float deltaZ = Math.abs(mLastZ - z);
	
	//the noise should be a big value so the affect would be as a shaker
	if (deltaX < NOISE) 
		deltaX = (float)0.0;
	if (deltaY < NOISE) 
		deltaY = (float)0.0;
	if (deltaZ < NOISE) 
		deltaZ = (float)0.0;
	
	//set the values
	mLastX = x;
	mLastY = y;
	mLastZ = z;
	
	if (deltaX > deltaY || deltaY > deltaX) {
		 mShakeListener.onShake();
	} 
	    
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

}