package com.example.passwordmanagerfigma;

/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		add_information
	 *	@date 		Monday 03rd of March 2025 09:30:58 PM
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

import java.util.concurrent.atomic.AtomicReference;

public class add_information_activity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_information);


		AtomicReference<ImageView> _rectangle_8;
		_rectangle_8 = new AtomicReference<>(findViewById(R.id._rectangle_8));


		_rectangle_8.get().setOnClickListener(v -> {

            Intent nextScreen = new Intent(getApplicationContext(), successfully_added_activity.class);
            startActivity(nextScreen);


        });
		
		
		//custom code goes here
	
	}
}
	
	