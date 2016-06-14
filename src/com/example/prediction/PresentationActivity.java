package com.example.prediction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PresentationActivity extends Activity{
	Context context = this;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        
        Button start = (Button) findViewById(R.id.button_Start);
        
        start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//TRANSICION DE ACTIVIDAD
				startActivity(new Intent(context, ConfiguresActivity.class));
				overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);
			}
        	
        });
	}
	
	
	

}
