package com.example.passwordmanagerfigma;

/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		register_successfully_screen
	 *	@date 		Monday 03rd of March 2025 02:28:17 AM
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

public class register_successfully_screen_activity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_successfully_screen);


		View _rectangle_52 = findViewById(R.id._rectangle_52);


		_rectangle_52.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), login_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		//custom code goes here
	
	}
}
	
	