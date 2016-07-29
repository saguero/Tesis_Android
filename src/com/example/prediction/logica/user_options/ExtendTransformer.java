package com.example.prediction.logica.user_options;

import java.io.File;

import com.example.prediction.logica.database.AbsDatabase;

public class ExtendTransformer implements DatabaseTransformer {
	
	private File file;
	
	public ExtendTransformer(File file){
		this.file=file;
	}
	
	public void transformDatabase(AbsDatabase database){
		database.addData(file);
	}

}
