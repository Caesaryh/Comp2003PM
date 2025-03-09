package com.example.passwordmanagerfigma;

/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		login_screen
	 *	@date 		Sunday 02nd of March 2025 10:06:03 PM
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

public class login_screen_activity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);


		View _rectangle_5 = findViewById(R.id._rectangle_5);
		View _rectangle_8 = findViewById(R.id._rectangle_8);


		_rectangle_5.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), register_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		_rectangle_8.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), login_successfully_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		//custom code goes here
	
	}
}
	
	