package com.example.passwordmanagerfigma;

/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		login_successfully_screen
	 *	@date 		Sunday 02nd of March 2025 10:50:15 PM
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

public class login_successfully_screen_activity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_successfully_screen);


		View _rectangle_52 = findViewById(R.id._rectangle_52);


		_rectangle_52.setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), manage_screen_activity.class);
            startActivity(nextScreen);


        });
		
		
		//custom code goes here
	
	}
}
	
	