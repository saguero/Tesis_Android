package com.example.prediction;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SelectorMultiItems extends DialogFragment {
	ArrayList<Integer> mSelectedItems; 
	
	
	
	public static SelectorMultiItems newInstance(){

		SelectorMultiItems dialogFragment = new SelectorMultiItems();
	    Bundle bundle = new Bundle();
	    dialogFragment.setArguments(bundle);

	    return dialogFragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    mSelectedItems = new ArrayList<Integer>();  // Where we track the selected items
	    
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    // Set the dialog title
	    builder.setTitle(R.string.title_chooseSchemes)
	    
	    	.setMultiChoiceItems(R.array.array_chooseSchemes, null,
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
	        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	             @Override
	             public void onClick(DialogInterface dialog, int id) {
	                   
	            	 Info i=new Info();
	            	 i.setListSchemes();		//--> FALTA!!!
	            	 
	                 dialog.dismiss();
	               }
	           })
	        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int id) {
	            	dialog.dismiss();  
	               }
	           });

	    return builder.create();
	}
	
}
