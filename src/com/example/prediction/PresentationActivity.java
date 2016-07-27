package com.example.prediction;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class PresentationActivity extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        this.getResources();
        
        Button flag_english = (Button) findViewById(R.id.button_presentation_flag_english);
        flag_english.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				selectLenguaje("en_US");
				launchActivity();
				
			}
        });
		
		Button flag_spanish = (Button) findViewById(R.id.button_presentation_flag_spanish);
        flag_spanish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				selectLenguaje("es");							
				launchActivity();
				
			}
        });	
	}
	
	private void launchActivity(){
		startActivity(new Intent(this, ConfiguresActivity.class));
		overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);
	}
	
	private void selectLenguaje(String lang){
		Locale locale = new Locale(lang); 
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration config = res.getConfiguration();
		config.locale = locale;
		res.updateConfiguration(config,dm);
	}
	
	
	

}
