package com.example.prediction;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ModelfitActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelfit);
        
       TextView test = (TextView) findViewById(R.id.textView_causesOverfit);
       String text = "<font COLOR=\'RED\'><b>" + "android-coding" + "</b></font>"
        	    + "<font COLOR=\'#00FF00\'><i>" + ".blogspot" + "</i></font>"
        	    + ".com";
        	  test.setText(Html.fromHtml(text));
        
        
        
        Button entendido = (Button) findViewById(R.id.button_modelfit_ok);
        entendido.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
        	
        });
        
	}

}
