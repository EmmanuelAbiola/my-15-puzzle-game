package com.game.fifteenpuzzle;

import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class BitmapFromGallery extends Activity{
	
private static int RESULT_LOAD_IMAGE = 1;
private static int RESULT_PHOTO_IMAGE = 2;
Context mContext;
Uri mCapturedImageURI;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.main);
        
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
		
        //EditText LoadImage = (EditText) findViewById(R.id.LoadPicture);
        //LoadImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
        
        Button buttonLoadNumbers = (Button) findViewById(R.id.buttonNumbers);
        buttonLoadNumbers.setOnClickListener(new View.OnClickListener() {
        //EditText LoadNumbers = (EditText) findViewById(R.id.Numbers);
       // LoadNumbers.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				  
				  Intent intent = new Intent(mContext, MainActivity.class);
				  intent.putExtra("type", "Numbers");
				  startActivity(intent);
				}
		});
        
        Button photoButton = (Button) this.findViewById(R.id.buttonPhoto);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	String fileName = "temp.jpg";  
                ContentValues values = new ContentValues();  
                values.put(MediaStore.Images.Media.TITLE, fileName);  
                mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);  

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);  
                
                //startActivityForResult(intent, CAPTURE_PICTURE_INTENT);
            	
                //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                 
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                //		MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                 
                startActivityForResult(cameraIntent, RESULT_PHOTO_IMAGE); 
            }
        });
    }

    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	Bitmap bmpPicture;
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			
			//ImageView imageView = (ImageView) findViewById(R.id.imgView);
			//imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			//bmpPicture = BitmapFactory.decodeFile(picturePath);
			
			Intent intent = new Intent(mContext, MainActivity.class);
			intent.putExtra("type", picturePath);
			startActivity(intent);
		
		}
    
		if (requestCode == RESULT_PHOTO_IMAGE && resultCode == RESULT_OK ) {  
			
			String[] filePathColumn = { MediaStore.Images.Media.DATA}; 
			Cursor cursor = getContentResolver().query(mCapturedImageURI,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
            
            Intent intent = new Intent(mContext, MainActivity.class);
			intent.putExtra("type", picturePath);
			startActivity(intent);
        }  
    
    }

}
