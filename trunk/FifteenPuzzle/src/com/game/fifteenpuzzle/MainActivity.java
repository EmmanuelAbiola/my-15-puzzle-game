package com.game.fifteenpuzzle;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;



public class MainActivity extends Activity{

	Bitmap bmpNumbers[] = new Bitmap[17];
	Context mContext;
	gameField game15puzzle;
	
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener; 
	private int              pixelByCol;
    private int              pixelByRow;
    private Bitmap[]         choppedPictures          = new Bitmap[17];  
    private Bitmap bmpPicture;
    private String  puzzleType=   null;
    private String  hint=  "cancel";
    private String  tilt=  "addTilt"; 
    private GameView gv;
    private Bitmap bmpScaled;
    private MenuItem addHint,cancelHint;
    private MenuItem addTilt,cancelTilt;
    private boolean mInit = false;
    private int BMP_WIDTH = 140;
    private int BMP_HIGHT = 140;
    private int BMP_PIC_WIDTH = 160;
    private int BMP_PIC_HIGHT = 160;
    private int NUM_WIDTH = 115;
    private int NUM_HIGHT = 115;
    private int BG_PIC = 16;
    
    int x=0;
    int y=1;
    
    // the array of all of the numbers
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
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Bundle extras = getIntent().getExtras(); 
		choppedPictures[BG_PIC] =BitmapFactory.decodeResource(mContext.getResources(),mBitmapsArray[BG_PIC]);
		
		
		if(extras !=null)
		{
			// getting the tipe of the puzzle from the intent param
			puzzleType = extras.getString("type");
		}
		
		//load the number bmps
		for (int j = 0; j < bmpNumbers.length; j++) {	
			bmpNumbers[j] = BitmapFactory.decodeResource(mContext.getResources(),mBitmapsArray[j]);	
			if(j!= BG_PIC)
				bmpNumbers[j] = Bitmap.createScaledBitmap(bmpNumbers[j], NUM_WIDTH, NUM_HIGHT, false);
		}
		
		//check which game to create
		if(puzzleType.equalsIgnoreCase("Numbers") == true)
			gv = new GameView(this,bmpNumbers);// numbers game
		else{ // picture/photo game
			bmpPicture = BitmapFactory.decodeFile(puzzleType.toString());
			
			//scale the image and create picture array
			if(bmpPicture.getWidth() > bmpPicture.getHeight()){// landscape
				bmpScaled = Bitmap.createScaledBitmap(bmpPicture, BMP_PIC_WIDTH*4, BMP_HIGHT*4, false);
			}
			else if(bmpPicture.getWidth() < bmpPicture.getHeight()){// portrait
				bmpScaled = Bitmap.createScaledBitmap(bmpPicture, BMP_WIDTH*4, BMP_PIC_HIGHT*4, false);
			}
			else // equal
				bmpScaled = Bitmap.createScaledBitmap(bmpPicture, BMP_WIDTH*4, BMP_HIGHT*4, false);
			
			//create the chopped picture
			generateChoppedBitmap(bmpScaled); 
			gv = new GameView(this,choppedPictures);//start the photo or image game
		
		}
		setContentView(gv);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorListener = new ShakeEventListener();   
	 	mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
			
