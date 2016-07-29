/*package main;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import database.WekaDatabase;
import models.AbsModeler;
import models.LinearRegClassifier;
import models.MultilayerPerceptronClassifier;
import models.SGDClassifier;
import models.SMOregClassifier;
import models.SimpleKClusterer;
import models.SimpleLinearRegClassifier;
import user_options.ExtendTransformer;
import user_options.PolinomialTransformer;

public class Main {
	
	public static void main(String[] args) throws IOException{
		Vector<AbsModeler> models=new Vector<AbsModeler>();
		
		File database=new File("C:\\Users\\Tina\\Desktop\\test.csv");
		File extend=new File("C:\\Users\\Tina\\Desktop\\test2.csv");
		
		WekaDatabase wekaDatabase1=new WekaDatabase();
		wekaDatabase1.parseFile(database);
		WekaDatabase wekaDatabase2=new WekaDatabase();
		wekaDatabase2.parseFile(database);
		
		ExtendTransformer tran=new ExtendTransformer(extend);
		PolinomialTransformer poltran=new PolinomialTransformer(2);
		
		poltran.transformDatabase(wekaDatabase2);

		SimpleKClusterer skc=new SimpleKClusterer();
		
		int index=10;
		
		LinearRegClassifier lrc=new LinearRegClassifier(index);
		MultilayerPerceptronClassifier mpc=new MultilayerPerceptronClassifier(index);	//Neural
		SimpleLinearRegClassifier slrgc=new SimpleLinearRegClassifier(index);
		SMOregClassifier smoreg=new SMOregClassifier(index);							//Vector

		SGDClassifier sgd=new SGDClassifier(index);
		
		//models.add(skc);
		models.add(lrc);	//-->Falta database2
		models.add(mpc);	//-->Falta database2
		models.add(slrgc);	//-->Falta database2
		models.add(smoreg);
		models.add(sgd);
		
		for (AbsModeler am: models){
			//AbsModeler result=am.calculateModeler(wekaDatabase1);
			//System.out.println(result.toString());
			//tran.transformDatabase(wekaDatabase1);
			AbsModeler result=am.calculateModeler(wekaDatabase2);
			System.out.println(result.toString());
		}
		
		
		
		//slrgc.calculateModeler(database);
		
		//poltran.transformModel(slrgc);
		System.out.println("FINIsh");
	}

}*/
