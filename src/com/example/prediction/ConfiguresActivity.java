package com.example.prediction;

import java.util.ArrayList;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

import com.example.prediction.Info;
import com.example.prediction.logica.Config;
import com.example.prediction.logica.models.AbsModeler;

public class ConfiguresActivity extends Activity {
	private static boolean[] enableItems;
	private static boolean[] selectItems;
	
	private ProgressDialog progressDialog;
	private static Button button_lock;
	private static ItemsAdapter adapter;
	private static ListView lvList;
	
	private Info info = new Info();
	private Integer[] selectedModels;
	private static Integer[] lastSelectedItems;
	
	private int selectedItem;
	public class RowItems {
		private int icon;
		private int checked;
	    private String title;
	    
	    public RowItems(int icon, int checked, String title) {
	        this.icon = icon;
	        this.checked = checked;
	        this.title = title;   
	    }
	    
	    public int getIcon(){
	    	return icon;
	    }
	    
	    public String getTitle(){
	    	return title;
	    }
	    
	    public int getChecked(){
	    	return checked;
	    }
	}

	public class ItemsAdapter extends ArrayAdapter<RowItems> {
		private final Context context;
        private final ArrayList<RowItems> list;

		public ItemsAdapter(Context context, ArrayList<RowItems> list) {
			super(context, R.layout.item_configures, list);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.list = list;
			
		}
		
		@SuppressLint("ViewHolder") @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.item_configures, parent, false);
            
            ImageView imgView_icon = (ImageView) rowView.findViewById(R.id.imageView_configures_items_icon); 
            ImageView imgView_checked = (ImageView) rowView.findViewById(R.id.imageView_configures_items_checked);
            TextView titleView = (TextView) rowView.findViewById(R.id.textview_configures_id);
 
            imgView_icon.setImageResource(list.get(position).getIcon());
            imgView_checked.setImageResource(list.get(position).getChecked());
            imgView_checked.setVisibility(View.INVISIBLE);
            titleView.setText(list.get(position).getTitle());
 
