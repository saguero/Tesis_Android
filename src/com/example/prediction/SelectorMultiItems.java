package com.example.prediction;

import java.util.Vector;

import com.example.prediction.logica.AbsClassifier;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SelectorMultiItems extends DialogFragment {
	Vector<Integer> mSelectedItems; 
	Info info=new Info();
	
	
	
	public static SelectorMultiItems newInstance(){

		SelectorMultiItems dialogFragment = new SelectorMultiItems();
	    Bundle bundle = new Bundle();
	    dialogFragment.setArguments(bundle);

	    return dialogFragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    mSelectedItems = new Vector<Integer>();  // Where we track the selected items
	    CharSequence[] items;
	    Vector<AbsClassifier> schemes = info.getLibrarySelected().getListSchemes();
	    items = new CharSequence[schemes.size()];
	    int index = 0;
	    for(AbsClassifier s:schemes){
	    	items[index] = s.getName();
	    	index++;
	    }
	    
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    // Set the dialog title
	    builder.setTitle(R.string.configures_dialogchooseSchemes)
	    
	    	.setMultiChoiceItems(items, null,
	             new DialogInterface.OnMultiChoiceClickListener() {
	               	@Override
	               	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
	                   if (isChecked) {
	                       mSelectedItems.add(which);
	                   } else if (mSelectedItems.contains(which)) {
	                      mSelectedItems.remove(Integer.valueOf(which));
	                   }
	               }
	           })
	    // Set the action buttons
	        .setPositiveButton(R.string.configures_dialogok, new DialogInterface.OnClickListener() {
	             @Override
	             public void onClick(DialogInterface dialog, int id) {
	                   
	            	 info.setSchemesSelected(mSelectedItems);
	            	 mSelectedItems.removeAllElements();
	                 dialog.dismiss();
	               }
	           })
	        .setNegativeButton(R.string.configures_dialogcancel, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int id) {
	            	dialog.dismiss();  
	               }
	           });

	    return builder.create();
	}
	
}
