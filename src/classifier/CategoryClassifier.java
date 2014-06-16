package classifier;

import helpers.Constants;
import tweet.Category;
import twitter4j.Status;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class CategoryClassifier {

final String CATEGORY_MODEL = "SMO2.model";
	
	private Classifier categorySMOClassifier;
	
	public CategoryClassifier(){
		categorySMOClassifier = loadSMOClassifier();
	}
	
	
	public Category determineCategory(Status status){
		//use the weka classifier here
		if(categorySMOClassifier != null){
			// Create the attributes, class and text 
			FastVector fvNominalVal = new FastVector(2); 
			for(Category category: Category.values())
				fvNominalVal.addElement(category.getName());
		
			
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
				double pred = categorySMOClassifier.classifyInstance(instances.instance(0)); 
				return Category.getCategory(instances.classAttribute().value((int) pred));
		
			}catch(Exception e){e.printStackTrace();}
		}
		return null;
	}
	
	private Classifier loadSMOClassifier(){
		//load the trained classifer from the file - desrialization
		try{
			return (Classifier)weka.core.SerializationHelper.read(Constants.MODEL_PATH+CATEGORY_MODEL);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
