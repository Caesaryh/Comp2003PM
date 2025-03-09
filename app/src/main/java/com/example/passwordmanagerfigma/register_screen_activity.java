package com.example.passwordmanagerfigma;

/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		register_screen
	 *	@date 		Monday 03rd of March 2025 01:12:32 AM
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

public class register_screen_activity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_screen);


		View _rectangle_8 = findViewById(R.id._rectangle_8);
		View _rectangle_4 = findViewById(R.id._rectangle_4);


		_rectangle_8.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), register_successfully_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		_rectangle_4.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), login_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		//custom code goes here
	
	}
}
	
	