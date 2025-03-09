package com.example.passwordmanagerfigma;

/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		setting
	 *	@date 		Monday 03rd of March 2025 11:36:39 PM
	 *	@title 		Page 5
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class setting_activity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);


		ImageView _image_38 = findViewById(R.id._image_38);
		ImageView _image_42 = findViewById(R.id._image_42);
		ImageView _image_41 = findViewById(R.id._image_41);
		ImageView _image_50 = findViewById(R.id._image_50);


		_image_38.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), login_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		_image_42.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), login_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		_image_41.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), introduce_activity.class);
            startActivity(nextScreen);


        });
		
		
		_image_50.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), photo_activity.class);
            startActivity(nextScreen);


        });
		
		
		//custom code goes here
	
	}
}
	
	