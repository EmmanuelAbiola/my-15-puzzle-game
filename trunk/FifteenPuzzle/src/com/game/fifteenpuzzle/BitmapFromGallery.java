package com.game.fifteenpuzzle;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class BitmapFromGallery extends Activity{
	
private static int RESULT_LOAD_IMAGE = 1;
private static int RESULT_PHOTO_IMAGE = 2;

private Context mContext;
private Uri mCapturedImageURI;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.main);
        
        //CLICK ON NUMBERS BUTTON
        Button buttonLoadNumbers = (Button) findViewById(R.id.buttonNumbers);
        buttonLoadNumbers.setOnClickListener(new View.OnClickListener() {
        
        @Override
		public void onClick(View arg0) {
				  
        		  //intent the numbers game	
				  Intent intentNum = new Intent(mContext, MainActivity.class);
				  intentNum.putExtra("type", "Numbers");
				  startActivity(intentNum);
				}
		});
      //CLICK ON IMAGE BUTTON
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
			
        @Override
		public void onClick(View arg0) {
        	    //intent the picture gallery game	
				Intent i = new Intent(
				Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
        //CLICK ON PHOTO IMAGE BUTTON
        Button photoButton = (Button) this.findViewById(R.id.buttonPhoto);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	
                ContentValues values = new ContentValues();  
                values.put(MediaStore.Images.Media.TITLE, "");  
                mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);  
                //intent the picture photo game	
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);   
                startActivityForResult(cameraIntent, RESULT_PHOTO_IMAGE); 
            }
        });
    }//onCreate

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	//load image from the gallery result
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			//start the gallery picture game
			Intent intent = new Intent(mContext, MainActivity.class);
			intent.putExtra("type", picturePath);
			startActivity(intent);
		
		}//if image
		
		//load photo result
		if (requestCode == RESULT_PHOTO_IMAGE && resultCode == RESULT_OK ) {  
			String[] filePathColumn = { MediaStore.Images.Media.DATA}; 
			Cursor cursor = getContentResolver().query(mCapturedImageURI,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			//start the photo game
            Intent intent = new Intent(mContext, MainActivity.class);
			intent.putExtra("type", picturePath);
			startActivity(intent);
        } //if photo
		
		
    
    }

}
