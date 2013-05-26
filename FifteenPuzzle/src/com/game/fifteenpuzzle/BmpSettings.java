package com.game.fifteenpuzzle;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class BmpSettings {
	
	
	protected Bitmap m_bmp;
	protected int bmpPosX = 0, bmpPosY=0;
	
	//constructor
	//loading icons from the assets folder
	public BmpSettings(String bmpName, AssetManager am) {
		try {
			this.m_bmp = BitmapFactory.decodeStream(am.open(bmpName));
		} catch (Exception e) {
			m_bmp = null;
		}
	}
	
	//constructor 
	//loading icons from the res folder
	public BmpSettings(Resources Res, int bmpID) {
		this.m_bmp = BitmapFactory.decodeResource(Res, bmpID);
	}
	
	//return the height of the bmp
	public int getHeight() {
		return getBmp().getHeight();
	}

	//return the width of the bmp
	public int getWidth() {
		return getBmp().getWidth();
	}
	
	//return the bmp
	public Bitmap getBmp() {
		return m_bmp;
	}	
	
	public void setBmpPosition(int x,int y){
		
		bmpPosX = x;
		bmpPosY = y;
	}
	
	//return the x Position of the bmp
	public int getBmpXpos() {
		return bmpPosX;
	}
		//return the y position of the bmp
	public int getBmpYpos() {
		return bmpPosY;
	}
}
