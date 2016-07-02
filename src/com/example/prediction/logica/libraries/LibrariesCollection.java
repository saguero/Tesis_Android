package com.example.prediction.logica.libraries;

public class LibrariesCollection {
	
	private static AbsLibrary[] libraries = {new WekaLibrary("Weka")
															}; 
	public LibrariesCollection(){
		
	}
	
	public static String[] getListLibraries(){
		String[] result = new String[libraries.length];
		int index = 0;
		for(AbsLibrary lib:libraries){
			result[index] = lib.getID();
		}
		return result;	
	}
	
	public static AbsLibrary getLibrary(String ID){
		AbsLibrary result = null;
		for(AbsLibrary lib:libraries){
			if(lib.getID().equals(ID))
				result = lib;
		}
		return result;
	}

	
}
