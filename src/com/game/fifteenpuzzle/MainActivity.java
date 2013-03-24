package com.game.fifteenpuzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	//private Numbers num;
	private Numbers[] numbers = new Numbers[16]; // array that holds the numbers
	Context mContext;
	Drawable d;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		 
		for(int i=0;i<16;i++)
		    	numbers[i] =  new Numbers(mContext,i);
		
		/*
         * Create an instance of an anonymous class whose base class is
         * android.view.View and whose method onDraw() is overridden.
         */
        View rootView = new View(this) {

            /*
             * Method onDraw() will be called by Android whenever the view needs
             * to be redrawn. It is given a Canvas object that can be used for
             * drawing operations.
             */
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                for(int i=0;i<16;i++){ 
                	d = getResources().getDrawable(numbers[i].getNumImg());
                	d.setBounds(numbers[i].getCoordX(),numbers[i].getCoordY() ,numbers[i].getCoordX()+120, numbers[i].getCoordY()+120);
                	d.draw(canvas);
                }
            }

        };
		
		setContentView(rootView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void randomizeNumbers()
	{
		int tempNum=0,temp;
		for(int i=0;i<16;i++){
			if (numbers[i].getRightNumber() == -1)
				tempNum = numbers[++i].getRightNumber();
				numbers[i].setRightNumber(tempNum);
				
			
		}

	}
}