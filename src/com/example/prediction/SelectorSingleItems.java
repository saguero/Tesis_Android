package com.example.prediction;


import com.example.prediction.logica.ClassEstructure.LibraryClasses;
import com.example.prediction.logica.Config;
import com.example.prediction.Info;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SelectorSingleItems extends DialogFragment {
	
	int checkedItem = 0;
	static Info info = new Info();
	static int item;
	static int TitleId;
	static CharSequence[] items;
	static LibraryClasses library;
	
	public static SelectorSingleItems newInstance(int type, CharSequence[] it){
		
		switch(type){
		case Config.Item.ITEM_SELECT_LIBRARY:
			TitleId = R.string.title_chooseLibrary;
			break;
		case Config.Item.ITEM_SELECT_PREDICTED_ATT:
			TitleId = R.string.title_chooseAtt;
			library = info.getLibrarySelected();
			break;
		case Config.Item.ITEM_SELECT_FILE_DATASET:
			TitleId = R.string.title_chooseFileDataset;
			library = info.getLibrarySelected();
			break;
		}
		item=type;
		items = it;
		
		SelectorSingleItems dialogFragment = new SelectorSingleItems();
	    Bundle bundle = new Bundle();
	    dialogFragment.setArguments(bundle);

	    return dialogFragment;

	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	   
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    // Set the dialog title
	    builder.setTitle(TitleId)
	    	.setSingleChoiceItems(items, checkedItem, 
	             new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int item) {
						// TODO Auto-generated method stub
						
						switch(item){
						case Config.Item.ITEM_SELECT_LIBRARY:
							info.setLibrarySelected(items[item].toString());
							break;
						case Config.Item.ITEM_SELECT_PREDICTED_ATT:
							info.setAttributeSelected(item);
							break;
						case Config.Item.ITEM_SELECT_FILE_DATASET:
							try {
								info.setFileDatasetSelected(items[item].toString());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break; 
						}	
					}
	    		
	    	}).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	             @Override
	             public void onClick(DialogInterface dialog, int id) {
	                   
	            	 //FALTA HACER ALGO CON EL ITEM SELECCIONADO!
	            	 
	            	 
	                  dialog.dismiss();
	               }
	    	}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	            	@Override
	            	public void onClick(DialogInterface dialog, int id) {
	            		
	            	}
	                   
	          });
	    return builder.create();
	}

}
