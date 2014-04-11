package tweet;

import twitter4j.Status;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class SMOClassifier {
	
	public Category classify(Status status){
		//use the weka classifier here
		Classifier smoClassifier = loadSMOClassifier();

		if(smoClassifier != null){
			// Create the attributes, class and text 
			FastVector fvNominalVal = new FastVector(2); 
			fvNominalVal.addElement("reliefph"); 
			fvNominalVal.addElement("rescueph"); 
			fvNominalVal.addElement("safenow"); 
			fvNominalVal.addElement("floodph"); 
			fvNominalVal.addElement("tracingph"); 
			fvNominalVal.addElement("yolandaph"); 
			
			
			Attribute attribute1 = new Attribute("class", fvNominalVal); 
			Attribute attribute2 = new Attribute("text",(FastVector) null); 
			
			// Create list of instances with one element 
			FastVector fvWekaAttributes = new FastVector(2); 
			fvWekaAttributes.addElement(attribute1); 
			fvWekaAttributes.addElement(attribute2); 
			Instances instances = new Instances("Test relation", fvWekaAttributes, 1); 
			
			// Set class index 
			instances.setClassIndex(0); 
			
			// Create and add the instance 
			Instance instance = new Instance(2); 
			instance.setValue(attribute2, status.getText()); 
			
			// instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text); 
			instances.add(instance);
			try{
				double pred = smoClassifier.classifyInstance(instances.instance(0)); 
				String category = instances.classAttribute().value((int) pred);
				System.out.println("Class predicted: " + category);
			}catch(Exception e){e.printStackTrace();}
		}
		return null;
	}
	
	private Classifier loadSMOClassifier(){
		//load the trained classifer from the file - desrialization
		try{
			return (Classifier)weka.core.SerializationHelper.read("C:/Users/asus/workspace/Typhoon Tweet Dashboard/data/SMO.model");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
