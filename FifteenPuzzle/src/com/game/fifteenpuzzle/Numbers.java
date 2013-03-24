package com.game.fifteenpuzzle;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

public class Numbers {
	

	private int pUp= 0;
	private int pDown = 0;
	private int pLeft = 0;
	private int pRight = 0;
	Drawable d;
	public Bitmap imgNum;
	private int id;
	private int coordinateX=0,coordinateY=0;
	public int img;
	
	public Numbers(Context context,int numId) {
		//initialization of numbers parameters (id, position relative to other numbers,position on the screen(X,Y),image
		switch(numId)
        {
        case 0:
			id = -1;
			pUp= 12; 
			pDown= 0; 
			pLeft= 15; 
			pRight= 0; 
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num16blank);
			img = com.game.fifteenpuzzle.R.drawable.num16blank;
			coordinateX =360; coordinateY =360;
			break;
        case 1:
        	id = numId;
			pUp= 0; 
			pDown= 5; 
			pLeft= 0; 
			pRight= 2; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num1);
			img = com.game.fifteenpuzzle.R.drawable.num1;
			coordinateX =0; coordinateY =0;
			break; 
        case 2:
        	id = numId;
			pUp= 0; 
			pDown= 6; 
			pLeft= 1; 
			pRight= 3; 	
			imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num2);
			coordinateX =120; coordinateY =0;
			img = com.game.fifteenpuzzle.R.drawable.num2;
			break; 
        case 3:
        	id = numId;
			pUp= 0; 
			pDown= 7; 
			pLeft= 2; 
			pRight= 4; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num3);
			img = com.game.fifteenpuzzle.R.drawable.num3;
			coordinateX =240; coordinateY =0;
			break; 
        case 4:
        	id = numId;
			pUp= 0; 
			pDown= 8; 
			pLeft= 0; 
			pRight= 2; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num4);
			img = com.game.fifteenpuzzle.R.drawable.num4;
			coordinateX =360; coordinateY =0;
			break; 
        case 5:
        	id = numId;
			pUp= 1; 
			pDown= 9; 
			pLeft= 0; 
			pRight= 6; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num5);
			img = com.game.fifteenpuzzle.R.drawable.num5;
			coordinateX =0; coordinateY =120;
			break; 
        case 6:
        	id = numId;
			pUp= 2; 
			pDown= 10; 
			pLeft= 5; 
			pRight= 7; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num6);
			img = com.game.fifteenpuzzle.R.drawable.num6;
			coordinateX =120; coordinateY =120;
			break; 
        case 7:
        	id = numId;
			pUp= 3; 
			pDown= 11; 
			pLeft= 6; 
			pRight= 8; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num7);
			img = com.game.fifteenpuzzle.R.drawable.num7;
			coordinateX =240; coordinateY =120;
			break; 
        case 8:
        	id = numId;
			pUp= 4; 
			pDown= 12; 
			pLeft= 7; 
			pRight= 0; 	
			imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num8);
			img = com.game.fifteenpuzzle.R.drawable.num8;
			coordinateX =360; coordinateY =120;
			break; 
        case 9:
        	id = numId;
			pUp= 5; 
			pDown= 13; 
			pLeft= 0; 
			pRight= 10; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num9);
			img = com.game.fifteenpuzzle.R.drawable.num9;
			coordinateX =0; coordinateY =240;
			break; 
        case 10:
        	id = numId;
			pUp= 6; 
			pDown= 14; 
			pLeft= 9; 
			pRight= 11; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num10);
			img = com.game.fifteenpuzzle.R.drawable.num10;
			coordinateX =120; coordinateY =240;
			break; 
        case 11:
        	id = numId;
			pUp= 7; 
			pDown= 15; 
			pLeft= 10; 
			pRight= 12; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num11);
			img = com.game.fifteenpuzzle.R.drawable.num11;
			coordinateX =240; coordinateY =240;
			break; 
        case 12:
        	id = numId;
			pUp= 8; 
			pDown= 13; 
			pLeft= 0; 
			pRight= 100; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num12);
			img = com.game.fifteenpuzzle.R.drawable.num12;
			coordinateX =360; coordinateY =240;
			break; 
        case 13:
        	id = numId;
			pUp= 9; 
			pDown= 0; 
			pLeft= 0; 
			pRight= 14; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num13);
			img = com.game.fifteenpuzzle.R.drawable.num13;
			coordinateX =0; coordinateY =360;
			break; 
        case 14:
        	id = numId;
			pUp= 10; 
			pDown= 0; 
			pLeft= 13; 
			pRight= 15; 	
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num14);
			img = com.game.fifteenpuzzle.R.drawable.num14;
			coordinateX =120; coordinateY =360;
			break; 
        case 15:
        	id = numId;
			pUp= 11; 
			pDown= 0; 
			pLeft= 14; 
			pRight= 100; 	
			img = com.game.fifteenpuzzle.R.drawable.num15;
			//imgNum = BitmapFactory.decodeResource(context.getResources(), com.game.fifteenpuzzle.R.drawable.num15);
			coordinateX =240; coordinateY =360;
			break; 
        }	
	}
	//get number id
	public int getId(){
		return id;
	}
	//get number img
	public int getNumImg(){
		return img;
	}
	//get number img
	public Bitmap getImg(){
		return imgNum;
	}
	//set the coordinate of the X
	void setCoordX(int newX) {
        coordinateX = newX;
    }
	//set the coordinate of the Y
	void setCoordY(int newY) {
        coordinateY = newY;
    }
	//get the coordinate of the X
	public int getCoordX() {
		return coordinateX;
	}
	//get the coordinate of the Y
	public int getCoordY(){
	     return coordinateY;
	}	
	//get number position (up,down,left,right)
	public int getUpperNumber(){
		return pUp;
	}
	public int getBottomNumber(){
		return pDown;
	}
	public int getLeftNumber(){
		return pLeft;
	}
	public int getRightNumber(){
		return pRight;
	}
	//set the number position(up,down,left,right)
	void setUpperNumber(int newUp) {
	        pUp = newUp;
	}
	void setBottomNumber(int newDown) {
        pDown = newDown;
	}
	void setLeftNumber(int newLeft) {
        pLeft = newLeft;
	}
	void setRightNumber(int newRight) {
        pRight = newRight;
	}
	
	}
	
	
	
