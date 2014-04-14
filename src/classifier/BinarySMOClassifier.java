package classifier;

import tweet.Category;
import twitter4j.Status;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class BinarySMOClassifier {
	
	private String categoryName;
	private Classifier smoClassifier;
	
	public BinarySMOClassifier(String categoryName){
		this.categoryName = categoryName;
		smoClassifier = loadSMOClassifier(categoryName);
	}
	
	public Category classify(Status status){
		//use the weka classifier here
		if(smoClassifier != null){
			// Create the attributes, class and text 
			FastVector fvNominalVal = new FastVector(2); 
			fvNominalVal.addElement("positive"); 
			fvNominalVal.addElement("negative"); 
			
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
				if(category.equals("positive"))
					return Category.getCategory(categoryName);
			}catch(Exception e){e.printStackTrace();}
		}
		return null;
	}
	
	private Classifier loadSMOClassifier(String categoryName){
		//load the trained classifer from the file - desrialization
		try{
			return (Classifier)weka.core.SerializationHelper.read("C:/Users/asus/workspace/Typhoon Tweet Dashboard/data/"+categoryName+".model");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
