package com.example.passwordmanagerfigma;

// *	This content is generated from the API File Info.
// *	(Alt+Shift+Ctrl+I).
// *
// *	@desc
// *	@file 		manage_screen
// *	@date 		Wednesday 05th of March 2025 12:22:23 AM
// *	@title 		Page 5
// *	@author
// *	@keywords
// *	@generator 	Export Kit v1.3.figma
// *


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class manage_screen_activity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_screen);


		TextView ___ = findViewById(R.id.___);
		ImageView _image_38 = findViewById(R.id._image_38);
		ImageView _image_40 = findViewById(R.id._image_40);
		ImageView _image_42 = findViewById(R.id._image_42);
		ImageView _image_41 = findViewById(R.id._image_41);


		___.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), condition_activity.class);
            startActivity(nextScreen);


        });
		
		
		_image_38.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), login_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		_image_40.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), setting_activity.class);
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
		
		
		//custom code goes here
	
	}
}
	
	