            return rowView;
        }
		
		
		public void showItemChecked(int position, ViewGroup parent){
			View row = ((ListView) parent).getChildAt(position);
			ImageView imgView = (ImageView) row.findViewById(R.id.imageView_configures_items_checked); 
			imgView.setVisibility(View.VISIBLE);        
		}
		
		public void hideItemChecked(int position, ViewGroup parent){
			View row = ((ListView) parent).getChildAt(position);
			ImageView imgView = (ImageView) row.findViewById(R.id.imageView_configures_items_checked); 
			imgView.setVisibility(View.INVISIBLE);  
		}
	}
	
	public class DialogItemsAdapter extends ArrayAdapter<CharSequence> {
		private Vector<Integer> container;

		public DialogItemsAdapter(Context context, CharSequence[] objects, Vector<Integer> container, int layout) {
			super(context,layout, objects);
			// TODO Auto-generated constructor stub
			this.container = container;	
		}
		
		@Override
		public boolean isEnabled(int position){
			return container.contains(position);
		}	
	}



	
	/*	ALERT DIALOG CLASS		*/
	
	public class OptionsItemsSelector extends AlertDialog{
		private int checkedItem = 0;
		private int item;
		private int TitleId;
		private CharSequence[] optionsItem;
		
		private ItemsAdapter adapter;
		private ViewGroup parentAdapter;
		private AlertDialog dialog;

		protected OptionsItemsSelector(int type, CharSequence[] it, ItemsAdapter ad, ViewGroup parent) {
			super(ConfiguresActivity.this);
			// TODO Auto-generated constructor stub
			config(type);
			item=type;
			optionsItem = it;
			adapter = ad;
			parentAdapter = parent;
		}
		
		private void enabledPositiveButton(boolean enabled){
			dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(enabled);	
		}
		
		private void config(int type){
			switch(type){
			case Config.AppSettings.ITEM_CODE_SELECT_LIBRARY:
				TitleId = R.string.configures_dialogchooseLibrary;
				break;
			case Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT:
				TitleId = R.string.configures_dialogchooseAtt;
				break;
			case Config.AppSettings.ITEM_CODE_SELECT_FILE_DATASET:
				TitleId = R.string.configures_dialogchooseFileDataset;
				break;
			case Config.AppSettings.ITEM_CODE_SELECT_SCHEMES:
				TitleId = R.string.configures_dialogchooseSchemes;
				break;
			}
		
		}
		
		public void createSingleOptionDialog() {
			   
			AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguresActivity.this);
		    builder.setTitle(TitleId)
		    .setSingleChoiceItems(optionsItem, checkedItem, new DialogInterface.OnClickListener(){
		    		@Override
					public void onClick(DialogInterface dialog, int itemSelected) {
						// TODO Auto-generated method stub
						checkedItem = itemSelected;		
					}
		    		
		    }).setPositiveButton(Config.Message.MESSAGE_DIALOG_OK, new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {  
		            	 switch(item){
							case Config.AppSettings.ITEM_CODE_SELECT_LIBRARY:
								selectItems[item] = true;
								controlSelectLibrary(checkedItem, optionsItem[checkedItem].toString());
								break;
								
							case Config.AppSettings.ITEM_CODE_SELECT_FILE_DATASET:
								selectItems[item] = true;
								controlPickFile(checkedItem,optionsItem[checkedItem].toString() );
								break;
								
							case Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT:
								selectItems[item] = true;
								controlSelectPredAtt(checkedItem);	
							}	
		            	  dialog.dismiss();
		                  adapter.showItemChecked(item, parentAdapter);
		               }
		    }).setNegativeButton(Config.Message.MESSAGE_DIALOG_CANCEL, new DialogInterface.OnClickListener() {
		            	@Override
		            	public void onClick(DialogInterface dialog, int id) {
		            		
		            	}
		                   
		          });
		    if(item == Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT){
		    	builder.setAdapter(new DialogItemsAdapter(ConfiguresActivity.this,
		    			optionsItem, info.getListAttHandlerPrediction(info.getDatasetSelected()),
		    			R.layout.select_dialog_singlechoice_material), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int itemSelected) {
								// TODO Auto-generated method stub
								checkedItem = itemSelected;	
							}
						});
		    }		    
		    builder.create().show();
		    
		}
		
		public void showMultiOptionDialog() {
			final Vector<Integer> mSelectedItems = new Vector<Integer>();
			
			AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguresActivity.this);
			builder.setTitle(TitleId)
		    .setMultiChoiceItems(optionsItem, null,
		    		new DialogInterface.OnMultiChoiceClickListener() {
		               	@Override
		               	public void onClick(DialogInterface dialog, int itemSelected, boolean isChecked) {
		               		if (isChecked) {
		                       mSelectedItems.add(itemSelected);
		                   } else if (mSelectedItems.contains(itemSelected)) {
		                      mSelectedItems.remove(Integer.valueOf(itemSelected));
		                   }
		                   if(mSelectedItems.size() > 0)
		                	 enabledPositiveButton(true);  
		                   else
		                	 enabledPositiveButton(false);
		               }
		           })
		   .setPositiveButton(Config.Message.MESSAGE_DIALOG_OK, new DialogInterface.OnClickListener() {
		    	@Override
		        public void onClick(DialogInterface dialog, int id) {
		            selectItems[item] = true;
		            controlSelectSchemes(mSelectedItems);
		            mSelectedItems.removeAllElements();
		            dialog.dismiss();
		            adapter.showItemChecked(item, parentAdapter);
		                 
		               }
		           })
		     .setNegativeButton(Config.Message.MESSAGE_DIALOG_CANCEL, new DialogInterface.OnClickListener() {
		        @Override
		        public void onClick(DialogInterface dialog, int id) { 
		               }
		           });


			dialog = builder.create();
		    dialog.show();
		    
		    //for(int item = 0; item < optionsItem.length; item++)
		    	//if(info.getListSchemesHandlerData(info.getDatasetSelected()).contains(item))
		    	//	dialog.getListView().setItemChecked(item, false);
		    
		   /*	Enabled-Disable button Ok	*/
		   if(mSelectedItems.size() == 0)
			   dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);	
		}	
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configures);
        
     
        button_lock = (Button) findViewById(R.id.button_configures_lock);
        button_lock.setAlpha(0.2F);
        button_lock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
        		progressDialog = ProgressDialog.show(	ConfiguresActivity.this, 
        												getString(Config.Message.MESSAGE_PROGRESSDIALOG_TITLE),
        												getString(Config.Message.MESSAGE_PROGRESSDIALOG_DETAIL, true) );
                progressDialog.setCancelable(false);
                progressDialog.setIcon(R.drawable.icon_alert_wait);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Do some stuff that take some time...
                            //Thread.sleep(5000); // Let's wait for some time	
                            Vector<AbsModeler> models=info.getListSchemesSelected();
                            for (AbsModeler m:models){
                            	m.calculateModeler(info.getDatasetSelected());
                            }
                            info.setFilteredBestSchemes();
                        } catch (Exception e) {
                             e.printStackTrace();
                        }
                        progressDialog.dismiss();
                        try {
							launchActivity(FilteredSchemesActivity.class);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                }).start();
			}
        	
        });
		button_lock.setEnabled(false);
        
        Button button_settings = (Button) findViewById(R.id.button_configures_settings);
        button_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				try {
					launchActivity(InitialConfigurationActivity.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        adapter = new ItemsAdapter(this, generateAndConfigData());  
        lvList = (ListView) findViewById(R.id.listview_configures);
        lvList.setAdapter(adapter); 
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case Config.AppSettings.ITEM_CODE_SELECT_LIBRARY:
					selectLibrary();
					break;
				case Config.AppSettings.ITEM_CODE_SELECT_FILE_DATASET:
					pickFile();
					break;
				case Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT:
					selectPredAtt(position);
					break;
				case Config.AppSettings.ITEM_CODE_SELECT_SCHEMES:
					selectSchemes();

				}	
			}
        });    
	}   
   
