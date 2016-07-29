package com.example.prediction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.example.prediction.logica.Config;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class InitialConfigurationActivity extends Activity {
	private ExpandibleListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private TextView textInfo;
    private Info info = new Info();
    private static Integer[] lastOptionSelect;
    

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialconfiguration);
        
       
        /*	ITEMS	*/
        expListView = (ExpandableListView) findViewById(R.id.expandableListView_initialconfiguration);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        
        configGroupItemsInitial();
        configItemsInitial();
        
        listAdapter = new ExpandibleListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
			@Override
			public boolean onChildClick(ExpandableListView parent, View view,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				
				if(lastOptionSelect[groupPosition] == null || lastOptionSelect[groupPosition] != childPosition){
					String itemSelected = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
					
					switch(groupPosition){
					
					case Config.InitialSettings.ITEM_CODE_FORMATFILE:
						Config.InitialSettings.setFormatDataset(Config.InitialSettings.getOptionsDatasetFormat()[childPosition]);
						configFileDatasetOptions();
						Toast.makeText(InitialConfigurationActivity.this,  getString(Config.Message.MESSAGE_SELECT_FORMATFILE) + itemSelected , Toast.LENGTH_LONG).show();			
						break;
						
					case Config.InitialSettings.ITEM_CODE_TYPEPREDICTION:
						Config.InitialSettings.setTypePrediction(Config.InitialSettings.getOptionsTypePrediction()[childPosition]);
						info.setTypePrediction(childPosition);
						configAttOptions();
						Toast.makeText(InitialConfigurationActivity.this, getString(Config.Message.MESSAGE_SELECT_TYPEPREDICTION) + itemSelected , Toast.LENGTH_LONG).show();
						break;
					
					case Config.InitialSettings.ITEM_CODE_STORAGE:
						moveDirectory(itemSelected);
						Config.InitialSettings.setWorkingDir(itemSelected);
						configFileDatasetOptions();
						Toast.makeText(InitialConfigurationActivity.this, getString(Config.Message.MESSAGE_SELECT_STORAGE) + itemSelected , Toast.LENGTH_LONG).show();
						break;
					}
					lastOptionSelect[groupPosition] = childPosition;
				}
				
				return false;
			}
        });
        
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
				// TODO Auto-generated method stub
				switch(groupPosition){
				case Config.InitialSettings.ITEM_CODE_FORMATFILE:
					textInfo.setText(R.string.initial_dialogTitle_formatfile);			
					break;
					
				case Config.InitialSettings.ITEM_CODE_TYPEPREDICTION:
					textInfo.setText(R.string.initial_dialogTitle_typeprediction);
					break;
				
				case Config.InitialSettings.ITEM_CODE_STORAGE:
					textInfo.setText(R.string.initial_dialogTitle_storage);
					break;
				}
				return false;
			}
		});
        
        
        textInfo = (TextView) findViewById(R.id.textView_initial_info);
 
        Button button_ok = (Button) findViewById(R.id.button_initial_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				finish();
			}
        	
        });
        
	}
	
	private void moveDirectory(String itemSelected){
		String oldPath = Config.InitialSettings.getDirWorking() + getString(R.string.initial_subdirectory_app);
		File directorySource = new File(oldPath);
		if(directorySource.exists()){
			File destination = new File(itemSelected + "/" + getString(R.string.initial_subdirectory_app) );
			destination.mkdir();
			File[] files = directorySource.listFiles();
			for(File f:files){
				File newFile = new File(destination,f.getName());
				f.renameTo(newFile);
			}
			directorySource.delete();
		}	
	}
	
	private void configFileDatasetOptions(){
		try {
			info.setListFilesDataset(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConfiguresActivity.controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_FILE_DATASET, true);
		ConfiguresActivity.controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT, false);
		ConfiguresActivity.controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_SCHEMES, false);
	}
	
	private void configAttOptions() {
		// TODO Auto-generated method stub
		ConfiguresActivity.controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT, true);
		ConfiguresActivity.controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_SCHEMES, false);
	}
	
	private void configGroupItemsInitial(){
		listDataHeader = new ArrayList<String>();
		for(Integer id: Config.InitialSettings.TITLE_INITIAL_ITEMS) {
			listDataHeader.add(getString(id));
		}
		lastOptionSelect = new Integer[listDataHeader.size()];
	}
	
	private void configItemsInitial(){
		listDataChild = new HashMap<String, List<String>>();
		
		List<String> formatFile = new ArrayList<String>();
		for(Integer id: Config.InitialSettings.getOptionsDatasetFormat())
			formatFile.add(getString(id));
		
		List<String> typePrediction = new ArrayList<String>();
		for(Integer id: Config.InitialSettings.getOptionsTypePrediction())
			typePrediction.add(getString(id));
		
		List<String> directorys = getDirectorys(Config.InitialSettings.getDirStorage());
		
		listDataChild.put(getString(Config.InitialSettings.TITLE_INITIAL_ITEMS[0]), formatFile);
		listDataChild.put(getString(Config.InitialSettings.TITLE_INITIAL_ITEMS[1]), typePrediction);
		listDataChild.put(getString(Config.InitialSettings.TITLE_INITIAL_ITEMS[2]), directorys);
		
	}
	
	private List<String> getDirectorys(String root){
		List<String> directorys = new ArrayList<String>();
		File f = new File(root);
		if(f.isDirectory())
			directorys.add(root);
		
		File[] files = f.listFiles();
		for(File arch: files)
			if(arch.isDirectory())  {
				String dir = arch.getAbsolutePath();
				if(! dir.endsWith("/"))
					dir = dir.concat("/");
				directorys.add(dir);
			}
		return directorys;
	}
	
	
}
