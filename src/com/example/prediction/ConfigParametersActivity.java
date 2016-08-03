package com.example.prediction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.prediction.logica.Config;
import com.example.prediction.logica.database.AbsDatabase;
import com.example.prediction.logica.user_options.PolinomialTransformer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigParametersActivity extends Activity {
	private int REQUEST_CODE_FILE_SELECTED = 0;
	private static int lastChecked;
	private int degree = 1;
	private Info info = Info.getInstance();
	private AbsDatabase database_main = info.getDatasetSelected();
	
	public class RowItem {
		private int icon;
	    private int title;
	    
	    public RowItem(int icon, int title) {
	        this.icon = icon;
	        this.title = title;   
	    }
	    
	    public int getIcon(){
	    	return icon;
	    }
	    
	    public int getTitle(){
	    	return title;
	    }
	}
	
	public class ItemsAdapter extends ArrayAdapter<RowItem>{
		private Context context;
		private List<RowItem> list;

		public ItemsAdapter(Context context, int resource, List<RowItem> list) {
			super(context, resource);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.list = list;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
	           
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.items_polynomial_degree, parent, false);
            
            TextView titleView = (TextView) rowView.findViewById(R.id.textView_configparams_itemdegree);
            titleView.setText( context.getString(list.get(position).getTitle()));
            
            RadioButton radiobutton = (RadioButton) rowView.findViewById(R.id.radioButton_configparams_itemdegree);
            if(position==lastChecked){
                	radiobutton.setChecked(true);
                	radiobutton.setEnabled(false);
            }
            else {
            	radiobutton.setChecked(false);
            	radiobutton.setEnabled(true);
            }
            return rowView;   
		}	
	}
	
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configparameters);
         
        
        Button button_add = (Button) findViewById(R.id.button_configparams_add);
        button_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				//intent.putExtra(getString(Config.InitialSettings.EXTENSION_DATASET) );
				startActivityForResult(intent,REQUEST_CODE_FILE_SELECTED);
			
			}
		});
        
        ListView lvList = (ListView) findViewById(R.id.listView_configparams);
        
        
        ArrayList<RowItem> listItems = new ArrayList<RowItem>();
        for(int degree:Config.AppSettings.TITLE_POLYNOMIAL_DEGREE){
			listItems.add(new RowItem(R.drawable.icon_alert_historial,degree));
		}
	    ArrayAdapter<RowItem> itemsAdapter = new ArrayAdapter<RowItem>(this, R.layout.select_dialog_singlechoice_material, listItems ); 
        lvList.setAdapter(itemsAdapter); 
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	lastChecked = position;
            	degree = lastChecked + 1;
            	Toast.makeText(ConfigParametersActivity.this, "selecciono " + position, Toast.LENGTH_SHORT).show();
        		
            }
        });
        
        
        Button button_ok = (Button) findViewById(R.id.button_configparams_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (degree!=1){
					PolinomialTransformer pt=new PolinomialTransformer(degree);
					pt.transformDatabase(database_main);
					System.out.print("Termino transform");
					try {
						OptimizingSchemeActivity.addImageError(info.generateImageErrorPrediction(database_main, ConfigParametersActivity.this));
						OptimizingSchemeActivity.addImageLearningCurve(info.generateImageLearningCurve(database_main, ConfigParametersActivity.this));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				finish();
			}
		});
        
        
	}
	
	@Override
	protected void onActivityResult(int requestcode, int resultcode, Intent data){
		if (requestcode == REQUEST_CODE_FILE_SELECTED) {
			Uri uri = data.getData();
			File file = new File(uri.getPath());
			try {
				controlSamples(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	private void controlSamples(File file) throws Exception{
		database_main = info.getDatasetSelected();
		AbsDatabase database_clone = database_main.clone();
		database_clone.parseFile(file);
		
		if(database_main.getIndividuals().firstElement().equalsHeader(database_clone.getIndividuals().firstElement()) ){
			database_main.addData(file);
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.icon_alert_exception)
			.setTitle(R.string.configures_exception_title)
			.setMessage(R.string.message_alertdialog_addsamples_detail)
			.setPositiveButton(Config.Message.MESSAGE_DIALOG_OK, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
		
		
	}
	
}
