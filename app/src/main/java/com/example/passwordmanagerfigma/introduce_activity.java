package com.example.passwordmanagerfigma;

/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		introduce
	 *	@date 		Tuesday 04th of March 2025 02:22:17 AM
	 *	@title 		Page 5
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class introduce_activity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.introduce);


		ImageView _image_38 = findViewById(R.id._image_38);
		ImageView _image_42 = findViewById(R.id._image_42);
		View _rectangle_111 = findViewById(R.id._rectangle_111);


		_image_38.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), login_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		_image_42.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), login_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		_rectangle_111.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), setting_activity.class);
            startActivity(nextScreen);


        });
		
		
		//custom code goes here
	
	}
}
	
	