	 		/**
	 		 * onShake - shake the tiles on the screen
	 		 */
	 		public void onShake() {
	 			mInit =true;
				MediaPlayer shakeSound = new MediaPlayer();
				shakeSound = MediaPlayer.create(MainActivity.this, R.raw.shake);
				shakeSound.start();// play the sound
				gv.ShakeField();
				try {
					Thread.sleep(200);// should be delays otherwise cant hear the sound
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				shakeSound.stop();
				shakeSound.reset();
				shakeSound.release();
			}
		});
	 	
	 	
	 	mSensorListener.setOnTiltListener(new ShakeEventListener.OnTiltListener() {
			
	 		/**
	 		 * onTiltX - tilt tile on X axis
	 		 */
			public void onTiltX(int modeX) {
				int emptyPos[] = new int[2];
				Log.d("MOVE", String.format(" modeX = %d", (int)modeX));
				emptyPos = gv.getEmptyPosition();
				if(tilt.contentEquals("cancel") == false && mInit == true){  
					if(modeX == -1){
						if(emptyPos[x]-1 != -1){
							gv.MoveField(emptyPos[x]-1,emptyPos[y]);
						}
					}
	    			if(modeX == 1)
	    				gv.MoveField(emptyPos[x]+1,emptyPos[y]);
				}
			}
			/**
	 		 * onTiltY - tilt tile on Y axis
	 		 */
			public void onTiltY(int modeY) {
				int emptyPos[] = new int[2];
				Log.d("MOVE", String.format(" modeY = %d", (int)modeY));
				emptyPos = gv.getEmptyPosition();
				if(tilt.contentEquals("cancel") == false && mInit == true){  
					if(modeY == -1)
	    				gv.MoveField(emptyPos[x],emptyPos[y]+1);
	    			if(modeY == 1){
	    				if(emptyPos[y]-1 != -1){
	    					gv.MoveField(emptyPos[x],emptyPos[y]-1);
	    				}
	    			}
				}
			}
			
		});
	 	
	 	
	}
	/**
    * generateChoppedBitmap - function generate small images from the given bitmap
    * @param bmp - the picture to generate
	*/
	 private void generateChoppedBitmap(Bitmap bmp) {
	        
		 	
		 	BitmapFactory.Options opts = new BitmapFactory.Options();
	        opts.inScaled = false;
	        opts.inJustDecodeBounds = true;
	        
	        final int width = bmp.getWidth();
	        final int height = bmp.getHeight();

	        pixelByCol = width/4;
	        pixelByRow = height/4;
	        int i = 1;
	        for (int row = 0; row < 4; row++) {
	            for (int col = 0; col < 4; col++) {
	                int startx = pixelByCol * col;
	                int starty = pixelByRow * row;
	                if(i<BG_PIC)
	                	choppedPictures[i++] = Bitmap.createBitmap(bmp, startx, starty, pixelByCol, pixelByRow);
	                else{
	                	Bitmap bitmap = Bitmap.createBitmap(pixelByCol, pixelByRow, Bitmap.Config.ARGB_8888);
	                	bitmap.eraseColor(-1);
	                	choppedPictures[0] = bitmap;
	            	}
	            }
	        }
	  }
	
	 /**
	 * AddBitmapHint - create the label as a number at the corner of the tile
	 * @param bmp - the picture to generate
	 * @param i = the value to draw
	 * @param add_canel - action 
	*/
	 private void AddBitmapHint(Bitmap bmp,int i,boolean add_cancel) {
		 Paint paint = new Paint();
		 Canvas c = new Canvas (choppedPictures[i]); 
		 paint.setTextSize(35);
         paint.setColor(Color.WHITE);
         paint.setTypeface(Typeface.DEFAULT_BOLD); 
         paint.setShadowLayer(4, 1, 7, Color.BLACK);
         if(add_cancel==true){
			 if(hint.contentEquals("add") == true){
				c.drawText(String.valueOf(i),7,30,paint);
				
			 }
         }
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
	    addHint = menu.getItem(0);
	    cancelHint =  menu.getItem(1);
	    addTilt = menu.getItem(2);
	    cancelTilt =  menu.getItem(3);
		
	    menu.getItem(1).setVisible(false);
	    menu.getItem(2).setVisible(false);
	    if(puzzleType.equalsIgnoreCase("Numbers") == true){
	    	addHint.setVisible(false);
	    	cancelHint.setVisible(false);
		}
	    return true;
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	 
	     if(item.getItemId() == R.id.add_tilt){
	         tilt = "addTilt";
	         item.setVisible(false);
	         cancelTilt.setVisible(true);
	     } 
	     if(item.getItemId() == R.id.cancel_tilt){
	         tilt = "cancel";
	         item.setVisible(false);
	         addTilt.setVisible(true);
	     } 
	     if(item.getItemId() == R.id.add_hint){
	         hint = "add";
	         item.setVisible(false);
	         cancelHint.setVisible(true);
	         for(int i= 1;i<16;i++){
	        	 AddBitmapHint(choppedPictures[i],i,true);
	         }
	        } 
	        if(item.getItemId() == R.id.cancel_hint){
	       	 hint = "cancel";
	       	 item.setVisible(false);
	       	 addHint.setVisible(true);
	       	 generateChoppedBitmap(bmpScaled);
	        } 
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
	  
	  @Override
	    protected void onDestroy() {
		
		//recycle all bmps
			for (int j = 0; j < bmpNumbers.length; j++) {	
				bmpNumbers[j].recycle();// clear the bmp at the end
				bmpNumbers[j]= null;
				if(puzzleType.equalsIgnoreCase("Numbers") == false){
					choppedPictures[j].recycle();// clear the bmp at the end
					choppedPictures[j]= null;
				}
			}
			if(puzzleType.equalsIgnoreCase("Numbers") == false){
				bmpPicture.recycle();// clear the bmp at the end
				bmpPicture =null;
				bmpScaled.recycle();
				bmpScaled =null;
				
			}  
	        super.onDestroy();
	        Log.d(getClass().getName(), "onDestroy() called");
	    }
	  
	  
}//end of class