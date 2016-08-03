package com.example.prediction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModelfitActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelfit);
        
        /* 	TextView title = (TextView) findViewById(R.id.textView_modelfit_title);
       		String text = "<font COLOR=\'RED\'><b>" + "android-coding" + "</b></font>"
        	    + "<font COLOR=\'#00FF00\'><i>" + ".blogspot" + "</i></font>"
        	    + ".com";
       		title.setText(Html.fromHtml(getString(R.string.modelfit_title))); */
       
       
        Button entendido = (Button) findViewById(R.id.button_modelfit_ok);
        entendido.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
        	
        });
        
	}

}