/*- - - - - - - - - - - - - - - - - - - - - - - - - 	LOGICA	- - - - - - - - - - - - - - - - - - - - - - - - -*/
	
	private ArrayList<RowItems> generateAndConfigData(){
		Integer[] aux = info.getConfigureItems();
		int length = aux.length;
		
		String[] configureItems = new String[length];
		selectItems = new boolean[length];
		enableItems = new boolean[length];
		lastSelectedItems = new Integer[length];
		
		int[] icons = Config.AppSettings.ICON_CONFIGURES_ITEMS;
		
		for(int index = 0; index<length; index++){
			configureItems[index] = getString(aux[index]);
			selectItems[index] = false;
			enableItems[index] = false;
		}
		enableItems[0] = true;
		
		ArrayList<RowItems> listItems = new ArrayList<RowItems>();
		for(int index=0; index<length; index++){
			int icon = icons[index];
			listItems.add(new RowItems(icon,R.drawable.icon_checked, configureItems[index]));
		}
		return listItems;
    }
	
	private void showNotification(int idMessage){
		AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguresActivity.this);
	    builder.setTitle(R.string.configures_exception_title)
	    .setMessage(idMessage)
	    .setIcon(R.drawable.icon_alert_exception)
	    .setPositiveButton(Config.Message.MESSAGE_DIALOG_OK, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	    builder.create().show();






	}
	
	private void selectLibrary(){


		int index = Config.AppSettings.ITEM_CODE_SELECT_LIBRARY;
		CharSequence[] items = info.getListLibraries();
		if(items.length > 0) {
			OptionsItemsSelector dialogFrag = new OptionsItemsSelector(index,items , adapter, lvList);
			dialogFrag.createSingleOptionDialog();


		}
		else
			showNotification(R.string.configures_exception_noLibrary);
	}
	
	private void controlSelectLibrary(int itemSelect, String item){
		int index = Config.AppSettings.ITEM_CODE_SELECT_LIBRARY;

		
		if(lastSelectedItems[index] == null || lastSelectedItems[index] != itemSelect) {					
			info.setLibrarySelected(item);
			info.setListFilesDataset(this);
			info.setListSchemes();
			lastSelectedItems[index]=itemSelect;
			info.getLibrarySelected().setNumericalTypes();
			info.getLibrarySelected().setCategoricalTypes();	
			
			controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_FILE_DATASET, true);
			controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT, false);
			controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_SCHEMES, false);
		}	

	}
	
	private void pickFile(){
		int index = Config.AppSettings.ITEM_CODE_SELECT_FILE_DATASET;
		if(enableItems[index]){	
			CharSequence[] items = info.getListFilesDataset();
			if(items.length > 0) {
				final OptionsItemsSelector dialogFrag = new OptionsItemsSelector(index, items , adapter, lvList);		
				dialogFrag.createSingleOptionDialog();
			}
			else
				showNotification(R.string.configures_exception_noFile);
		}



		else

			Toast.makeText(getApplicationContext(), Config.Exception.EXCEPTION_MISSING_LIBRARY,Toast.LENGTH_LONG).show(); 	
	}
	
	private void controlPickFile(int itemSelect, String item){
		int index = Config.AppSettings.ITEM_CODE_SELECT_FILE_DATASET;
		if(lastSelectedItems[index] == null || lastSelectedItems[index] != itemSelect) {					
			try {
				info.setFileDatasetSelected(item, 	Config.InitialSettings.getDirWorking() +
													getString(Config.InitialSettings.SUBDIR_APP) +
													getString(Config.InitialSettings.SUBDIR_DATABASE));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lastSelectedItems[index]=itemSelect;


			
			controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT, true);
			controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_SCHEMES, false);
		}			
	}
	
	private void selectPredAtt(int itemSelect){
		int index = Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT;
		if(enableItems[index]){
			CharSequence[] items = info.getListAttributes();
			if(items.length > 0) {
				final OptionsItemsSelector dialogFrag = new OptionsItemsSelector(index, items, adapter, lvList);
				dialogFrag.createSingleOptionDialog();
			}
			else
				showNotification(R.string.configures_exception_convertFile);
		}
		else
			Toast.makeText(getApplicationContext(), Config.Exception.EXCEPTION_MISSING_FILEDATASET,Toast.LENGTH_LONG).show(); 	
	}
		
	private void controlSelectPredAtt(int itemSelect){
		int index = Config.AppSettings.ITEM_CODE_SELECT_PREDICTED_ATT;
		if(lastSelectedItems[index] == null || lastSelectedItems[index] != itemSelect) {
			info.setAttributeSelected(itemSelect);
			lastSelectedItems[index]=itemSelect;	 
			
			controlReSelect(Config.AppSettings.ITEM_CODE_SELECT_SCHEMES, true);
		}	
		
	}
	
	private void selectSchemes() {
		if(enableItems[Config.AppSettings.ITEM_CODE_SELECT_SCHEMES]) {
			CharSequence[] items = info.getListSchemes();
			if(items.length > 0) {
				final OptionsItemsSelector dialogFrag = new OptionsItemsSelector(Config.AppSettings.ITEM_CODE_SELECT_SCHEMES, items, adapter, lvList);
				dialogFrag.showMultiOptionDialog();
			}
			else
				showNotification(R.string.configures_exception_noModels);
		}
		else 
			Toast.makeText(getApplicationContext(),Config.Exception.EXCEPTION_MISSING_PREDATT,Toast.LENGTH_LONG).show();			
	}


	
	private void controlSelectSchemes(Vector<Integer> schemes) {
		int index = Config.AppSettings.ITEM_CODE_SELECT_SCHEMES;
		if(selectItems[index]){
			info.setSchemesSelected(schemes);
			button_lock.setBackgroundResource(R.drawable.icon_lockopen);
			button_lock.setAlpha(1);
			button_lock.setEnabled(true);
		}		
	}
	
	private void launchActivity(Class<?> activity) throws Exception{
    	Intent intent = new Intent(this, activity);
    	startActivity(intent);
    	overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);





	}
	
	public static void controlReSelect(int index, boolean enabled){
		enableItems[index]=enabled;
		if(selectItems[index]){
			selectItems[index] = false;
			lastSelectedItems[index]=null;
			button_lock.setEnabled(false);
			button_lock.setBackgroundResource(R.drawable.icon_lockclose);
			button_lock.setAlpha(0.2F);
			adapter.hideItemChecked(index, lvList);












		}



	}
}
