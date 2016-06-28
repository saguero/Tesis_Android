package com.example.prediction;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prediction.Info;
import com.example.prediction.logica.Config;

public class ConfiguresActivity extends Activity {
	
	//comment
	public class RowItem {
		//COMPLETAR
	}
	
	ProgressDialog progressDialog;
	Button button_prediction;
	ListView lvList;
	
	Info info = new Info();
	Integer[] selectItems;
	int selectedItems;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configures);
        String[] configureItems = info.getConfigureItems();
        
     
        button_prediction = (Button) findViewById(R.id.button_makePred);
        button_prediction.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progressDialog = ProgressDialog.show(ConfiguresActivity.this, Config.Item.ITEM_PROGRESSDIALOG_TITLE,
													Config.Item.ITEM_PROGRESSDIALOG_DETAIL, true);
                progressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Do some stuff that take some time...
                            Thread.sleep(5000); // Let's wait for some time			 
                        } catch (Exception e) {
                             
                        }
                        progressDialog.dismiss();
                        try {
							selectBestSchemes();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                }).start();
			}
        	
        });
        
        initializeSelectedItems();
        button_prediction.setVisibility(View.INVISIBLE);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_configures, R.id.textview_configures_id, configureItems);

        lvList= (ListView) findViewById(R.id.listview_configure);
        lvList.setAdapter(adapter);
         
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        
            @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case Config.Item.ITEM_SELECT_LIBRARY:
					selectLibrary();
					break;
				case Config.Item.ITEM_SELECT_FILE_DATASET:
					pickFile();
					break;
				case Config.Item.ITEM_SELECT_PREDICTED_ATT:
					selectPredAtt();
					break;
				case Config.Item.ITEM_SELECT_SCHEMES:
					selectSchemes();
					break;
				}	
				if(selectItems[position] == 0){
					selectItems[position] = 1;
					selectedItems++;
				}
				if(selectedItems == selectItems.length)
					button_prediction.setVisibility(View.VISIBLE);
					
			}
        });    
	}
	
	private void initializeSelectedItems(){
		selectedItems = 0;
		int length = info.getConfigureItems().length;
		selectItems = new Integer[length];
		for(int index = 0; index<length; index++)
			selectItems[index] = 0;	
	}
	
	private void selectSchemes() {
		
		if(selectItems[Config.Item.ITEM_SELECT_LIBRARY]==1){
			SelectorMultiItems dialogFrag = SelectorMultiItems.newInstance();
			dialogFrag.show(getFragmentManager().beginTransaction(), "dialog");
		}
		else
			Toast.makeText(getApplicationContext(),Config.Exception.MISSING_LIBRARY,Toast.LENGTH_LONG).show();
		
		
	}
	
	private void selectPredAtt(){
		if(selectItems[Config.Item.ITEM_SELECT_LIBRARY]==0)
			Toast.makeText(getApplicationContext(),Config.Exception.MISSING_LIBRARY,Toast.LENGTH_LONG).show();
		else
			if(selectItems[Config.Item.ITEM_SELECT_FILE_DATASET]==0)
				Toast.makeText(getApplicationContext(),Config.Exception.MISSING_FILEDATASET,Toast.LENGTH_LONG).show();
			else {
				showDialogSelectSingleItems(Config.Item.ITEM_SELECT_PREDICTED_ATT);
			
			}
	}
	
	private void selectLibrary(){
		showDialogSelectSingleItems(Config.Item.ITEM_SELECT_LIBRARY);
	
	}
	
	private void pickFile(){
		if(selectItems[Config.Item.ITEM_SELECT_LIBRARY] == 0)
			Toast.makeText(getApplicationContext(), Config.Exception.MISSING_LIBRARY,Toast.LENGTH_LONG).show(); 
		
		showDialogSelectSingleItems(Config.Item.ITEM_SELECT_FILE_DATASET);	
	}

	public void showDialogSelectSingleItems(int type){
		CharSequence[] aux = null;
		switch (type) {
		case Config.Item.ITEM_SELECT_LIBRARY:
			aux=info.getListLibraries();
			break;
		case Config.Item.ITEM_SELECT_PREDICTED_ATT:
			aux=info.getListAttributes();
			break;
		case Config.Item.ITEM_SELECT_FILE_DATASET:
			aux=info.getListFilesDataset();
		}
		
		SelectorSingleItems dialogFrag = SelectorSingleItems.newInstance(type, aux);
        dialogFrag.show(getFragmentManager().beginTransaction(), "dialog");	
	}

	public void selectBestSchemes() throws Exception{
		
		//when finish...
    	Intent intent = new Intent(this, FilteredSchemesActivity.class);
    	startActivity(intent);
    	overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);
	}
}
